package com.garden.back.member;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MemberTest {

    @ParameterizedTest
    @MethodSource("provideInvalidMembers")
    void testInvalidMemberCreation(String email, String nickname, Role role) {
        assertThatThrownBy(() -> Member.create(email, nickname, role))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideInvalidMembers() {
        return Stream.of(
            Arguments.of("invalid-email", "nickname", Role.USER),
            Arguments.of("email@example.com", StringUtils.repeat("a", 31), Role.USER)
        );
    }
}