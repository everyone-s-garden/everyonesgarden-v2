package com.garden.back.crop;

import com.garden.back.crop.domain.repository.response.FindAllMyBookmarkCropPostsResponse;
import com.garden.back.crop.domain.repository.response.FindAllMyBoughtCropPostsResponse;
import com.garden.back.crop.domain.repository.response.FindAllMyCropPostsResponse;
import com.garden.back.crop.request.FindAllMyBookmarkCropPostsRequest;
import com.garden.back.crop.request.FindAllMyBoughtCropRequest;
import com.garden.back.crop.request.FindAllMyCropPostsRequest;
import com.garden.back.crop.service.CropQueryService;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/my/crops")
public class MyCropController {

    private final CropQueryService cropQueryService;

    public MyCropController(CropQueryService cropQueryService) {
        this.cropQueryService = cropQueryService;
    }

    //- 내가 북마크 한 작물 글
    @GetMapping(
        value = "/bookmarks",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FindAllMyBookmarkCropPostsResponse> findAllByMyBookmark(
        @CurrentUser LoginUser loginUser,
        @ModelAttribute @Valid FindAllMyBookmarkCropPostsRequest request
    ) {
        return ResponseEntity.ok(cropQueryService.findAllByMyBookmark(loginUser.memberId(), request.toRepositoryDto()));
    }

    //- 내가 작성한 작물 글
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FindAllMyCropPostsResponse> findAllByMyCropPosts(
        @CurrentUser LoginUser loginUser,
        @ModelAttribute @Valid FindAllMyCropPostsRequest request
    ) {
        return ResponseEntity.ok(cropQueryService.findAllMyCropPosts(loginUser.memberId(), request.toRepositoryDto()));
    }

    // - 내가 구매한 작물 글
    @GetMapping(
        value = "/buy",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FindAllMyBoughtCropPostsResponse> findAllMyBoughtCrops(
        @CurrentUser LoginUser loginUser,
        @ModelAttribute @Valid FindAllMyBoughtCropRequest request
    ) {
        return ResponseEntity.ok(cropQueryService.findAllMyBoughtCrops(request.toRepositoryRequest(loginUser.memberId())));
    }
}
