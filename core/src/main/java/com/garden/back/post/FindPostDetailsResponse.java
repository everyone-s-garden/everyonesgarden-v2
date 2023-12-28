package com.garden.back.post;

public record FindPostDetailsResponse(
    Long likeCount,
    String author,
    String content,
    String title
) {
}
