package com.garden.back.testutil.garden;

import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.domain.GardenImage;

public class GardenImageFixture {

    public static GardenImage gardenImage(Garden garden) {
        return GardenImage.of(
                "www.myGarde.com",
                garden
        );
    }

}
