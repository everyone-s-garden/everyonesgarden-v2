package com.garden.back.garden;


import com.garden.back.garden.dto.request.GardenByNameRequest;
import com.garden.back.garden.dto.response.GardenByNameResponses;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.garden.service.dto.GardenByNameParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/garden")
public class GardenController {

    private final GardenReadService gardenReadService;

    public GardenController(GardenReadService gardenReadService) {
        this.gardenReadService = gardenReadService;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GardenByNameResponses> getGardenNames(
            @ModelAttribute GardenByNameRequest gardenByNameRequest) {
        GardenByNameParam gardenByNameParam = GardenByNameRequest.to(gardenByNameRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(GardenByNameResponses.to(gardenReadService.getGardensByName(gardenByNameParam)));
    }

}
