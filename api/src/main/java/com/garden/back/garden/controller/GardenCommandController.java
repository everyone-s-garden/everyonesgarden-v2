package com.garden.back.garden.controller;

import com.garden.back.garden.controller.dto.request.*;
import com.garden.back.garden.controller.dto.response.GardenLikeCreateResponse;
import com.garden.back.garden.service.GardenCommandService;
import com.garden.back.garden.service.dto.request.GardenDeleteParam;
import com.garden.back.garden.service.dto.request.GardenLikeCreateParam;
import com.garden.back.garden.service.dto.request.GardenLikeDeleteParam;
import com.garden.back.garden.service.dto.request.MyManagedGardenDeleteParam;
import com.garden.back.global.LocationBuilder;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v2/gardens")
public class GardenCommandController {
    private static final String GARDEN_DEFAULT_URL = "/v2/gardens/";
    private final GardenCommandService gardenCommandService;

    public GardenCommandController(GardenCommandService gardenCommandService) {
        this.gardenCommandService = gardenCommandService;
    }

    @DeleteMapping(
        path = "/{gardenId}")
    public ResponseEntity<Void> deleteGarden(
        @PathVariable @Positive Long gardenId,
        @CurrentUser LoginUser loginUser) {
        gardenCommandService.deleteGarden(
            GardenDeleteParam.of(loginUser.memberId(), gardenId));

        URI location = URI.create(GARDEN_DEFAULT_URL + gardenId);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .location(location)
            .build();
    }

    @PostMapping(
        path = "/{gardenId}/likes",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GardenLikeCreateResponse> createGardenLike(
        @PathVariable
        @Positive(message = "gardenId는 양수여아 합니다.")
        @NotNull(message = "gardenId는 null일 수 없습니다.")
        Long gardenId,
        @CurrentUser LoginUser loginUser) {
        Long gardenLikeId = gardenCommandService.createGardenLike(
            GardenLikeCreateParam.to(loginUser.memberId(), gardenId));

        return ResponseEntity.created(
                URI.create(String.format(GARDEN_DEFAULT_URL + "/%d/likes/%d", gardenId, gardenLikeId)))
            .body(GardenLikeCreateResponse.to(gardenLikeId));
    }

    @DeleteMapping(
        path = "/likes/{gardenLikeId}")
    public ResponseEntity<Void> deleteGardenLike(
        @PathVariable
        @Positive(message = "gardenLikeId는 양수여아 합니다.")
        @NotNull(message = "gardenLikeId는 null일 수 없습니다.")
        Long gardenLikeId,
        @CurrentUser LoginUser loginUser) {
        gardenCommandService.deleteGardenLike(
            GardenLikeDeleteParam.to(loginUser.memberId(), gardenLikeId));

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> createGarden(
        @RequestPart(value = "gardenImages") List<MultipartFile> gardenImages,
        @RequestPart(value = "gardenCreateRequest") @Valid GardenCreateRequest gardenCreateRequest,
        @CurrentUser LoginUser loginUser
    ) {
        Long gardenId = gardenCommandService.createGarden(
            GardenCreateRequest.to(gardenImages, gardenCreateRequest, loginUser.memberId()));
        URI location = LocationBuilder.buildLocation(gardenId);

        return ResponseEntity.created(location).build();
    }

    @PutMapping(
        value = "/{gardenId}",
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateGarden(
        @PathVariable @Positive Long gardenId,
        @RequestPart(value = "newGardenImages") List<MultipartFile> newGardenImages,
        @RequestPart(value = "gardenUpdateRequest") @Valid GardenUpdateRequest gardenUpdateRequest,
        @CurrentUser LoginUser loginUser
    ) {
        gardenCommandService.updateGarden(GardenUpdateRequest.to(
            gardenId,
            newGardenImages,
            gardenUpdateRequest,
            loginUser.memberId()));

        URI location = URI.create(GARDEN_DEFAULT_URL + gardenId);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .location(location)
            .build();
    }

    @DeleteMapping(
        value = "/my-managed/{myManagedGardenId}"
    )
    public ResponseEntity<Void> deletedMyManagedGarden(
        @PathVariable @Positive Long myManagedGardenId,
        @CurrentUser LoginUser loginUser
    ) {
        gardenCommandService.deleteMyManagedGarden(MyManagedGardenDeleteParam.of(
            myManagedGardenId,
            loginUser.memberId()));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(
        value = "/my-managed",
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Void> createMyManagedGarden(
        @RequestPart(value = "gardenImage", required = false) MultipartFile newGardenImage,
        @RequestPart(value = "myManagedGardenCreateRequest") @Valid MyManagedGardenCreateRequest request,
        @CurrentUser LoginUser loginUser
    ) {
        Long myManagedGardenId = gardenCommandService.createMyManagedGarden(
            request.of(newGardenImage,loginUser.memberId()));
        URI location = LocationBuilder.buildLocation(myManagedGardenId);

        return ResponseEntity.created(location).build();
    }

    @PutMapping(
        value = "/my-managed/{myManagedGardenId}",
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Void> updateMyManagedGarden(
        @PathVariable @Positive Long myManagedGardenId,
        @RequestPart(value = "gardenImage", required = false) MultipartFile newGardenImage,
        @RequestPart(value = "myManagedGardenUpdateRequest") @Valid MyManagedGardenUpdateRequest request,
        @CurrentUser LoginUser loginUser
    ) {
        Long updatedMyManagedGardenId = gardenCommandService.updateMyManagedGarden(
            request.toMyManagedGardenUpdateParam(
                myManagedGardenId,
                newGardenImage,
                loginUser.memberId()
            ));
        URI location = URI.create("/v2/gardens/my-managed/" + updatedMyManagedGardenId);

        return ResponseEntity.noContent().location(location).build();
    }

}
