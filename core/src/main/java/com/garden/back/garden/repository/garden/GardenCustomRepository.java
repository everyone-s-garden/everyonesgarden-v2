package com.garden.back.garden.repository.garden;

import com.garden.back.garden.repository.garden.dto.GardensByComplexes;
import com.garden.back.garden.repository.garden.dto.GardensByComplexesWithScroll;
import com.garden.back.garden.repository.garden.dto.request.GardenByComplexesRepositoryRequest;
import com.garden.back.garden.repository.garden.dto.request.GardenByComplexesWithScrollRepositoryRequest;

public interface GardenCustomRepository {
    GardensByComplexesWithScroll getGardensByComplexesWithScroll(GardenByComplexesWithScrollRepositoryRequest request);

    GardensByComplexes getGardensByComplexes(GardenByComplexesRepositoryRequest request);
}
