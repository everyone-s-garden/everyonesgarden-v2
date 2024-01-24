package com.garden.back.crop.domain.repository.request;

public record FindAllMyCropPostsRepositoryRequest(
    Long offset,
    Long limit
) {
}
