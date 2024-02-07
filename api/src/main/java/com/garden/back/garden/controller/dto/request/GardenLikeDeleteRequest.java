package com.garden.back.garden.controller.dto.request;

import com.garden.back.garden.service.dto.request.GardenLikeDeleteParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GardenLikeDeleteRequest (
        @NotNull
        @Positive
        Long gardenId
) {
    public static GardenLikeDeleteParam of(Long memberId, GardenLikeDeleteRequest request) {
        return new GardenLikeDeleteParam(
                memberId,
                request.gardenId
        );
    }
}
