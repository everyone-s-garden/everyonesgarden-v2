package com.garden.back.crop.domain.repository.response;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.member.MemberMannerGrade;

import java.util.List;

public record FindCropsPostDetailsResponse(
    String content,
    String author,
    MemberMannerGrade memberMannerGrade,
    String address,
    CropCategory cropCategory,
    Long bookmarkCount,
    List<String> images
) {
}
