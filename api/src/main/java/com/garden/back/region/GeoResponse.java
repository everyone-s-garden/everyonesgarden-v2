package com.garden.back.region;

public record GeoResponse(
    String latitude,
    String longitude
) {

    public static GeoResponse of(GeoResult result) {
        return new GeoResponse(
            result.latitude(),
            result.longitude()
        );
    }
}
