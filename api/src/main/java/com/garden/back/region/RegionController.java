package com.garden.back.region;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/regions")
public class RegionController {
    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationSearchApiResponses> findByRegionName(@ModelAttribute @Valid LocationSearchApiRequest request) {
        return ResponseEntity.ok(regionService.autoCompleteRegion(request.toServiceRequest()));
    }

    @GetMapping(
        value = "/geocode",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeoResponse> getLatitudeAndLongitude(
        @RequestParam String fullAddress ) {
        return ResponseEntity.ok(
            GeoResponse.of(regionService.getLatitudeAndLongitude(fullAddress)));
    }
}
