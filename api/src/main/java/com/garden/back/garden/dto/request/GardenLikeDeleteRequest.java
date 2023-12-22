package com.garden.back.garden.dto.request;

import com.garden.back.garden.service.dto.request.GardenLikeDeleteParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record GardenLikeDeleteRequest (
        @NotNull
        @PositiveOrZero
        Long gardenId
) {
    public static GardenLikeDeleteParam of(Long memberId, GardenLikeDeleteRequest request) {
        return new GardenLikeDeleteParam(
                memberId,
                request.gardenId
        );
    }
}
