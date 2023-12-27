package com.garden.back.report.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GardenReportTest {

    @ParameterizedTest
    @MethodSource("invalidCreateArguments")
    void createWithInvalidArguments(Long reporterId, String content, GardenReportType reportType, Long gardenId) {
        assertThatThrownBy(() ->
                GardenReport.create(reporterId, content, reportType, gardenId))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> invalidCreateArguments() {
        return Stream.of(
            Arguments.of(null, "컨텐츠", GardenReportType.FAKED_SALE, 1L),
            Arguments.of(1L, "컨텐츠", GardenReportType.FAKED_SALE, null),
            Arguments.of(1L, "컨텐츠", GardenReportType.FAKED_SALE, -1L),
            Arguments.of(-1L, "컨텐츠", GardenReportType.FAKED_SALE, 1L)
        );
    }
}