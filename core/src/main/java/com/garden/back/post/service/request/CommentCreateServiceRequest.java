package com.garden.back.post.service.request;

import com.garden.back.post.domain.PostComment;

public record CommentCreateServiceRequest(
    String content,
    Long parentId
) {
    public PostComment toEntity(Long authorId, Long postId) {
        return PostComment.create(parentId, authorId, content, postId);
    }
}
