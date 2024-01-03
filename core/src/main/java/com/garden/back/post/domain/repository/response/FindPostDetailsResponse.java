package com.garden.back.post.domain.repository.response;

public record FindPostDetailsResponse(
    Long commentCount,
    Long likeCount,
    String author,
    String content,
    String title
) {
}
