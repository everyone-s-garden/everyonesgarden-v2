package com.garden.back.post.domain.repository;

import com.garden.back.post.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsCommentLikeByLikesClickerIdAndCommentId(Long likesClickerId, Long commentId);

    Optional<CommentLike> findCommentLikeByCommentIdAndLikesClickerId(Long commentId, Long likesClickerId);
}
