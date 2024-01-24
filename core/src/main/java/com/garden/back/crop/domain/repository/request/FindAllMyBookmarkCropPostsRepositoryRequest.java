package com.garden.back.crop.domain.repository.request;

public record FindAllMyBookmarkCropPostsRepositoryRequest(
    Long offset,
    Long limit
) {
}
