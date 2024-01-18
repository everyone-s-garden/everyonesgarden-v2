package com.garden.back.domain.garden;

import com.garden.back.garden.domain.GardenChatRoom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GardenChatRoomTest {

    @DisplayName("roomId가 양수가 아니면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    void throwException_zeroOrMinus_roomId(Long roomId) {
        assertThrows(IllegalArgumentException.class,
                () -> GardenChatRoom.of(
                        roomId
                ));
    }
}