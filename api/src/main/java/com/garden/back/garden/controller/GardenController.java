package com.garden.back.garden.controller;

import com.garden.back.garden.controller.dto.request.GardenByComplexesRequest;
import com.garden.back.garden.controller.dto.request.GardenByNameRequest;
import com.garden.back.garden.controller.dto.request.GardenDetailRequest;
import com.garden.back.garden.controller.dto.response.*;
import com.garden.back.garden.facade.GardenDetailFacadeResponse;
import com.garden.back.garden.facade.GardenFacade;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.garden.service.dto.request.GardenByNameParam;
import com.garden.back.garden.service.dto.response.GardenByComplexesResults;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/gardens")
public class GardenController {

    private final GardenReadService gardenReadService;
    private final GardenFacade gardenFacade;

    public GardenController(GardenReadService gardenReadService, GardenFacade gardenFacade) {
        this.gardenReadService = gardenReadService;
        this.gardenFacade = gardenFacade;
    }

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenByNameResponses> getGardenNames(
        @ModelAttribute @Valid GardenByNameRequest gardenByNameRequest) {
        GardenByNameParam gardenByNameParam = GardenByNameRequest.to(gardenByNameRequest);

        return ResponseEntity.status(HttpStatus.OK)
            .body(GardenByNameResponses.to(gardenReadService.getGardensByName(gardenByNameParam)));
    }

    @GetMapping(
        path = "/all",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenGetAllResponses> getGardenAll(
        @RequestParam @PositiveOrZero Integer pageNumber) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(GardenGetAllResponses.to(
                gardenReadService.getAllGarden(pageNumber)));
    }

    @GetMapping(
        path = "/by-complexes",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenByComplexesResponses> getGardensByComplexes(
        @ModelAttribute @Valid GardenByComplexesRequest request) {
        GardenByComplexesResults gardensByComplexes
            = gardenReadService.getGardensByComplexes(GardenByComplexesRequest.to(request));

        return ResponseEntity.status(HttpStatus.OK)
            .body(GardenByComplexesResponses.to(gardensByComplexes));
    }

    @GetMapping(
        path = "/{gardenId}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenDetailResponse> getGardenDetail(
        @PathVariable @Positive Long gardenId,
        @CurrentUser LoginUser loginUser) {
        GardenDetailFacadeResponse gardenDetail = gardenFacade.getGardenDetail(
            GardenDetailRequest.of(loginUser.memberId(), gardenId));

        return ResponseEntity.status(HttpStatus.OK)
            .body(GardenDetailResponse.to(gardenDetail));
    }

    @GetMapping(
        path = "/recent",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecentGardenResponses> getRecentGardens(
        @CurrentUser LoginUser loginUser
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(RecentGardenResponses.to(gardenReadService.getRecentGardens(loginUser.memberId())));
    }

    @GetMapping(
        path = "/mine",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenMineResponses> getMyGarden(
        @CurrentUser LoginUser loginUser
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(GardenMineResponses.to(gardenReadService.getMyGarden(loginUser.memberId())));
    }

    @GetMapping(
        path = "/likes",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenLikeByMemberResponses> getLikeGardenByMember(
        @CurrentUser LoginUser loginUser
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(GardenLikeByMemberResponses.to(
                gardenReadService.getLikeGardensByMember(loginUser.memberId())));
    }

    @GetMapping(
        value = "/recent-created",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RecentCreatedGardenResponses> getRecentCreatedGardens(
        @RequestParam @PositiveOrZero Long memberId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(RecentCreatedGardenResponses.to(
                gardenReadService.getRecentCreatedGardens(memberId)));
    }

    @GetMapping(
        value = "/my-managed",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MyManagedGardenGetResponses> getMyManagedGardens(
        @CurrentUser LoginUser loginUser
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(MyManagedGardenGetResponses.to(gardenReadService.getMyManagedGardens(loginUser.memberId())));
    }

    @GetMapping(
        value = "/my-managed/{myManagedGardenId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MyManagedGardenDetailResponse> getDetailMyManagedGarden(
        @PathVariable @Positive Long myManagedGardenId
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(MyManagedGardenDetailResponse.to(
                gardenReadService.getDetailMyManagedGarden(myManagedGardenId)));
    }

    @GetMapping(
        value = "/{gardenId}/locations",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GardenLocationResponse> getGardenLocation(
        @PathVariable @Positive Long gardenId
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(GardenLocationResponse.to(gardenReadService.getGardenLocation(gardenId)));
    }

}
