package com.garden.back.garden.service.dto.request;

public record GardenLikeCreateParam(
    Long memberId,
    Long gardenId
) {
    public static GardenLikeCreateParam to(
        Long memberId,
        Long gardenId
    ) {
        return new GardenLikeCreateParam(
            memberId,
            gardenId
        );
    }
}
