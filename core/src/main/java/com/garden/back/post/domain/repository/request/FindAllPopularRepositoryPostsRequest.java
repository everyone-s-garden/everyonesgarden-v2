package com.garden.back.post.domain.repository.request;

public record FindAllPopularRepositoryPostsRequest(
    Long offset,
    Long limit,
    Integer hour
) {
}
