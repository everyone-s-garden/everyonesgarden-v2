package com.garden.back.garden.domain;

import com.garden.back.testutil.garden.GardenFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GardenLikeTest {

    @DisplayName("memberId가 음수이면 예외를 던진다.")
    @Test
    void throwException_negative_memberId() {
        assertThrows(IllegalArgumentException.class,
                () -> new GardenLike(
                        -1L,
                        GardenFixture.privateGarden()
                ));
    }

    @DisplayName("memberId가 null이면 예외를 던진다.")
    @Test
    void throwException_null_memberId() {
        assertThrows(IllegalArgumentException.class,
                () -> new GardenLike(
                        null,
                        GardenFixture.privateGarden()
                ));
    }

    @DisplayName("garden null이면 예외를 던진다.")
    @Test
    void throwException_null_garden() {
        assertThrows(IllegalArgumentException.class,
                () -> new GardenLike(
                        1L,
                        null
                ));
    }
}
