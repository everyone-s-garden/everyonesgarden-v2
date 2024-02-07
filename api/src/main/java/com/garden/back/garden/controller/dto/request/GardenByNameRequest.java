package com.garden.back.garden.controller.dto.request;

import com.garden.back.garden.service.dto.request.GardenByNameParam;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record GardenByNameRequest(
        @NotBlank(message = "텃밭 이름은 null이거나 빈 값일 수 없습니다.")
        String gardenName,

        @NotNull(message = "페이지 수는 필수입니다.")
        @PositiveOrZero(message = "페이지 수는 음수일 수 없습니다.")
        Integer pageNumber
) {
    public static GardenByNameParam to(GardenByNameRequest request) {
        return new GardenByNameParam(
                request.gardenName(),
                request.pageNumber()
        );
    }

}
