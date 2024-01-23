package com.garden.back.crop;

import com.garden.back.crop.domain.repository.CropQueryRepository;
import com.garden.back.crop.domain.repository.request.FindAllCropsPostRepositoryRequest;
import com.garden.back.crop.domain.repository.request.FindAllMyBookmarkCropPostsRepositoryRequest;
import com.garden.back.crop.domain.repository.request.FindAllMyBoughtCropPostsRepositoryRequest;
import com.garden.back.crop.domain.repository.request.FindAllMyCropPostsRepositoryRequest;
import com.garden.back.crop.domain.repository.response.*;
import org.springframework.stereotype.Service;

@Service
public class CropQueryService {

    private final CropQueryRepository cropQueryRepository;

    public CropQueryService(CropQueryRepository cropQueryRepository) {
        this.cropQueryRepository = cropQueryRepository;
    }

    public FindAllCropsPostResponse findAll(FindAllCropsPostRepositoryRequest request) {
        return cropQueryRepository.findAll(request);
    }

    public FindCropsPostDetailsResponse findCropsPostDetails(Long id) {
        return cropQueryRepository.findCropPostDetails(id);
    }

    public FindAllMyBookmarkCropPostsResponse findAllByMyBookmark(Long loginUserId, FindAllMyBookmarkCropPostsRepositoryRequest request) {
        return cropQueryRepository.findAllByMyBookmark(loginUserId, request);
    }

    public FindAllMyCropPostsResponse findAllMyCropPosts(Long loginUserId, FindAllMyCropPostsRepositoryRequest request) {
        return cropQueryRepository.findAllMyCropPosts(loginUserId, request);
    }

    public FindAllMyBoughtCropPostsResponse findAllMyBoughtCrops(FindAllMyBoughtCropPostsRepositoryRequest request){
        return cropQueryRepository.findAllMyBoughtCrops(request);
    }
}
