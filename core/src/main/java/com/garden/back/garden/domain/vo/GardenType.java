package com.garden.back.garden.domain.vo;

import java.util.Objects;
import java.util.Set;

public enum GardenType {
    ALL,
    PUBLIC,
    PRIVATE,
    EVERY_FARM_PUBLIC,
    EVERY_FARM_PRIVATE;

    private static final Set<String> EVERY_PRIVATE_GARDEN_NAMES = Set.of("민간", "개인");

    public static String isAllType(String gardenType) {
        Objects.requireNonNull(gardenType, "garden type이 null 값일 수 없습니다.");

        if (gardenType.equals(GardenType.ALL.name())) {
            return String.format("'%s','%s','%s','%s'",
                GardenType.PUBLIC, GardenType.PRIVATE, GardenType.EVERY_FARM_PUBLIC, GardenType.EVERY_FARM_PRIVATE);
        }

        return String.format("'%s'", GardenType.valueOf(gardenType));
    }

    public static GardenType getOpenAPIGardenType(String gardenType) {
        return EVERY_PRIVATE_GARDEN_NAMES.stream()
            .anyMatch(gardenType::contains) ? EVERY_FARM_PRIVATE : EVERY_FARM_PUBLIC;
    }

}
