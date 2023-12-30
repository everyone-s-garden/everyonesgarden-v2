package com.garden.back.post.domain.repository;

import com.garden.back.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsPostLikeByLikesClickerIdAndPostId(Long likesClickerId, Long postId);

    Optional<PostLike> findPostLikeByPostIdAndLikesClickerId(Long postId, Long likesClickerId);
}
