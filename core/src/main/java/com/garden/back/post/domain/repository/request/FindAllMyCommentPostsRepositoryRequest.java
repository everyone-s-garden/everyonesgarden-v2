package com.garden.back.post.domain.repository.request;

public record FindAllMyCommentPostsRepositoryRequest(
    Long offset,
    Long limit
) {
}
