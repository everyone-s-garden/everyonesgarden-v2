package com.garden.back.testutil.garden;

import com.garden.back.garden.domain.MyManagedGarden;

import java.time.LocalDate;

public class MyManagedGardenFixture {

    public static MyManagedGarden myManagedGarden(Long gardenId) {
        return MyManagedGarden.of(
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                1L,
                "https://kr.object.ncloudstorage.com/every-garden/images/garden/background.jpg",
                gardenId
        );
    }

}
