package com.garden.back.post.domain;

import com.garden.back.global.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "post_comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Column(name = "report_count")
    private Long reportCount;

    @Column(name = "likes_count")
    private Long likesCount;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "content")
    private String content;

    @Column(name = "post_id")
    private Long postId;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "delete_status")
    private boolean deleteStatus;

    @Transient
    private static final int DELETE_REPORT_COUNT = 10;

    private PostComment(Long parentCommentId, Long authorId, String content, Long postId) {
        this.reportCount = 0L;
        this.likesCount = 0L;
        this.parentCommentId = parentCommentId;
        this.authorId = authorId;
        this.content = content;
        this.postId = postId;
        validatePostCommentStatus();
    }

    private void validatePostCommentStatus() {
        if (authorId == null || authorId <= 0) {
            throw new IllegalArgumentException("authorId는 0보다 커야 하며 null 허용이 안됩니다.");
        }
        if (content == null || content.length() > 255) {
            throw new IllegalArgumentException("내용은 255자를 초과할 수 없으며 null 허용이 안됩니다.");
        }
        if (postId == null || postId <= 0) {
            throw new IllegalArgumentException("postId는 0보다 커야 하며 null 허용이 안됩니다.");
        }
        if (parentCommentId != null && parentCommentId <= 0) {
            throw new IllegalArgumentException("parentCommentId가 null이 아닌 경우, 0보다 커야 합니다.");
        }
    }

    public static PostComment create(Long parentCommentId, Long authorId, String content, Long postId) {
        return new PostComment(parentCommentId, authorId, content, postId);
    }

    public void update(Long memberId, String content) {
        validateAuthority(memberId);
        this.content = content;
    }

    public void validateDeletable(Long memberId) {
        validateAuthority(memberId);
    }

    private void validateAuthority(Long memberId) {
        if (!this.authorId.equals(memberId)) {
            throw new IllegalArgumentException("댓글 작성자에게 권한이 있습니다.");
        }
    }

    public void increaseLikeCount() {
        this.likesCount++;
    }

    public void decreaseLikeCount() {
        this.likesCount--;
        if (likesCount < 0) {
            throw new IllegalStateException("좋아요의 갯수는 음수가 될 수 없습니다.");
        }
    }

    public void delete(Long reportCount) {
        if (reportCount > DELETE_REPORT_COUNT) {
            this.deleteStatus = true;
        }
    }
}
