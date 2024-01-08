package com.garden.back.crop;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.region.Address;

import java.util.List;

public record FindCropsPostDetailsResponse(
    String content,
    String author,
    Integer mannerPoint,
    Address authorAddress,
    CropCategory cropCategory,
    Long bookmarkCount,
    List<String> images
) {
}
