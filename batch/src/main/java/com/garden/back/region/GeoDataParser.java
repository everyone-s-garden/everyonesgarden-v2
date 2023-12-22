package com.garden.back.region;

import lombok.extern.slf4j.Slf4j;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
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
            if (line.get(0).matches(NUMBER_REGX) && Integer.parseInt(line.get(14)) == 0 && line.get(12).length() < 2) {
                String withoutLastTwoChars = line.get(0).substring(0, line.get(0).length() - 2);

                if (line.get(1).length() < 3) continue; //경기도 화성시 새솔동은 정보를 제공 안함.

                String 시도 = line.get(7);
                String 시군구 = line.get(9);
                String 읍면동 = line.get(11);
                Long code = Long.parseLong(withoutLastTwoChars);

                if (시도.equals("강원도")) {
                    시도 = "강원특별자치도";
                    code += 10000000;
                    code -= 1000000;
                }

                if (시도.equals("경상북도") && 시군구.equals("군위군")) { //경북 군위가 대구 군위로 바뀜
                    code -= 20000000;
                    if (읍면동.equals("삼국유사면")) code = 27720370L;
                }
                double x = Double.parseDouble(line.get(2));
                double y = Double.parseDouble(line.get(3));


                Point point = geometryFactory.createPoint(new Coordinate(x, y));
                Address address = new Address(시도, 시군구, 읍면동);
                MultiPolygon multiPolygon = emdCdToMultiPolygonMap.get(String.valueOf(code));

                Region region = new Region(code, address, point, multiPolygon);
                regions.add(region);
            }
        }
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
                    emdCdToMultiPolygonMap.put(emdCd, multiPolygon);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
