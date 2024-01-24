package com.garden.back.post.domain.repository.request;

public record FindAllMyLikePostsRepositoryRequest(
    Long offset,
    Long limit
) {
}
