package com.garden.back.crop.service;

import com.garden.back.crop.domain.CropPost;
import com.garden.back.crop.domain.repository.CropBookmarkJpaRepository;
import com.garden.back.crop.domain.repository.CropJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CropCommandValidatorImpl implements CropCommandValidator {
    private final CropJpaRepository cropJpaRepository;
    private final CropBookmarkJpaRepository cropBookmarkJpaRepository;

    public CropCommandValidatorImpl(
        CropJpaRepository cropJpaRepository,
        CropBookmarkJpaRepository cropBookmarkJpaRepository
    ) {
        this.cropJpaRepository = cropJpaRepository;
        this.cropBookmarkJpaRepository = cropBookmarkJpaRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public CropPost validateCropPostUpdatable(Long id, Integer addedImageCount, Integer deletedImageCount) {
        CropPost cropPost = cropJpaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("등록된 작물게시글이 없습니다."));
        cropPost.validateUpdatable(addedImageCount, deletedImageCount);
        return cropPost;
    }

    @Transactional(readOnly = true)
    @Override
    public void validateCropBookMarkCreatable(Long id, Long loginUserId) {
        if (cropBookmarkJpaRepository.existsByIdAndBookMarkOwnerId(id, loginUserId)) {
            throw new IllegalArgumentException("같은 사용자가 동일한 게시물에 북마크를 추가할 수 없습니다.");
        }
    }
}
