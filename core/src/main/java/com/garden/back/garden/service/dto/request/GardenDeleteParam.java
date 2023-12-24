package com.garden.back.garden.service.dto.request;

public record GardenDeleteParam (
        Long memberId,
        Long gardenId
) {
    public static GardenDeleteParam of(
            Long memberId,
            Long gardenId
    ){
        return new GardenDeleteParam(
                memberId,
                gardenId
        );
    }
}
