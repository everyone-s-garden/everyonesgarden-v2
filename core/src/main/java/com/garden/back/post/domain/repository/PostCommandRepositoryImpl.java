package com.garden.back.post.domain.repository;

import com.garden.back.post.domain.*;
import com.garden.back.post.domain.repository.request.PostUpdateRepositoryRequest;
import com.garden.back.report.domain.comment.CommentReportRepository;
import com.garden.back.report.domain.post.PostReportRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class PostCommandRepositoryImpl implements PostCommandRepository {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostCommentRepository postCommentRepository;
    private final PostReportRepository postReportRepository;
    private final CommentReportRepository commentReportRepository;

    public PostCommandRepositoryImpl(
        PostRepository postRepository,
        PostLikeRepository postLikeRepository,
        CommentLikeRepository commentLikeRepository,
        PostCommentRepository postCommentRepository,
        PostReportRepository postReportRepository, CommentReportRepository commentReportRepository
    ) {
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
        this.commentLikeRepository = commentLikeRepository;
        this.postCommentRepository = postCommentRepository;
        this.postReportRepository = postReportRepository;
        this.commentReportRepository = commentReportRepository;
    }

    @Override
    public Long savePost(Post post) {
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Override
    public void updatePost(PostUpdateRepositoryRequest request) {
        Post post = request.post();
        post.update(request.title(), request.content(), request.memberId(), request.deleteImages(), request.addedImages(), request.postType());
    }

    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Override
    public Long saveComment(PostComment postComment) {
        Post post = postRepository.findById(postComment.getPostId()).orElseThrow(() -> new EntityNotFoundException("존재하는 게시글입니다."));
        post.increaseCommentCount();
        return postCommentRepository.save(postComment).getId();
    }

    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Override
    public void updateComment(Long commentId, Long memberId, String content) {
        PostComment postComment = postCommentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글 id 입니다."));
        postComment.update(memberId, content);
    }

    @Override
    public void deleteComment(PostComment postComment) {
        Post post = postRepository.findById(postComment.getPostId()).orElseThrow(() -> new EntityNotFoundException("존재하는 게시글입니다."));
        post.decreaseCommentCount();
        postCommentRepository.delete(postComment);
    }

    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Override
    public Long savePostLike(PostLike postLike) {
        Post post = postRepository.findById(postLike.getPostId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글입니다."));
        post.increaseLikeCount();
        return postLikeRepository.save(postLike).getId();
    }

    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Override
    public Long saveCommentLike(CommentLike commentLike) {
        PostComment comment = postCommentRepository.findById(commentLike.getCommentId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글입니다."));
        comment.increaseLikeCount();
        return commentLikeRepository.save(commentLike).getId();
    }

    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Override
    public void deleteCommentLike(Long commentId, Long memberId) {
        CommentLike like = commentLikeRepository.findCommentLikeByCommentIdAndLikesClickerId(commentId, memberId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 해당 댓글에 누른 좋아요가 없습니다."));
        commentLikeRepository.delete(like);
        PostComment comment = postCommentRepository.findById(like.getCommentId()).orElseThrow(() -> new EntityNotFoundException("존재하는 댓글이 없습니다."));
        comment.decreaseLikeCount();
    }

    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Override
    public void deletePostLike(Long postId, Long memberId) {
        PostLike like = postLikeRepository.findPostLikeByPostIdAndLikesClickerId(postId, memberId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 해당 게시글에 누른 좋아요가 없습니다."));
        postLikeRepository.delete(like);
        Post post = postRepository.findById(like.getPostId()).orElseThrow(() -> new EntityNotFoundException("존재하는 게시글이 없습니다."));
        post.decreaseLikeCount();
    }

    @Override
    public void deletePostByReport(Long id) {
        Long reportCount = postReportRepository.countByPostId(id);

        postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하는 게시글이 없습니다.")).delete(reportCount);

    }

    @Override
    public void deletePostCommentByReport(Long id) {
        Long reportCount = commentReportRepository.countByCommentId(id);
        postCommentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하는 댓글이 없습니다.")).delete(reportCount);
    }
}
