package com.garden.back.garden.controller.dto.request;

import com.garden.back.garden.facade.GardenDetailFacadeRequest;

public record GardenDetailRequest(
        Long memberId,
        Long gardenId
) {
    public static GardenDetailFacadeRequest of(Long memberId, Long gardenId) {
        return new GardenDetailFacadeRequest(
                memberId,
                gardenId
        );
    }
}
