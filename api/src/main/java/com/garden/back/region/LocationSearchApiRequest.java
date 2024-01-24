package com.garden.back.region;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record LocationSearchApiRequest(
    String address,

    @PositiveOrZero(message = "0 이상의 offset을 입력해주세요.")
    Integer offset,

    @Positive(message = "0 초과의 limit을 입력해주세요.")
    Integer limit
) {

    public LocationSearchServiceRequest toServiceRequest() {
        return new LocationSearchServiceRequest(address.replaceAll("\\s+", ""), offset, limit);
    }
}