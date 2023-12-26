package com.garden.back.garden;

import com.garden.back.garden.dto.request.*;
import com.garden.back.garden.dto.response.*;
import com.garden.back.garden.service.GardenCommandService;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.garden.service.dto.request.GardenByNameParam;
import com.garden.back.garden.service.dto.request.GardenDeleteParam;
import com.garden.back.garden.service.dto.request.MyManagedGardenDeleteParam;
import com.garden.back.garden.service.dto.response.GardenByComplexesResults;
import com.garden.back.garden.service.dto.response.GardenDetailResult;
import com.garden.back.global.LocationBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

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

        URI location = URI.create("/v2/gardens/" + gardenId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .location(location)
                .build();
    }

    @GetMapping(
            path = "/mine",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenMineResponses> getMyGarden() {
        Long memberId = 1L;
        return ResponseEntity.status(HttpStatus.OK)
                .body(GardenMineResponses.to(gardenReadService.getMyGarden(memberId)));

    }

    @GetMapping(
            path = "/likes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenLikeByMemberResponses> getLikeGardenByMember() {
        Long memberId = 1L;
        return ResponseEntity.status(HttpStatus.OK)
                .body(GardenLikeByMemberResponses.to(gardenReadService.getLikeGardensByMember(memberId)));
    }

    @PostMapping(
            path = "/likes",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createGardenLike(
            @RequestBody @Valid GardenLikeCreateRequest gardenLikeCreateRequest) {
        Long memberId = 1L;
        Long gardenLikeId = gardenCommandService.createGardenLike(
                GardenLikeCreateRequest.of(memberId, gardenLikeCreateRequest));

        return ResponseEntity.created(URI.create("/v2/gardens/" + gardenLikeId)).build();
    }

    @DeleteMapping(
            path = "/likes",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteGardenLike(
            @RequestBody @Valid GardenLikeDeleteRequest gardenLikeDeleteRequest) {
        Long memberId = 1L;
        gardenCommandService.deleteGardenLike(
                GardenLikeDeleteRequest.of(memberId, gardenLikeDeleteRequest));

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> createGarden(
            @RequestPart(value = "gardenImages", required = false) List<MultipartFile> gardenImages,
            @RequestPart(value = "gardenCreateRequest") @Valid GardenCreateRequest gardenCreateRequest
    ) {
        Long memberId = 1L;
        Long gardenId = gardenCommandService.createGarden(
                GardenCreateRequest.to(gardenImages, gardenCreateRequest, memberId));
        URI location = LocationBuilder.buildLocation(gardenId);

        return ResponseEntity.created(location).build();
    }

    @PutMapping(
            value = "/{gardenId}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateGarden(
            @PathVariable @PositiveOrZero Long gardenId,
            @RequestPart(value = "newGardenImages", required = false) List<MultipartFile> newGardenImages,
            @RequestPart(value = "gardenUpdateRequest") @Valid GardenUpdateRequest gardenUpdateRequest
    ) {
        Long memberId = 1L;
        gardenCommandService.updateGarden(GardenUpdateRequest.to(
                gardenId,
                newGardenImages,
                gardenUpdateRequest,
                memberId));

        URI location = URI.create("/v2/gardens/" + gardenId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .location(location)
                .build();

    }

    @GetMapping(
            value = "/my-managed",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MyManagedGardenGetResponses> getMyManagedGardens() {
        Long memberId = 1L;
        return ResponseEntity.status(HttpStatus.OK)
                .body(MyManagedGardenGetResponses.to(gardenReadService.getMyManagedGarden(memberId)));
    }

    @DeleteMapping(
            value = "/my-managed/{myManagedGardenId}"
    )
    public ResponseEntity<Void> deletedMyManagedGarden(
            @PathVariable @PositiveOrZero Long myManagedGardenId
    ) {
        Long memberId = 1L;
        gardenCommandService.deleteMyManagedGarden(MyManagedGardenDeleteParam.of(
                myManagedGardenId,
                memberId));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(
            value = "/my-managed",
            consumes ={MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Void> createMyManagedGarden(
            @RequestPart(value = "gardenImage", required = false) MultipartFile newGardenImage,
            @RequestPart(value = "myManagedGardenCreateRequest") @Valid MyManagedGardenCreateRequest request
    ) {
        Long memberId = 1L;
        Long myManagedGardenId = gardenCommandService.createMyManagedGarden(
                MyManagedGardenCreateRequest.of(newGardenImage, request, memberId));
        URI location = LocationBuilder.buildLocation(myManagedGardenId);

        return ResponseEntity.created(location).build();
    }

    @PutMapping(
            value = "/my-managed/{myManagedGardenId}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Void> updateMyManagedGarden(
            @PathVariable @PositiveOrZero Long myManagedGardenId,
            @RequestPart(value = "gardenImage", required = false) MultipartFile newGardenImage,
            @RequestPart(value = "myManagedGardenUpdateRequest") @Valid MyManagedGardenUpdateRequest request
    ){
        Long memberId = 1L;
        Long updatedMyManagedGardenId = gardenCommandService.updateMyManagedGarden(
                MyManagedGardenUpdateRequest.to(
                        myManagedGardenId,
                        newGardenImage,
                        request,
                        memberId
                ));
        URI location = URI.create("/v2/gardens/my-managed/" + updatedMyManagedGardenId) ;

        return ResponseEntity.noContent().location(location).build();
    }

}
