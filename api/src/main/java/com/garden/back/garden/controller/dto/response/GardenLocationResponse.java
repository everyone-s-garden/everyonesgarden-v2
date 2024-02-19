package com.garden.back.garden.controller.dto.response;

import com.garden.back.garden.service.dto.response.GardenLocationResult;

public record GardenLocationResponse(
    Double latitude,
    Double longitude
) {
    public static GardenLocationResponse to(GardenLocationResult result) {
        return new GardenLocationResponse(
            result.latitude(),
            result.longitude()
        );
    }
}
