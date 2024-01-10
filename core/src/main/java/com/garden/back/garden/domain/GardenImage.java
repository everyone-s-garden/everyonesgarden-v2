package com.garden.back.garden.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GardenImage {


    private Long gardenImageId;
    private String imageUrl;
    Garden garden;

    protected GardenImage(
            Long gardenImageId,
            String imageUrl,
            Garden garden
    ) {
        Assert.hasLength(imageUrl, "imageUrl은 null이거나 빈 값일 수 없습니다.");
        Assert.notNull(garden, "garden은 null일 수 없습니다.");

        this.gardenImageId =gardenImageId;
        this.imageUrl = imageUrl;
        this.garden = garden;
    }

    public static GardenImage of(
            Long gardenImageId,
            String imageUrl,
            Garden garden
    ) {
        return new GardenImage(
                gardenImageId,
                imageUrl,
                garden
        );
    }

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
