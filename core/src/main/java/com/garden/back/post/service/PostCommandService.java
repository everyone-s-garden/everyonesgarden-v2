package com.garden.back.post.service;

import com.garden.back.global.image.ParallelImageUploader;
import com.garden.back.post.domain.*;
import com.garden.back.post.domain.repository.PostCommandRepository;
import com.garden.back.post.service.request.CommentCreateServiceRequest;
import com.garden.back.post.service.request.CommentUpdateServiceRequest;
import com.garden.back.post.service.request.PostCreateServiceRequest;
import com.garden.back.post.service.request.PostUpdateServiceRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCommandService {

    private final PostCommandRepository postCommandRepository;
    private final ParallelImageUploader parallelImageUploader;
    private final PostCommandValidator postCommandValidator;

    private static final String IMAGE_DIRECTORY = "posts/";

    public PostCommandService(
        PostCommandRepository postCommandRepository,
        ParallelImageUploader parallelImageUploader,
        PostCommandValidator postCommandValidator
    ) {
        this.postCommandRepository = postCommandRepository;
        this.parallelImageUploader = parallelImageUploader;
        this.postCommandValidator = postCommandValidator;
    }

    @Transactional
    public Long createPost(
        PostCreateServiceRequest request,
        Long memberId
    ) {
        List<String> urls = parallelImageUploader.upload(IMAGE_DIRECTORY, request.images());
        Post post = request.toEntity(memberId, urls);
        return postCommandRepository.savePost(post);
    }

    @Transactional
    public void updatePost(Long id, Long memberId, PostUpdateServiceRequest request) {
        Post post = postCommandValidator.validatePostUpdatable(id, request.addedImagesCount(), request.deletedImagesCount());
        List<String> addedUrls = parallelImageUploader.upload(IMAGE_DIRECTORY, request.addedImages());
        parallelImageUploader.delete(IMAGE_DIRECTORY, request.deleteImages());
        postCommandRepository.updatePost(request.toRepositoryRequest(post, memberId, addedUrls));
    }

    public void deletePost(Long id, Long memberId) {
        Post post = postCommandValidator.validatePostDeletable(id, memberId);
        postCommandRepository.deletePost(post);
    }

    public Long addLikeToPost(Long id, Long memberId) {
        postCommandValidator.validatePostLikeCreatable(id, memberId);
        return postCommandRepository.savePostLike(PostLike.create(memberId, id));
    }

    public Long createComment(Long id, Long memberId, CommentCreateServiceRequest request) {
        return postCommandRepository.saveComment(request.toEntity(memberId, id));
    }

    public void updateComment(Long commentId, Long memberId, CommentUpdateServiceRequest request) {
        postCommandRepository.updateComment(commentId, memberId, request.content());
    }

    public void deleteComment(Long id, Long memberId) {
        PostComment postComment = postCommandValidator.validateCommentDeletable(id, memberId);
        postCommandRepository.deleteComment(postComment);
    }

    public Long addLikeToComment(Long id, Long memberId) {
        postCommandValidator.validateCommentLikeCreatable(id, memberId);
        return postCommandRepository.saveCommentLike(CommentLike.create(memberId, id));
    }

    public void deleteCommentLike(Long commentId, Long memberId) {
        postCommandRepository.deleteCommentLike(commentId, memberId);
    }

    public void deletePostLike(Long postId, Long memberId) {
        postCommandRepository.deletePostLike(postId, memberId);
    }
}
