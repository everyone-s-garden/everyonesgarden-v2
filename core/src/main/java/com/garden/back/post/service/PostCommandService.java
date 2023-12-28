package com.garden.back.post.service;

import com.garden.back.post.service.request.CommentCreateServiceRequest;
import com.garden.back.post.service.request.CommentUpdateServiceRequest;
import com.garden.back.post.service.request.PostCreateServiceRequest;
import com.garden.back.post.service.request.PostUpdateServiceRequest;
import org.springframework.stereotype.Service;

@Service
public class PostCommandService {

    public Long createPost(PostCreateServiceRequest request, Long memberId) {
        return 1L;
    }

    public void updatePost(Long id, Long memberId, PostUpdateServiceRequest request) {

    }

    public void deletePost(Long id, Long memberId) {

    }

    public Long addLikeToPost(Long id, Long memberId) {
        return 1L;
    }

    public Long createComment(Long id, Long memberId, CommentCreateServiceRequest request) {
        return 1L;
    }

    public void updateComment(Long commentId, Long memberId, CommentUpdateServiceRequest request) {

    }

    public void deleteComment(Long id, Long memberId) {

    }

    public Long addLikeToComment(Long id, Long memberId) {
        return 1L;
    }

    public void deleteCommentLike(Long commentId, Long memberId) {

    }

    public void deletePostLike(Long postId, Long memberId) {

    }
}
