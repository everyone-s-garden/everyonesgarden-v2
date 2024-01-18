package com.garden.back.domain.crop;

import com.garden.back.crop.domain.CropChatRoomInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CropChatRoomInfoTest {

    @DisplayName("postId가 양수가 아니면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    void throwException_zeroOrMinus_postId(Long postId) {
        assertThrows(IllegalArgumentException.class,
                () -> CropChatRoomInfo.of(
                        false,
                        postId,
                        1L
                ));
    }

    @DisplayName("memberId가 양수가 아니면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    void throwException_zeroOrMinus_memberId(Long memberId) {
        assertThrows(IllegalArgumentException.class,
                () -> CropChatRoomInfo.of(
                        false,
                        1L,
                        memberId
                ));
    }

}
