package com.garden.back.post.domain.repository.response;

import java.util.List;

public record FindPostsAllCommentResponse(
    List<CommentInfo> commentInfos
) {
    public record CommentInfo(
        Long commentId,
        Long parentId,
        Long likeCount,
        String content,
        String author
    ) {}
}
