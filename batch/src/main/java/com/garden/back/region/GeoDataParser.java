package com.garden.back.region;

import com.garden.back.global.GeometryUtil;
import lombok.extern.slf4j.Slf4j;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
public class GeoDataParser {

    @Value("${region.geo.csv.path}")
    private Resource csvResource;

    @Value("${region.geo.json.path}")
    private Resource jsonResource;

    private static final GeometryFactory geometryFactory = new GeometryFactory();
    private static final Map<String, MultiPolygon> emdCdToMultiPolygonMap = new HashMap<>();

    private static final String NUMBER_REGX = "[1-9]\\d*|0";

    private final RegionRepository regionRepository;

    public GeoDataParser(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public void saveAllKoreanRegionWithMultipolygonAndBeopjeongdong() {
        List<List<String>> list = readToList();
        saveMultipolygon();
        List<Region> regions = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {

            List<String> line = list.get(i);

            //csv를 읽을 때 한줄한줄 읽는데, 현재 엑셀 파일에는 시도 전체 정보,해당 시의 시군구 전체정보, 읍면동 전체정보, 리 전체 정보
            //가 가장 위엣줄에 존재한다. 해당 시도의 시군구, 읍면동, 리의 정보가 많아지면 줄바뀜이 일어나는데, 프로젝트에서 원하는 데이터는
            //특정 읍면동의 emd_cd라는 고유 코드와 중심좌표다. 따라서 각 시도의 시군구 정보를 표시하는 줄을 제거하고, 각 시군구에서 읍면동
            //정보를 제공하는 라인을 제거하고, 리의 정보를 제거해야 한다. 정확히 한줄 씩 표기되는 읍면동 정보만 읽어야 한다.
            //1. 시도의 시군구 정보 표시 줄 제거
            //2. 시군구의 읍면동 정보 표시 줄 제거
            //3. 리 정보 표시 줄 제거
            //4. 이렇게 제거하면 남는 모든 줄은 읍면동에 대한 정보이다.
            //출력 예시: [4617013400, POINT (126.78911649602146 35.01843790119987), 126.78911649602146, 35.01843790119987, 전라남도 나주시 빛가람동, 2, 46, 전라남도, 46170, 나주시, 46170134, 빛가람동, , 0, 0, 0]
            String emdCd = CsvUtil.getColumnValue(line, RegionColumn.EMD_CD);
            if (!emdCd.matches(NUMBER_REGX)) {
                continue; //지역 코드가 제일 앞에 와야 함(아닌 경우는 지역 정보가 아님)
            }

            Integer townCount = Integer.parseInt(CsvUtil.getColumnValue(line, RegionColumn.TOWN_CNT));
            if (townCount != 0) {
                continue; //읍면동의 개수가 여러개 출력되는 경우는 단일 읍면동의 정보는 없음.
            }

            String complexCountryName = CsvUtil.getColumnValue(line, RegionColumn.COMPLEX_COUNTRY_NAME);
            if (complexCountryName.length() >= 2) {
                continue; //시군구의 정보가 여러개 조회되는 경우도 단일 읍면동의 정보는 없음
            }

            String withoutLastTwoChars = emdCd.substring(0, emdCd.length() - 2); //마지막 두 자리는 리에 대한 번호인데 시군구 데이터는
            String pointName = CsvUtil.getColumnValue(line, RegionColumn.POINT_NAME);
            if (pointName.length() < 3) {
                continue; //경기도 화성시 새솔동은 정보를 제공 안함.
            }

            String city = CsvUtil.getColumnValue(line, RegionColumn.CITY);
            String country = CsvUtil.getColumnValue(line, RegionColumn.COUNTRY);
            String town = CsvUtil.getColumnValue(line, RegionColumn.TOWN);

            if (town.equals("오포읍")) continue; //오포읍은 사라짐
            Long code = Long.parseLong(withoutLastTwoChars);

            if (city.equals("강원도")) {
                city = "강원특별자치도";
                code += 10000000;
                code -= 1000000;
            }

            if (city.equals("경상북도") && country.equals("군위군")) { //경북 군위가 대구 군위로 바뀜
                code -= 20000000;
                if (town.equals("삼국유사면")) code = 27720370L;
            }
            double longitude = Double.parseDouble(CsvUtil.getColumnValue(line, RegionColumn.LONGITUDE));
            double latitude = Double.parseDouble(CsvUtil.getColumnValue(line, RegionColumn.LATITUDE));


            Point point = GeometryUtil.createPoint(latitude, longitude);
            Address address = new Address(city, country, town);
            MultiPolygon multiPolygon = emdCdToMultiPolygonMap.get(String.valueOf(code));

            Region region = new Region(code, address, point, multiPolygon);
            regions.add(region);
        }

        regionRepository.saveAll(regions);
    }

    public void saveChangedRegions() {
        saveMultipolygon();
        Point point = GeometryUtil.createPoint(37.3742, 127.2234);
        Address address = new Address("경기도", "광주시", "고산동");
        Long code = 41610114L;
        MultiPolygon multiPolygon = emdCdToMultiPolygonMap.get(String.valueOf(code));
        Region region = new Region(code, address, point, multiPolygon);


        Point point2 = GeometryUtil.createPoint(37.3494, 127.2065);
        Address address2 = new Address("경기도", "광주시", "문형동");
        Long code2 = 41610117L;
        MultiPolygon multiPolygon2 = emdCdToMultiPolygonMap.get(String.valueOf(code2));
        Region region2 = new Region(code2, address2, point2, multiPolygon2);

        Point point3 = GeometryUtil.createPoint(37.3623, 127.2238);
        Address address3 = new Address("경기도", "광주시", "추자동");
        Long code3 = 41610118L;
        MultiPolygon multiPolygon3 = emdCdToMultiPolygonMap.get(String.valueOf(code3));
        Region region3 = new Region(code3, address3, point3, multiPolygon3);

        Point point4 = GeometryUtil.createPoint(37.356157, 127.262164);
        Address address4 = new Address("경기도", "광주시", "매산동");
        Long code4 = 41610119L;
        MultiPolygon multiPolygon4 = emdCdToMultiPolygonMap.get(String.valueOf(code4));
        Region region4 = new Region(code4, address4, point4, multiPolygon4);

        Point point5 = GeometryUtil.createPoint(37.429431, 127.255048);
        Address address5 = new Address("경기도", "광주시", "양벌동");
        Long code5 = 41610120L;
        MultiPolygon multiPolygon5 = emdCdToMultiPolygonMap.get(String.valueOf(code5));
        Region region5 = new Region(code5, address5, point5, multiPolygon5);

        Point point6 = GeometryUtil.createPoint(37.38331, 127.260577);
        Address address6 = new Address("경기도", "광주시", "신현동");
        Long code6 = 41610115L;
        MultiPolygon multiPolygon6 = emdCdToMultiPolygonMap.get(String.valueOf(code6));
        Region region6 = new Region(code6, address6, point6, multiPolygon6);

        Point point7 = GeometryUtil.createPoint(37.351526, 127.155835);
        Address address7 = new Address("경기도", "광주시", "능평동");
        Long code7 = 41610116L;
        MultiPolygon multiPolygon7 = emdCdToMultiPolygonMap.get(String.valueOf(code7));
        Region region7 = new Region(code7, address7, point7, multiPolygon7);

        List<Region> regions = new ArrayList<>();
        regions.add(region);
        regions.add(region2);
        regions.add(region3);
        regions.add(region4);
        regions.add(region5);
        regions.add(region6);
        regions.add(region7);

        regionRepository.saveAll(regions);
    }

    private List<List<String>> readToList() {
        List<List<String>> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(csvResource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] token = line.split(",");
                List<String> tempList = new ArrayList<>(Arrays.asList(token));
                list.add(tempList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    private void saveMultipolygon() {
        try (InputStream inputStream = jsonResource.getInputStream()) {
            FeatureJSON featureJSON = new FeatureJSON();
            SimpleFeatureCollection featureCollection = (SimpleFeatureCollection) featureJSON.readFeatureCollection(inputStream);

            try (SimpleFeatureIterator iterator = featureCollection.features()) {
                while (iterator.hasNext()) {
                    SimpleFeature feature = iterator.next();
                    String emdCd = (String) feature.getAttribute("EMD_CD");
                    MultiPolygon multiPolygon = (MultiPolygon) feature.getDefaultGeometry();
                    multiPolygon.setSRID(4326);
                    emdCdToMultiPolygonMap.put(emdCd, multiPolygon);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
