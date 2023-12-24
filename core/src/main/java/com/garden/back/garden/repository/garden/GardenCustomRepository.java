package com.garden.back.garden.repository.garden;

import com.garden.back.garden.repository.garden.dto.GardensByComplexes;
import com.garden.back.garden.repository.garden.dto.request.GardenByComplexesRepositoryRequest;

public interface GardenCustomRepository {
    GardensByComplexes getGardensByComplexes(GardenByComplexesRepositoryRequest request);
}
