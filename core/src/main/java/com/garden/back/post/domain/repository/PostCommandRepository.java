package com.garden.back.post.domain.repository;

import com.garden.back.post.domain.CommentLike;
import com.garden.back.post.domain.Post;
import com.garden.back.post.domain.PostComment;
import com.garden.back.post.domain.PostLike;
import com.garden.back.post.domain.repository.request.PostUpdateRepositoryRequest;

public interface PostCommandRepository {
    Long savePost(Post post);

    void updatePost(PostUpdateRepositoryRequest request);

    void deletePost(Post postId);

    Long savePostLike(PostLike postLike);

    Long saveCommentLike(CommentLike commentLike);

    Long saveComment(PostComment postComment);

    void updateComment(Long commentId, Long memberId, String content);

    void deleteComment(PostComment postComment);

    void deleteCommentLike(Long commentId, Long memberId);

    void deletePostLike(Long postId, Long memberId);
}
