package com.garden.back.garden.repository.garden.dto.request;
public record GardenByComplexesRepositoryRequest(
        String gardenTypes,
        String diagonal,
        int pageSize,
        int pageNumber
) {
}
