package com.garden.back.garden.dto.request;

import com.garden.back.garden.service.dto.request.GardenByComplexesParam;
import com.garden.back.global.validation.EnumValue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record GardenByComplexesRequest(
        @NotBlank
        @EnumValue(enumClass = GardenType.class)
        String gardenType,

        @PositiveOrZero
        Integer pageNumber,

        @DecimalMin(value = "-90.0", message = "위도는 -90.0 보다 크거나 같아야 한다.")
        @DecimalMax(value = "90.0", message = "위도는 90.0보다 같거나 작아야 한다.")
        Double startLat,

        @DecimalMin(value = "-180.0", message = "경도는 -180.0 보다 크거나 같아야 한다.")
        @DecimalMax(value = "180.0", message = "경도는 180.0 보다 같거나 작아야 한다.")
        Double startLong,

        @DecimalMin(value = "-90.0", message = "위도는 -90.0 보다 크거나 같아야 한다.")
        @DecimalMax(value = "90.0", message = "위도는 90.0보다 같거나 작아야 한다.")
        Double endLat,

        @DecimalMin(value = "-180.0", message = "경도는 -180.0 보다 크거나 같아야 한다.")
        @DecimalMax(value = "180.0", message = "경도는 180.0 보다 같거나 작아야 한다.")
        Double endLong
) {
    public static GardenByComplexesParam to(GardenByComplexesRequest request) {
        return new GardenByComplexesParam(
                request.gardenType(),
                request.pageNumber(),
                request.startLat(),
                request.startLong(),
                request.endLat(),
                request.endLong()
        );
    }
}
