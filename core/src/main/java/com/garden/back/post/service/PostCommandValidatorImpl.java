package com.garden.back.post.service;

import com.garden.back.post.domain.Post;
import com.garden.back.post.domain.PostComment;
import com.garden.back.post.domain.repository.CommentLikeRepository;
import com.garden.back.post.domain.repository.PostCommentRepository;
import com.garden.back.post.domain.repository.PostLikeRepository;
import com.garden.back.post.domain.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostCommandValidatorImpl implements PostCommandValidator {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostCommentRepository postCommentRepository;

    public PostCommandValidatorImpl(
        PostRepository postRepository,
        PostLikeRepository postLikeRepository,
        CommentLikeRepository commentLikeRepository,
        PostCommentRepository postCommentRepository
    ) {
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
        this.commentLikeRepository = commentLikeRepository;
        this.postCommentRepository = postCommentRepository;
    }

    @Override
    public Post validatePostUpdatable(Long postId, Integer addedPostCount, Integer deletedPostCount) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글 id 입니다."));
        post.validateUpdatable(addedPostCount, deletedPostCount);

        return post;
    }

    @Override
    public Post validatePostDeletable(Long postId, Long memberId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글 id 입니다."));
        post.validateDeletable(memberId);

        return post;
    }

    @Override
    public void validatePostLikeCreatable(Long postId, Long memberId) {
        if (postLikeRepository.existsPostLikeByLikesClickerIdAndPostId(memberId, postId)) {
            throw new IllegalArgumentException("동일한 사용자는 같은 게시글에 좋아요를 누를 수 없습니다.");
        }
    }

    @Override
    public PostComment validateCommentDeletable(Long id, Long memberId) {
        PostComment postComment = postCommentRepository.findById(id).orElseThrow();
        postComment.validateDeletable(memberId);

        return postComment;
    }

    @Override
    public void validateCommentLikeCreatable(Long id, Long memberId) {
        if (commentLikeRepository.existsCommentLikeByLikesClickerIdAndCommentId(memberId, id)) {
            throw new IllegalArgumentException("동일한 사용자는 같은 댓글에 좋아요를 누를 수 없습니다.");
        }
    }

    @Override
    public void validateCommentCreatable(Long parentId) {
        if (parentId == null) {
            return;
        }

        Optional<PostComment> parentCommentOptional = postCommentRepository.findById(parentId);
        if (parentCommentOptional.isPresent()) {
            PostComment parentComment = parentCommentOptional.get();

            if (parentComment.getParentCommentId() != null) {
                throw new IllegalArgumentException("대댓글 에는 대댓글을 작성할 수 없습니다.");
            }
        }
    }
}
