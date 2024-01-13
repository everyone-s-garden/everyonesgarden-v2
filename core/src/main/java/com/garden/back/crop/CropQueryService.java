package com.garden.back.crop;

import com.garden.back.crop.domain.repository.CropQueryRepository;
import com.garden.back.crop.domain.repository.request.FindAllCropsPostRepositoryRequest;
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
}
