package com.garden.back.post.domain.repository.response;

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
        List<CommentInfo> subComments
    ) {}

    public record CommentInfo(
        Long commentId,
        Long parentId,
        Long likeCount,
        String content,
        UserResponse userInfo,
        Boolean isLikeClick
    ) {}
}
