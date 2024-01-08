package com.garden.back.crop.service;

import com.garden.back.crop.domain.CropBookmark;
import com.garden.back.crop.domain.CropPost;
import com.garden.back.crop.domain.repository.CropCommandRepository;
import com.garden.back.crop.infra.MonthlyRecommendedCropsFetcher;
import com.garden.back.crop.service.request.AssignBuyerServiceRequest;
import com.garden.back.crop.service.request.CreateCropsPostServiceRequest;
import com.garden.back.crop.service.request.UpdateCropsPostServiceRequest;
import com.garden.back.crop.service.response.MonthlyRecommendedCropsResponse;
import com.garden.back.global.image.ParallelImageUploader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropCommandService {

    private final MonthlyRecommendedCropsFetcher monthlyRecommendedCropsFetcher;
    private final CropCommandRepository cropCommandRepository;
    private final ParallelImageUploader parallelImageUploader;
    private final CropCommandValidator cropCommandValidator;

    private static final String CROP_IMAGE_DIRECTORY = "crops/";

    public CropCommandService(
        MonthlyRecommendedCropsFetcher monthlyRecommendedCropsFetcher,
        CropCommandRepository cropCommandRepository,
        ParallelImageUploader parallelImageUploader,
        CropCommandValidator cropCommandValidator
    ) {
        this.monthlyRecommendedCropsFetcher = monthlyRecommendedCropsFetcher;
        this.cropCommandRepository = cropCommandRepository;
        this.parallelImageUploader = parallelImageUploader;
        this.cropCommandValidator = cropCommandValidator;
    }

    public MonthlyRecommendedCropsResponse getMonthlyRecommendedCrops() {
        return MonthlyRecommendedCropsResponse.from(monthlyRecommendedCropsFetcher.getMonthlyRecommendedCrops());
    }

    //어노테이션 하나 만들어서 save 실패할 때 업로드 한 이미지 삭제 할 수 있게 리팩토링 하거나 이대로 쓰거나
    public Long createCropsPost(CreateCropsPostServiceRequest request, Long loginUserId) {
        List<String> images = parallelImageUploader.upload(CROP_IMAGE_DIRECTORY, request.images());
        return cropCommandRepository.saveCropPost(request.toEntity(images, loginUserId));
    }

    public void updateCropsPost(Long id, UpdateCropsPostServiceRequest request, Long loginUserId) {
        CropPost cropPost = cropCommandValidator.validateCropPostUpdatable(id, request.getAddedImageSize(), request.getDeletedImageSize());
        parallelImageUploader.delete(CROP_IMAGE_DIRECTORY, request.deletedImages());
        List<String> images = parallelImageUploader.upload(CROP_IMAGE_DIRECTORY, request.images());
        cropCommandRepository.updateCropPost(request.toRepositoryDto(cropPost, images, loginUserId));
    }

    public Long addCropsBookmark(Long id, Long loginUserId) {
        cropCommandValidator.validateCropBookMarkCreatable(id, loginUserId);
        return cropCommandRepository.saveCropBookmark(CropBookmark.create(id, loginUserId));
    }

    public void deleteCropsPost(Long id, Long loginUserId) {
        cropCommandRepository.deleteCropPost(id, loginUserId);
    }

    public void deleteCropsBookmark(Long cropPostId, Long loginUserId) {
        cropCommandRepository.deleteCropBookmark(cropPostId, loginUserId);
    }

    public void assignCropBuyer(Long id, Long loginUserId, AssignBuyerServiceRequest request) {
        cropCommandRepository.assignCropBuyer(id ,loginUserId, request.memberId());
    }
}
