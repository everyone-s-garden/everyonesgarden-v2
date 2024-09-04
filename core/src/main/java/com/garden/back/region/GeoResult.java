package com.garden.back.region;

import com.garden.back.region.infra.api.GeoResponse;

public record GeoResult(
    String latitude,
    String longitude
) {
    private static final int detailResultIndex = 0;

    public static GeoResult of(GeoResponse response) {
        return new GeoResult(
            response.addresses().get(detailResultIndex).y(),
            response.addresses().get(detailResultIndex).x()
        );
    }
}
