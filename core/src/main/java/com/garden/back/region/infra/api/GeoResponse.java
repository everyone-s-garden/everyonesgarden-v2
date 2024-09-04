package com.garden.back.region.infra.api;

import java.util.List;

public record GeoResponse(
    String status,
    Meta meta,
    List<Address> addresses
) {
    public record Meta(
        int totalCount,
        int page,
        int count
    ) {
    }

    public record Address(
        String roadAddress,
        String jibunAddress,
        String englishAddress,
        List<AddressElement> addressElements,
        String x,
        String y,
        double distance
    ) {
        public record AddressElement(
            List<String> types,
            String longName,
            String shortName,
            String code
        ) {
        }

    }
}


