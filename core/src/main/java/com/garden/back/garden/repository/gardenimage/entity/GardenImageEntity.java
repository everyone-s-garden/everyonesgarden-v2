package com.garden.back.garden.repository.gardenimage.entity;

import com.garden.back.garden.domain.GardenImage;
import com.garden.back.garden.repository.garden.entity.GardenEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "garden_images")
public class GardenImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "garden_image_id")
    private Long gardenImageId;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_id")
    GardenEntity garden;

    public GardenImage toModel() {
        return GardenImage.of(
                gardenImageId,
                imageUrl,
                garden.toModel()
        );
    }

    public static GardenImageEntity from(GardenImage gardenImage, GardenEntity garden) {
        GardenImageEntity gardenImageEntity = new GardenImageEntity();
        gardenImageEntity.gardenImageId = gardenImage.getGardenImageId();
        gardenImageEntity.imageUrl = gardenImage.getImageUrl();
        gardenImageEntity.garden = garden;

        return gardenImageEntity;
    }

    public static GardenImageEntity from(GardenImage gardenImage) {
        GardenImageEntity gardenImageEntity = new GardenImageEntity();
        gardenImageEntity.gardenImageId = gardenImage.getGardenImageId();
        gardenImageEntity.imageUrl = gardenImage.getImageUrl();
        gardenImageEntity.garden = GardenEntity.from(gardenImage.getGarden());

        return gardenImageEntity;
    }

}
