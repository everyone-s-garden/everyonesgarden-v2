package com.garden.back.garden.controller.dto.request;

import com.garden.back.garden.service.dto.request.GardenLikeCreateParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GardenLikeCreateRequest (
        @NotNull
        @Positive
        Long gardenId
) {
    public static GardenLikeCreateParam of(Long memberId, GardenLikeCreateRequest gardenLikeCreateRequest) {
        return new GardenLikeCreateParam(
                memberId,
                gardenLikeCreateRequest.gardenId()
        );
    }
}
