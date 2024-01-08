package com.garden.back.crop.domain.repository;

import com.garden.back.crop.FindAllCropsPostResponse;
import com.garden.back.crop.FindCropsPostDetailsResponse;
import com.garden.back.crop.domain.repository.request.FindAllCropsPostRepositoryRequest;

public interface CropQueryRepository {
    FindAllCropsPostResponse findAll(FindAllCropsPostRepositoryRequest request);

    FindCropsPostDetailsResponse findCropPostDetails(Long id);
}
