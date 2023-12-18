package com.garden.back.garden.model.vo;

import java.util.Objects;

public enum GardenType {
    All, PUBLIC, PRIVATE;

    public static String isAllType(String gardenType) {
        Objects.requireNonNull(gardenType, "garden type이 null 값일 수 없습니다.");

        if (gardenType.equals(GardenType.All.name())) {
            return String.format("'%s','%s'", GardenType.PUBLIC, GardenType.PRIVATE);
        }
        return String.format("'%s'", GardenType.valueOf(gardenType));
    }

}
