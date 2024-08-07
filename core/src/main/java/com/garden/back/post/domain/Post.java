package com.garden.back.post.domain;

import com.garden.back.global.jpa.BaseTimeEntity;
import com.mysema.commons.lang.Assert;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts")
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "likes_count", nullable = false)
    private Long likesCount;

    @Column(name = "comments_count", nullable = false)
    private Long commentsCount;

    @Column(name = "report_count", nullable = false)
    private Long reportCount;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    @Lob
    private String content;

    @Column(name = "post_author_id", nullable = false)
    private Long postAuthorId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "post")
    private List<PostImage> postImages = new ArrayList<>();

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "delete_status")
    private boolean deleteStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    private PostType postType;

    @Transient
    private static final int DELETE_REPORT_COUNT = 10;

    private Post(String title, String content, Long postAuthorId, List<String> postUrls, PostType postType) {
        this.commentsCount = 0L;
        this.reportCount = 0L;
        this.likesCount = 0L;
        this.title = title;
        this.content = content;
        this.postAuthorId = postAuthorId;
        this.postImages = postUrls.stream().map(postUrl -> PostImage.create(postUrl, this)).toList();
        this.createdDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        this.deleteStatus = false;
        this.postType = postType;
        validatePostStatus();
    }

    public static Post create(String title, String content, Long postAuthorId, List<String> postUrls, PostType postType) {
        return new Post(title, content, postAuthorId, postUrls, postType);
    }

    public void update(String title, String content, Long postAuthorId, List<String> deletedImages, List<String> addedImages, PostType postType) {
        if (!this.postAuthorId.equals(postAuthorId)) {
            throw new IllegalArgumentException("자신이 작성한 게시물만 수정이 가능합니다.");
        }

        this.title = title;
        this.content = content;
        this.postType = postType;
        validateUpdatable(addedImages.size(), deletedImages.size());

        // 기존 리스트를 변경 가능한 리스트로 복사
        List<PostImage> modifiablePostImages = new ArrayList<>(this.postImages);

        addedImages.stream()
            .map(url -> PostImage.create(url, this))
            .forEach(modifiablePostImages::add);

        deletedImages.forEach(url ->
            modifiablePostImages.removeIf(postImage -> postImage.hasUrl(url))
        );

        // 변경된 리스트를 다시 할당
        this.postImages = modifiablePostImages;

        validatePostStatus();
    }

    public void validateUpdatable(Integer addedImageCount, Integer deletedImageCount) {
        final int approveImageCount = 10;
        final int totalSize = addedImageCount + this.getPostImages().size() - deletedImageCount;
        if (totalSize > approveImageCount) {
            throw new IllegalArgumentException("작물 게시글 1개에는 10개의 이미지만 등록할 수 있습니다.");
        }
    }

    public void validateDeletable(Long postAuthorId) {
        if (!postAuthorId.equals(this.postAuthorId)) {
            throw new IllegalArgumentException("게시글의 작성자만 게시글을 삭제할 수 있습니다.");
        }
    }

    public void increaseLikeCount() {
        this.likesCount++;
    }

    public void decreaseLikeCount() {
        this.likesCount--;
        if (this.likesCount < 0) {
            throw new IllegalStateException("게시글의 좋아요의 수는 음수가 될 수 없습니다.");
        }
    }

    public void increaseCommentCount() {
        this.commentsCount++;
    }

    public void decreaseCommentCount() {
        this.commentsCount--;
        if (this.commentsCount < 0) {
            throw new IllegalStateException("게시글의 댓글의 수는 음수가 될 수 없습니다.");
        }
    }

    private void validatePostStatus() {

        Assert.notNull(this.postType, "게시글의 타입을 입력해주세요.");

        if (this.title != null && this.title.length() > 255) {
            throw new IllegalArgumentException("제목은 255자를 초과할 수 없으며 null 허용이 안됩니다.");
        }

        if (this.postAuthorId == null || this.postAuthorId <= 0) {
            throw new IllegalArgumentException("postAuthorId는 0보다 커야 하며 null 허용이 안됩니다.");
        }

        if (StringUtils.isBlank(this.content)) {
            throw new IllegalArgumentException("내용은 공백일 수 없습니다.");
        }
    }

    public void delete(Long reportCount) {
        if (reportCount > DELETE_REPORT_COUNT) {
            this.deleteStatus = true;
        }
    }
}
