package com.garden.back.garden.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "garden_images")
public class GardenImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "garden_image_id")
    private Long gardenImageId;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_id")
    Garden garden;

    protected GardenImage(
            String imageUrl,
            Garden garden
    ) {
        Assert.hasLength(imageUrl, "imageUrl은 null이거나 빈 값일 수 없습니다.");
        Assert.notNull(garden, "garden은 null일 수 없습니다.");

        this.imageUrl = imageUrl;
        this.garden = garden;
    }

    public static GardenImage of(
            String imageUrl,
            Garden garden
    ) {
        return new GardenImage(
                imageUrl,
                garden
        );
    }

}
