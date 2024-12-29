package com.garden.back.testutil.garden;

import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.domain.GardenImage;

import java.util.List;

public class GardenImageFixture {

    public static GardenImage gardenImage(Garden garden) {
        return GardenImage.of(
            "www.myGarde.com",
            garden
        );
    }

    public static List<GardenImage> gardenImages(Garden garden) {
        return List.of(GardenImage.of(
                "www.myGarde.comA",
                garden
            ),
            GardenImage.of(
                "www.myGardeB.com",
                garden
            )
        );
    }

}
