package com.garden.back.garden.service;

import com.garden.back.garden.repository.GardenRepository;
import com.garden.back.garden.service.dto.GardenByNameParam;
import com.garden.back.garden.service.dto.GardenByNameResults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GardenReadService {

    private static final int GARDEN_BY_NAME_PAGE_SIZE = 10;
    private final GardenRepository gardenRepository;

    public GardenReadService(GardenRepository gardenRepository) {
        this.gardenRepository = gardenRepository;
    }

    @Transactional(readOnly = true)
    public GardenByNameResults getGardensByName(GardenByNameParam gardenByNameParam) {
        Pageable pageable = PageRequest.of(gardenByNameParam.pageNumber(), GARDEN_BY_NAME_PAGE_SIZE);

        return GardenByNameResults.to(gardenRepository.findGardensByName(gardenByNameParam.gardenName(), pageable));
    }

}
