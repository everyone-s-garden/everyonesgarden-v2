package com.garden.back.garden;

import com.garden.back.garden.dto.request.*;
import com.garden.back.garden.service.GardenCommandService;
import com.garden.back.garden.service.dto.request.GardenDeleteParam;
import com.garden.back.garden.service.dto.request.MyManagedGardenDeleteParam;
import com.garden.back.global.LocationBuilder;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import jakarta.validation.Valid;
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
    private static final String GARDEN_DEFAULT_URL = "/v2/gardens";
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
        path = "/likes",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createGardenLike(
        @RequestBody @Valid GardenLikeCreateRequest gardenLikeCreateRequest,
        @CurrentUser LoginUser loginUser) {
        Long gardenLikeId = gardenCommandService.createGardenLike(
            GardenLikeCreateRequest.of(loginUser.memberId(), gardenLikeCreateRequest));

        return ResponseEntity.created(URI.create(GARDEN_DEFAULT_URL + gardenLikeId)).build();
    }

    @DeleteMapping(
        path = "/likes",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteGardenLike(
        @RequestBody @Valid GardenLikeDeleteRequest gardenLikeDeleteRequest,
        @CurrentUser LoginUser loginUser) {
        gardenCommandService.deleteGardenLike(
            GardenLikeDeleteRequest.of(loginUser.memberId(), gardenLikeDeleteRequest));

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> createGarden(
        @RequestPart(value = "gardenImages", required = false) List<MultipartFile> gardenImages,
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
        @RequestPart(value = "newGardenImages", required = false) List<MultipartFile> newGardenImages,
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
            MyManagedGardenCreateRequest.of(newGardenImage, request, loginUser.memberId()));
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
            MyManagedGardenUpdateRequest.to(
                myManagedGardenId,
                newGardenImage,
                request,
                loginUser.memberId()
            ));
        URI location = URI.create("/v2/gardens/my-managed/" + updatedMyManagedGardenId);

        return ResponseEntity.noContent().location(location).build();
    }

}
