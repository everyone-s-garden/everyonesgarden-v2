package com.garden.back.report.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    @ParameterizedTest
    @MethodSource("invalidCreateArguments")
    void createWithInvalidArguments(Long reporterId, String content) {
        assertThrows(IllegalArgumentException.class, () -> new Report(reporterId, content) {});
    }

    private static Stream<Arguments> invalidCreateArguments() {
        return Stream.of(
            Arguments.of(null, "Valid content"),
            Arguments.of(-1L, "Valid content"),
            Arguments.of(1L, generateString(256))
        );
    }

    private static String generateString(int length) {
        return new String(new char[length]).replace('\0', 'a');
    }
}