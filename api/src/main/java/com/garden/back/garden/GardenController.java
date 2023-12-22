package com.garden.back.garden;

import com.garden.back.garden.dto.request.*;
import com.garden.back.garden.dto.response.*;
import com.garden.back.garden.service.GardenCommandService;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.garden.service.dto.request.GardenByNameParam;
import com.garden.back.garden.service.dto.request.GardenDeleteParam;
import com.garden.back.garden.service.dto.response.GardenByComplexesResults;
import com.garden.back.garden.service.dto.response.GardenDetailResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v2/gardens")
public class GardenController {

    private final GardenReadService gardenReadService;
    private final GardenCommandService gardenCommandService;

    public GardenController(GardenReadService gardenReadService, GardenCommandService gardenCommandService) {
        this.gardenReadService = gardenReadService;
        this.gardenCommandService = gardenCommandService;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenByNameResponses> getGardenNames(
            @Valid @ModelAttribute GardenByNameRequest gardenByNameRequest) {
        GardenByNameParam gardenByNameParam = GardenByNameRequest.to(gardenByNameRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(GardenByNameResponses.to(gardenReadService.getGardensByName(gardenByNameParam)));
    }

    @GetMapping(
            path = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenGetAllResponses> getGardenAll(
            @PositiveOrZero @RequestParam Integer pageNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GardenGetAllResponses.to(
                        gardenReadService.getAllGarden(pageNumber)));
    }

    @GetMapping(
            path = "/by-complexes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenByComplexesResponses> getGardensByComplexes(
            @Valid @ModelAttribute GardenByComplexesRequest request) {
        GardenByComplexesResults gardensByComplexes
                = gardenReadService.getGardensByComplexes(GardenByComplexesRequest.to(request));

        return ResponseEntity.status(HttpStatus.OK)
                .body(GardenByComplexesResponses.to(gardensByComplexes));

    }

    @GetMapping(
            path = "/{gardenId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenDetailResponse> getGardenDetail(
            @PositiveOrZero @PathVariable Long gardenId) {
        Long memberId = 1L;
        GardenDetailResult gardenDetail = gardenReadService.getGardenDetail(GardenDetailRequest.of(memberId, gardenId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(GardenDetailResponse.to(gardenDetail));
    }

    @GetMapping(
            path = "/recent",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecentGardenResponses> getRecentGardens() {
        Long memberId = 1L;
        return ResponseEntity.status(HttpStatus.OK)
                .body(RecentGardenResponses.to(gardenReadService.getRecentGardens(memberId)));
    }

    @DeleteMapping(
            path = "/{gardenId}")
    public ResponseEntity<Void> deleteGarden(
            @PositiveOrZero @PathVariable Long gardenId) {
        Long memberId = 1L;

        gardenCommandService.deleteGarden(GardenDeleteParam.of(memberId, gardenId));

        URI location = URI.create("/garden/" + gardenId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .location(location)
                .build();
    }

    @GetMapping(
            path = "/mine",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenMineResponses> getMyGarden(Long memberId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GardenMineResponses.to(gardenReadService.getMyGarden(memberId)));

    }

    @GetMapping(
            path = "/likes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenLikeByMemberResponses> getLikeGardenByMember(Long memberId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GardenLikeByMemberResponses.to(gardenReadService.getLikeGardensByMember(memberId)));
    }

    @PostMapping(
            path = "/likes",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenLikeCreateResponse> createGardenLike(
            Long memberId,
            @RequestBody @Valid GardenLikeCreateRequest gardenLikeCreateRequest) {
        gardenCommandService.createGardenLike(
                GardenLikeCreateRequest.of(memberId, gardenLikeCreateRequest));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GardenLikeCreateResponse(true));
    }

    @DeleteMapping(
            path = "/likes",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenLikeDeleteResponse> deleteGardenLike(
            Long memberId,
            @RequestBody @Valid GardenLikeDeleteRequest gardenLikeDeleteRequest) {
        gardenCommandService.deleteGardenLike(
                GardenLikeDeleteRequest.of(memberId, gardenLikeDeleteRequest));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GardenLikeDeleteResponse(true));
    }

}
