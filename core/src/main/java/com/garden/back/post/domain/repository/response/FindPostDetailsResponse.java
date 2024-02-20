package com.garden.back.post.domain.repository.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.garden.back.post.domain.PostType;

import java.time.LocalDate;
import java.util.List;

public record FindPostDetailsResponse(
    Long commentCount,
    Long likeCount,
    UserResponse userInfo,
    String content,
    String title,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate createdDate,
    Boolean isLikeClick,
    PostType postType,
    List<String> images
) {
}
