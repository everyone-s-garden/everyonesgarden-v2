package com.garden.back.garden.service.dto.request;

public record GardenByNameParam(
        String gardenName,
        int pageNumber
) {
}
