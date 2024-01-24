package com.garden.back.region;

public record LocationSearchServiceRequest(
    String fullAddress,
    Integer offset,
    Integer limit
) {
}
