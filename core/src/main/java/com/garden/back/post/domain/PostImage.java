package com.garden.back.post.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_images")
@Getter
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "image_url")
    private String imageUrl;

    private PostImage(String imageUrl, Post post) {
        this.imageUrl = imageUrl;
        this.post = post;
        validatePostImage();
    }


    private void validatePostImage() {
        if (this.post == null) {
            throw new IllegalArgumentException("Post는 null일 수 없습니다.");
        }
        if (this.imageUrl == null || this.imageUrl.length() > 255) {
            throw new IllegalArgumentException("imageUrl은 255자를 초과할 수 없으며 null 허용이 안됩니다.");
        }

        if (!UrlValidator.getInstance().isValid(this.imageUrl)) {
            throw new IllegalArgumentException("url 형식에 맞게 입력해주세요.");
        }
    }

    public static PostImage create(String imageUrl, Post post) {
        return new PostImage(imageUrl, post);
    }

    public boolean hasUrl(String url) {
        if (this.imageUrl == null || url == null) {
            return false;
        }
        return this.imageUrl.equals(url);
    }
}
