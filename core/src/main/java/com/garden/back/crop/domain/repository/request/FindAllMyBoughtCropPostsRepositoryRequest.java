package com.garden.back.crop.domain.repository.request;

public record FindAllMyBoughtCropPostsRepositoryRequest(
    Long loginUserId,
    Long offset,
    Long limit
) {
}
