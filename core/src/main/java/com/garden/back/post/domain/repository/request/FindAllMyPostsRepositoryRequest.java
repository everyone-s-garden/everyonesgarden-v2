package com.garden.back.post.domain.repository.request;

public record FindAllMyPostsRepositoryRequest(
    Long offset,
    Long limit
) {
}
