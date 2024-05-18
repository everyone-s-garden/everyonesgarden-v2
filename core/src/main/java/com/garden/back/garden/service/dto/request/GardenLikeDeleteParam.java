package com.garden.back.garden.service.dto.request;

public record GardenLikeDeleteParam(
        Long memberId,
        Long gardenLikeId
) {
    public static GardenLikeDeleteParam to(
        Long memberId,
        Long gardenLikeId
    ) {
        return new GardenLikeDeleteParam(memberId, gardenLikeId);
    }
}
