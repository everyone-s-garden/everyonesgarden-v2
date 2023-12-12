package com.garden.back.garden.service.dto;

public record GardenByNameParam(
        String gardenName,
        int pageNumber
) {
}
