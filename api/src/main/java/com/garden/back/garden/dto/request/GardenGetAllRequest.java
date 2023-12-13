package com.garden.back.garden.dto.request;

import com.garden.back.garden.service.dto.request.GardenGetAllParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record GardenGetAllRequest(
        @NotNull
        @PositiveOrZero(message = "페이지 수는 음수일 수 없습니다.")
        Integer pageNumber,

        @NotNull
        @PositiveOrZero
        Long memberId
) {
    public static GardenGetAllParam to(GardenGetAllRequest gardenGetAllRequest) {
        return new GardenGetAllParam(
                gardenGetAllRequest.pageNumber(),
                gardenGetAllRequest.memberId()
        );
    }
}
