package com.garden.back.garden.dto.request;

import com.garden.back.garden.service.dto.request.GardenDetailParam;

public record GardenDetailRequest(
        Long memberId,
        Long gardenId
) {
    public static GardenDetailParam of(Long memberId, Long gardenId) {
        return new GardenDetailParam(
                memberId,
                gardenId
        );
    }
}
