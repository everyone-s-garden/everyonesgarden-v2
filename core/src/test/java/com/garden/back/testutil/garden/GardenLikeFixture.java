package com.garden.back.testutil.garden;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.model.GardenLike;

public class GardenLikeFixture {

    public static GardenLike gardenLike(Garden garden) {
        return GardenLike.of(
                1L,
                garden
        );
    }
}
