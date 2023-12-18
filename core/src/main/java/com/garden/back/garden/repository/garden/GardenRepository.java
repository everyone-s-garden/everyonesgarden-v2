package com.garden.back.garden.repository.garden;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.repository.garden.dto.GardenByName;
import com.garden.back.garden.repository.garden.dto.GardenGetAll;
import com.garden.back.garden.repository.garden.dto.GardensByComplexes;
import com.garden.back.garden.repository.garden.dto.request.GardenByComplexesRepositoryRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface GardenRepository {
    Slice<GardenByName> findGardensByName( String gardenName, Pageable pageable);

    Slice<GardenGetAll> getAllGardens(Pageable pageable, Long memberId);

    GardensByComplexes getGardensByComplexes(GardenByComplexesRepositoryRequest request);

    Garden save(Garden garden);

}
