package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.garden.dto.response.GardenLocationRepositoryResponse;

public record GardenLocationResult(
    Double latitude,
    Double longitude
) {

    public static GardenLocationResult to(GardenLocationRepositoryResponse response) {
        return new GardenLocationResult(
            response.getLatitude(),
            response.getLongitude()
        );
    }
}
