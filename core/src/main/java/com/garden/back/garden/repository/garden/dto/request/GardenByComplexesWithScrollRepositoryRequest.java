package com.garden.back.garden.repository.garden.dto.request;
public record GardenByComplexesWithScrollRepositoryRequest(
        String gardenTypes,
        String diagonal,
        int pageSize,
        int pageNumber
) {
}
