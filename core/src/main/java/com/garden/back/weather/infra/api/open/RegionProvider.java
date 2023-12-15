package com.garden.back.weather.infra.api.open;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RegionProvider {

    private final List<Region> regionList = new ArrayList<>();

    @PostConstruct
    public void setUp() {
        regionList.add(new Region(73, 134, "강원도", "11D10000"));
        regionList.add(new Region(60, 127, "서울특별시", "11B00000"));
        regionList.add(new Region(60, 120, "경기도", "11B00000"));
        regionList.add(new Region(91, 77, "경상남도", "11H20000"));
        regionList.add(new Region(89, 91, "경상북도", "11H10000"));
        regionList.add(new Region(58, 74, "광주광역시", "11F20000"));
        regionList.add(new Region(89, 90, "대구광역시", "11H10000"));
        regionList.add(new Region(67, 100, "대전광역시", "11C20000"));
        regionList.add(new Region(98, 76, "부산광역시", "11H20000"));
        regionList.add(new Region(66, 103, "세종특별자치시", "11C20000"));
        regionList.add(new Region(102, 84, "울산광역시", "11H20000"));
        regionList.add(new Region(55, 124, "인천광역시", "11B00000"));
        regionList.add(new Region(51, 67, "전라남도", "11F20000"));
        regionList.add(new Region(63, 89, "전라북도", "11F10000"));
        regionList.add(new Region(52, 38, "제주특별자치도", "11G00000"));
        regionList.add(new Region(68, 100, "충청남도", "11C20000"));
        regionList.add(new Region(69, 107, "충청북도", "11C10000"));
    }

    public List<Region> findAll() {
        return regionList;
    }

    public Optional<Region> findRegionByName(String name) {
        return regionList.stream()
            .filter(region -> region.regionName().equals(name))
            .findFirst();
    }

}