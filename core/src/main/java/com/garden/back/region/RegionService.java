package com.garden.back.region;

import org.springframework.stereotype.Service;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public LocationSearchApiResponses autoCompleteRegion(LocationSearchServiceRequest request) {
        return LocationSearchApiResponses.from(
            regionRepository.findAllRegions(request.fullAddress(), request.limit(), request.offset())
                .stream()
                .map(LocationSearchApiResponses.LocationSearchResponse::from)
                .toList()
        );
    }
}
