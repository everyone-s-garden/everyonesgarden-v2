package com.garden.back.region;

import java.util.List;

public record LocationSearchApiResponses(
    List<LocationSearchResponse> locationSearchResponses
) {
    public record LocationSearchResponse(
        String position,
        Double latitude,
        Double longitude
    ) {
        public static LocationSearchResponse from(Region region) {
            return new LocationSearchResponse(region.getAddress().getFullAddress(), region.getPoint().getX(), region.getPoint().getY());
        }
    }

    public static LocationSearchApiResponses from(List<LocationSearchResponse> responses) {
        return new LocationSearchApiResponses(responses);
    }

}