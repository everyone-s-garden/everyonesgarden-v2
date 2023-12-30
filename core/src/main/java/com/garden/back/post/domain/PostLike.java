package com.garden.back.post.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "likes_clicker_id")
    private Long likesClickerId;

    private PostLike(Long likesClickerId, Long postId) {
        this.likesClickerId = likesClickerId;
        this.postId = postId;
        validatePostLike();
    }

    private void validatePostLike() {
        if (this.likesClickerId == null || this.likesClickerId <= 0) {
            throw new IllegalArgumentException("likesClickerId는 0보다 커야 하며 null 허용이 안됩니다.");
        }
        if (this.postId == null || this.postId <= 0) {
            throw new IllegalArgumentException("postId는 0보다 커야 하며 null 허용이 안됩니다.");
        }
    }

    public static PostLike create(Long likesClickerId, Long postId) {
        return new PostLike(likesClickerId, postId);
    }
}
