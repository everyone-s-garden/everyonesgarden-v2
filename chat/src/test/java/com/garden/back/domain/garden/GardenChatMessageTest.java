package com.garden.back.domain.garden;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GardenChatMessageTest {

    @DisplayName("chatRoom이 null이면 예외를 던진다.")
    @Test
    void throwException_nullOrEmpty_imageUrl() {
        assertThrows(IllegalArgumentException.class,
                () -> GardenChatMessage.of(
                        null,
                        1L,
                        "안녕",
                        false
                ));
    }

    @DisplayName("memberId가 양수가 아니면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    void throwException_zeroOrMinus_memberId(Long memberId) {
        assertThrows(IllegalArgumentException.class,
                () -> GardenChatMessage.of(
                        new GardenChatRoom(),
                        memberId,
                        "안녕",
                        false
                ));
    }

    @DisplayName("chatContent가 null이면 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void throwException_nullOrEmpty_chatContent(String chatContent) {
        assertThrows(IllegalArgumentException.class,
                () -> GardenChatMessage.of(
                        new GardenChatRoom(),
                        1L,
                        chatContent,
                        false
                ));
    }
}