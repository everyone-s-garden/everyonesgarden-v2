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

    public static GardenLike gardenLikeToVisit (Garden garden) {
        return GardenLike.of(
            garden.getWriterId()+1,
            garden
        );
    }
}
