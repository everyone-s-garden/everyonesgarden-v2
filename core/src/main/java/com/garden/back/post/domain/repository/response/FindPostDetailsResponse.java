package com.garden.back.post.domain.repository.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record FindPostDetailsResponse(
    Long commentCount,
    Long likeCount,
    String author,
    String content,
    String title,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate createdDate,
    List<String> images
) {
}
