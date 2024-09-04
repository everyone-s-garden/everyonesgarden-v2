package com.garden.back.region;

import com.garden.back.region.infra.api.NaverGeoCodeClient;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    private final NaverGeoCodeClient naverGeoCodeClient;

    public RegionService(RegionRepository regionRepository,
                         NaverGeoCodeClient naverGeoCodeClient) {
        this.regionRepository = regionRepository;
        this.naverGeoCodeClient = naverGeoCodeClient;
    }

    public LocationSearchApiResponses autoCompleteRegion(LocationSearchServiceRequest request) {
        return LocationSearchApiResponses.from(
            regionRepository.findAllRegions(request.fullAddress(), request.limit(), request.offset())
                .stream()
                .map(LocationSearchApiResponses.LocationSearchResponse::from)
                .toList()
        );
    }

    public GeoResult getLatitudeAndLongitude(String fullAddress) {
        return GeoResult.of(naverGeoCodeClient.getLatitudeAndLongitude(fullAddress));
    }
}
