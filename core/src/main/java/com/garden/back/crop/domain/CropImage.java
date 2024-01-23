package com.garden.back.crop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "crop_images")
public class CropImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "crop_posts__id")
    private CropPost crop;

    @Column(name = "image_url")
    private String imageUrl;

    private CropImage(String imageUrl, CropPost cropPost) {
        Assert.notNull(cropPost, "Crop은 null일 수 없습니다.");
        validateImageUrl(imageUrl);
        this.imageUrl = imageUrl;
        this.crop = cropPost;
    }

    private void validateImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.length() > 255) {
            throw new IllegalArgumentException("imageUrl은 255자를 초과할 수 없으며 null 허용이 안됩니다.");
        }

        if (!UrlValidator.getInstance().isValid(imageUrl)) {
            throw new IllegalArgumentException("url 형식에 맞게 입력해주세요.");
        }
    }

    public static CropImage create(String imageUrl, CropPost cropPost) {
        return new CropImage(imageUrl, cropPost);
    }

    public boolean hasUrl(String url) {
        if (this.imageUrl == null || url == null) {
            return false;
        }
        return this.imageUrl.equals(url);
    }
}
