package com.garden.back.testutil.garden;

import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.domain.GardenLike;

public class GardenLikeFixture {

    public static GardenLike gardenLike(Garden garden) {
        return GardenLike.of(
                1L,
                garden
        );
    }
}
