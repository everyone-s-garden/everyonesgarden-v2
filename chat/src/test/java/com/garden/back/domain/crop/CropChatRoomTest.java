package com.garden.back.domain.crop;

import com.garden.back.crop.domain.CropChatRoom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CropChatRoomTest {

    @DisplayName("roomId가 양수가 아니면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    void throwException_zeroOrMinus_roomId(Long roomId) {
        assertThrows(IllegalArgumentException.class,
                () -> CropChatRoom.of(
                        roomId
                ));
    }

}