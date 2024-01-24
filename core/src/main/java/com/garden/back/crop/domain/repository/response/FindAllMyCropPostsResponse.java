package com.garden.back.crop.domain.repository.response;

import java.util.List;

public record FindAllMyCropPostsResponse(
    List<CropInfo> cropInfos
) {
    public record CropInfo(
        Long cropPostId,
        String title,
        String imageUrl
    ) {
    }
}
