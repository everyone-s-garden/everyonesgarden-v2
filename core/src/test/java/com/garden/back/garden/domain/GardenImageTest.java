package com.garden.back.garden.domain;

import com.garden.back.testutil.garden.GardenFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GardenImageTest {

    @DisplayName("garden이 null이면 예외를 던진다.")
    @Test
    void throwException_null_garden() {
        assertThrows(IllegalArgumentException.class,
                () -> new GardenImage(
                        "everyGarden.com",
                        null
                ));
    }

    @DisplayName("imageUrl이 null이거나 빈값이면 예외를 던진다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwException_nullOrEmpty_imageUrl(String imageUrl) {
        assertThrows(IllegalArgumentException.class,
                () -> new GardenImage(
                        imageUrl,
                        GardenFixture.privateGarden()
                ));
    }

}
