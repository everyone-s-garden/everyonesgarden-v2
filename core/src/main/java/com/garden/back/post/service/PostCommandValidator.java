package com.garden.back.post.service;

import com.garden.back.post.domain.Post;
import com.garden.back.post.domain.PostComment;

public interface PostCommandValidator {
    Post validatePostUpdatable(Long postId, Integer addedPostCount, Integer deletedPostCount);

    Post validatePostDeletable(Long postId, Long memberId);

    void validatePostLikeCreatable(Long postId, Long memberId);

    PostComment validateCommentDeletable(Long id, Long memberId);

    void validateCommentLikeCreatable(Long id, Long memberId);

    void validateCommentCreatable(Long parentId);
}
