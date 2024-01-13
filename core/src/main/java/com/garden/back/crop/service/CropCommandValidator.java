package com.garden.back.crop.service;

import com.garden.back.crop.domain.CropPost;

public interface CropCommandValidator {
    CropPost validateCropPostUpdatable(Long id, Integer addedImageCount, Integer deletedImageCount);

    void validateCropBookMarkCreatable(Long id, Long loginUserId);
}
