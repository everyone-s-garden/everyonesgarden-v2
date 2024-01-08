package com.garden.back.crop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "crop_bookmarks")
public class CropBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "crop_post_id", nullable = false)
    private Long cropPostId;

    @Column(name = "bookmark_owner_id")
    private Long bookMarkOwnerId;

    private CropBookmark(Long cropPostId, Long bookMarkOwnerId) {
        this.cropPostId = cropPostId;
        this.bookMarkOwnerId = bookMarkOwnerId;
    }

    public static CropBookmark create(Long cropPostId, Long bookMarkOwnerId) {
        return new CropBookmark(cropPostId, bookMarkOwnerId);
    }
}
