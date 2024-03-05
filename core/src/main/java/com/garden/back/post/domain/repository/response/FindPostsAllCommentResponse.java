package com.garden.back.post.domain.repository.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record FindPostsAllCommentResponse(
    List<ParentInfo> mainComment
) {
    public record ParentInfo(
        Long commentId,
        Long likeCount,
        String content,
        UserResponse userInfo,
        Boolean isLikeClick,
        List<CommentInfo> subComments,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt
    ) {}

    public record CommentInfo(
        Long commentId,
        Long parentId,
        Long likeCount,
        String content,
        UserResponse userInfo,
        Boolean isLikeClick,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt
    ) {}
}
