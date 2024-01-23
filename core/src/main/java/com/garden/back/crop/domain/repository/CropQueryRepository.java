package com.garden.back.crop.domain.repository;

import com.garden.back.crop.domain.repository.request.FindAllMyBookmarkCropPostsRepositoryRequest;
import com.garden.back.crop.domain.repository.request.FindAllMyBoughtCropPostsRepositoryRequest;
import com.garden.back.crop.domain.repository.request.FindAllMyCropPostsRepositoryRequest;
import com.garden.back.crop.domain.repository.response.*;
import com.garden.back.crop.domain.repository.request.FindAllCropsPostRepositoryRequest;

public interface CropQueryRepository {
    FindAllCropsPostResponse findAll(FindAllCropsPostRepositoryRequest request);

    FindCropsPostDetailsResponse findCropPostDetails(Long id);

    FindAllMyBookmarkCropPostsResponse findAllByMyBookmark(Long loginUserId, FindAllMyBookmarkCropPostsRepositoryRequest request);

    FindAllMyCropPostsResponse findAllMyCropPosts(Long loginUserId, FindAllMyCropPostsRepositoryRequest request);

    FindAllMyBoughtCropPostsResponse findAllMyBoughtCrops(FindAllMyBoughtCropPostsRepositoryRequest request);
}
