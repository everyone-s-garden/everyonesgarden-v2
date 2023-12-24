package com.garden.back.garden.dto.request;

import com.garden.back.garden.service.dto.request.GardenLikeCreateParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record GardenLikeCreateRequest (
        @NotNull
        @PositiveOrZero
        Long gardenId
) {
    public static GardenLikeCreateParam of(Long memberId, GardenLikeCreateRequest gardenLikeCreateRequest) {
        return new GardenLikeCreateParam(
                memberId,
                gardenLikeCreateRequest.gardenId()
        );
    }
}
