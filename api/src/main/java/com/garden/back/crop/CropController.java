package com.garden.back.crop;

import com.garden.back.crop.domain.repository.response.FindAllCropsPostResponse;
import com.garden.back.crop.domain.repository.response.FindCropsPostDetailsResponse;
import com.garden.back.crop.request.AssignBuyerRequest;
import com.garden.back.crop.request.CropsPostCreateRequest;
import com.garden.back.crop.request.CropsPostsUpdateRequest;
import com.garden.back.crop.request.FindAllCropsPostRequest;
import com.garden.back.crop.service.CropCommandService;
import com.garden.back.crop.service.CropQueryService;
import com.garden.back.crop.service.response.MonthlyRecommendedCropsResponse;
import com.garden.back.global.LocationBuilder;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/crops")
public class CropController {

    private final CropCommandService cropCommandService;
    private final CropQueryService cropQueryService;

    public CropController(
        CropCommandService cropCommandService,
        CropQueryService cropQueryService
    ) {
        this.cropCommandService = cropCommandService;
        this.cropQueryService = cropQueryService;
    }

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MonthlyRecommendedCropsResponse> getMonthlyRecommendedCrops() {
        return ResponseEntity.ok(cropCommandService.getMonthlyRecommendedCrops());
    }

    @GetMapping(
        value = "/posts",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FindAllCropsPostResponse> getAllCropsPost(
        @ModelAttribute @Valid FindAllCropsPostRequest request
    ) {
        return ResponseEntity.ok(cropQueryService.findAll(request.toRepositoryDto()));
    }

    @GetMapping(
        value = "/posts/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FindCropsPostDetailsResponse> getDetailPost(
        @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(cropQueryService.findCropsPostDetails(id));
    }

    @PostMapping(
        value = "/posts",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> createCropsPost(
        @RequestPart(value = "texts", required = true) @Valid CropsPostCreateRequest request,
        @RequestPart(value = "images", required = false) List<MultipartFile> images,
        @CurrentUser LoginUser loginUser
    ) {
        URI uri = LocationBuilder.buildLocation(cropCommandService.createCropsPost(request.toServiceRequest(images), loginUser.memberId()));
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping(
        value = "/posts/{id}",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> updateCropsPost(
        @PathVariable("id") Long id,
        @RequestPart(value = "texts", required = true) @Valid CropsPostsUpdateRequest request,
        @RequestPart(value = "images", required = false) List<MultipartFile> images,
        @CurrentUser LoginUser loginUser
    ) {
        cropCommandService.updateCropsPost(id, request.toServiceDto(images), loginUser.memberId());
        return ResponseEntity.ok().build();
    }

    @PatchMapping(
        value = "/posts/{id}/assign-buyer",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> assignBuyer(
        @PathVariable("id") Long id,
        @CurrentUser LoginUser loginUser,
        @RequestBody @Valid AssignBuyerRequest request
    ) {
        cropCommandService.assignCropBuyer(id, loginUser.memberId(), request.toServiceDto());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/posts/{id}/bookmarks")
    public ResponseEntity<Void> addBookmarkCropsPost(
        @PathVariable("id") Long id,
        @CurrentUser LoginUser loginUser
    ) {
        URI uri = LocationBuilder.buildLocation(cropCommandService.addCropsBookmark(id, loginUser.memberId()));
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(
        value = "/posts/{id}/bookmarks"
    )
    public ResponseEntity<Void> deleteCropsBookmark(
        @PathVariable("id") Long id,
        @CurrentUser LoginUser loginUser
    ) {
        cropCommandService.deleteCropsBookmark(id, loginUser.memberId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
        value = "/posts/{id}"
    )
    public ResponseEntity<Void> deleteCropsPost(
        @PathVariable("id") Long id,
        @CurrentUser LoginUser loginUser
    ) {
        cropCommandService.deleteCropsPost(id, loginUser.memberId());
        return ResponseEntity.noContent().build();
    }
}