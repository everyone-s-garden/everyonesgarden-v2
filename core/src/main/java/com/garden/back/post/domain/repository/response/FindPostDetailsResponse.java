package com.garden.back.post.domain.repository.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record FindPostDetailsResponse(
    Long commentCount,
    Long likeCount,
    String author,
    String content,
    String title,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate createdDate
) {
}
