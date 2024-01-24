package com.garden.back.global;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ChatTypeTest {

    @DisplayName("해당하는 아이디를 포함하지 않으면 false를 반환한다.")
    @Test
    void containsId_notExisted_throwException() {
        assertThat(ChatType.containsId(3L)).isFalse();
    }

    @DisplayName("해당하는 아이디를 포함하면 true를 반환한다.")
    @ParameterizedTest
    @ValueSource(longs = {1, 2})
    void containsId(Long chatTypeId) {
        assertThat(ChatType.containsId(chatTypeId)).isTrue();
    }
}
