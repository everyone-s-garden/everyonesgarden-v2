package com.garden.back.region;

import lombok.Getter;

@Getter
public enum RegionColumn {
    EMD_CD(0),
    LONGITUDE(2),
    LATITUDE(3),
    CITY(7),
    COUNTRY(9),
    TOWN(11),
    TOWN_CNT(14),
    COMPLEX_COUNTRY_NAME(12),
    POINT_NAME(1);

    private final int index;

    RegionColumn(int index) {
        this.index = index;
    }
}
