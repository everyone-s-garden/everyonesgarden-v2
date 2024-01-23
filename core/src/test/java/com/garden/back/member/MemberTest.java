package com.garden.back.member;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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

    @ParameterizedTest
    @MethodSource("provideScoresAndGrades")
    void testUpdateMannerScore(Float score, MemberMannerGrade expectedGrade) {
        // Given
        Member member = Member.create("valid@example.com", "nickname", Role.USER);

        // When
        member.updateMannerScore(score);

        // Then
        assertAll(
            () -> assertThat(member.getMannerScore()).isEqualTo(score),
            () -> assertThat(member.getMemberMannerGrade()).isEqualTo(expectedGrade)
        );
    }

    private static Stream<Arguments> provideScoresAndGrades() {
        return Stream.of(
            Arguments.of(0.0f, MemberMannerGrade.SEED),
            Arguments.of(10.0f, MemberMannerGrade.SPROUT),
            Arguments.of(30.0f, MemberMannerGrade.STEM),
            Arguments.of(60.0f, MemberMannerGrade.FRUIT),
            Arguments.of(80.0f, MemberMannerGrade.HARVEST),
            Arguments.of(95.0f, MemberMannerGrade.FARMER)
        );
    }
}