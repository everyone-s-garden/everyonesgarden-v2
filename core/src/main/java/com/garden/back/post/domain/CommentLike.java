package com.garden.back.post.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_id", nullable = true)
    private Long commentId;

    @Column(name = "likes_clicker_id", nullable = false)
    private Long likesClickerId;

    private CommentLike(Long likeClickerId, Long commentId) {
        this.likesClickerId = likeClickerId;
        this.commentId = commentId;
        validateCommentLikeStatus();
    }

    public static CommentLike create(Long likesClickerId, Long commentId) {
        return new CommentLike(likesClickerId, commentId);
    }

    private void validateCommentLikeStatus() {
        if (likesClickerId == null || likesClickerId <= 0) {
            throw new IllegalArgumentException("likesClickerId는 0보다 커야 합니다.");
        }
        if (commentId != null && commentId <= 0) {
            throw new IllegalArgumentException("commentId가 null이 아닌 경우, 0보다 커야 합니다.");
        }
    }
}
