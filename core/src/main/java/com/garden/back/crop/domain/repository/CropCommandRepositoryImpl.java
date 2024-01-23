package com.garden.back.crop.domain.repository;

import com.garden.back.crop.domain.CropBookmark;
import com.garden.back.crop.domain.CropPost;
import com.garden.back.crop.domain.repository.request.UpdateCropsPostRepositoryRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CropCommandRepositoryImpl implements CropCommandRepository {

    private final CropJpaRepository cropJpaRepository;
    private final CropBookmarkJpaRepository cropBookmarkJpaRepository;

    private static final String DEFAULT_CROP_NOT_FOUND_MESSAGE = "존재하지 않는 작물 게시글 입니다.";

    public CropCommandRepositoryImpl(
        CropJpaRepository cropJpaRepository,
        CropBookmarkJpaRepository cropBookmarkJpaRepository
    ) {
        this.cropJpaRepository = cropJpaRepository;
        this.cropBookmarkJpaRepository = cropBookmarkJpaRepository;
    }

    @Override
    @Transactional
    public Long saveCropPost(CropPost cropPost) {
        return cropJpaRepository.save(cropPost).getId();
    }

    @Transactional
    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Override
    public void updateCropPost(UpdateCropsPostRepositoryRequest request) {
        CropPost crop = request.crop();

        crop.update(
            request.title(),
            request.content(),
            request.cropCategory(),
            request.price(),
            request.priceProposal(),
            request.tradeType(),
            request.addedImages(),
            request.deletedImages(),
            request.loginUserId(),
            request.tradeStatus(),
            request.memberAddressId()
        );
    }

    @Transactional
    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Override
    public Long saveCropBookmark(CropBookmark cropBookmark) {
        CropPost cropPost = cropJpaRepository.findById(cropBookmark.getCropPostId()).orElseThrow(() -> new EntityNotFoundException(DEFAULT_CROP_NOT_FOUND_MESSAGE));
        cropPost.increaseBookmarkCount();
        return cropBookmarkJpaRepository.save(cropBookmark).getId();
    }

    @Override
    @Transactional
    public void deleteCropPost(Long id, Long loginUserId) {
        CropPost cropPost = cropJpaRepository.findByIdAndCropPostAuthorId(id, loginUserId).orElseThrow(() -> new IllegalArgumentException(DEFAULT_CROP_NOT_FOUND_MESSAGE));
        cropJpaRepository.delete(cropPost);
    }

    @Override
    @Transactional
    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void deleteCropBookmark(Long cropPostId, Long loginUserId) {
        CropPost cropPost = cropJpaRepository.findById(cropPostId).orElseThrow(() -> new EntityNotFoundException(DEFAULT_CROP_NOT_FOUND_MESSAGE));
        cropPost.decreaseBookmarkCount();
        CropBookmark cropBookmark = cropBookmarkJpaRepository.findByCropPostIdAndBookMarkOwnerId(cropPostId, loginUserId).orElseThrow(() -> new IllegalArgumentException("해당 게시글에 등록한 북마크가 없습니다."));
        cropBookmarkJpaRepository.delete(cropBookmark);
    }

    @Override
    @Transactional
    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void assignCropBuyer(Long id, Long loginUserId, Long cropBuyerId) {
        CropPost cropPost = cropJpaRepository.findByIdAndCropPostAuthorId(id, loginUserId).orElseThrow(() -> new EntityNotFoundException(DEFAULT_CROP_NOT_FOUND_MESSAGE));
        cropPost.assignBuyer(cropBuyerId);
    }

}
