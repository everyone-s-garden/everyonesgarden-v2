package com.garden.back.garden.controller.dto.response;

public record GardenLikeCreateResponse(
    long gardenLikeId
) {
    public static GardenLikeCreateResponse to(Long gardenLikeId) {
        return new GardenLikeCreateResponse(gardenLikeId);
    }
}
