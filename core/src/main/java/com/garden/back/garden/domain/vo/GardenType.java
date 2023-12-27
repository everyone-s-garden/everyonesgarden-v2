package com.garden.back.garden.domain.vo;

import java.util.Objects;

public enum GardenType {
    ALL, PUBLIC, PRIVATE;

    public static String isAllType(String gardenType) {
        Objects.requireNonNull(gardenType, "garden type이 null 값일 수 없습니다.");

        if (gardenType.equals(GardenType.ALL.name())) {
            return String.format("'%s','%s'", GardenType.PUBLIC, GardenType.PRIVATE);
        }
        return String.format("'%s'", GardenType.valueOf(gardenType));
    }

}
