package com.garden.back.crop;

import com.garden.back.crop.domain.CropCategory;

import java.util.List;

public record FindCropsPostDetailsResponse(
    String content,
    String author,
    String mannerPoint,
    String authorAddress,
    CropCategory cropCategory,
    Integer bookmarkCount,
    List<String> images
) {
}
