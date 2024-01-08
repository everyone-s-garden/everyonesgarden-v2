package com.garden.back.crop.domain.repository;


import com.garden.back.crop.domain.CropBookmark;
import com.garden.back.crop.domain.CropPost;
import com.garden.back.crop.domain.repository.request.UpdateCropsPostRepositoryRequest;

public interface CropCommandRepository {

    Long saveCropPost(CropPost cropPost);

    void updateCropPost(UpdateCropsPostRepositoryRequest request);

    Long saveCropBookmark(CropBookmark cropBookmark);

    void deleteCropPost(Long id, Long loginUserId);

    void deleteCropBookmark(Long id, Long loginUserId);

    void assignCropBuyer(Long id, Long loginUserId, Long buyerId);
}
