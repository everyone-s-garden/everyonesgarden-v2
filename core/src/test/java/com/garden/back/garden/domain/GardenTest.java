package com.garden.back.garden.domain;

import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.domain.vo.GardenType;
import com.garden.back.global.GeometryUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GardenTest {
    private static final double LATITUDE = 37.4449168;
    private static final double LONGITUDE = 127.1388684;

    @DisplayName("null과 빈 값에 대해 에외가 발생하는 상황을 검증합니다.")
    @Nested
    class NullAndEmpty {

        @DisplayName("텃밭의 주소가 null 혹은 빈값이면 예외를 던진다.")
        @ParameterizedTest
        @NullAndEmptySource
        void testInvalidAddress(String invalidAddress) {
            assertThrows(IllegalArgumentException.class,
                () -> Garden.of(
                    invalidAddress,
                    LATITUDE,
                    LONGITUDE,
                    GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                    "Beautiful Garden",
                    GardenType.PUBLIC,
                    GardenStatus.ACTIVE,
                    "10",
                    "123-456-7890",
                    "Large",
                    "A wonderful garden",
                    LocalDate.now(),
                    LocalDate.now().plusDays(30),
                    "화장실, 농기구 등",
                    1L,
                    false,
                    0
                ));
        }

        @DisplayName("텃밭의 이름가 null 혹은 빈값이면 예외를 던진다.")
        @ParameterizedTest
        @NullAndEmptySource
        void testInvalidGardenName(String invalidGardenName) {
            assertThrows(IllegalArgumentException.class,
                () -> Garden.of(
                    "인천시 서구 가정동",
                    LATITUDE,
                    LONGITUDE,
                    GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                    invalidGardenName,
                    GardenType.PUBLIC,
                    GardenStatus.ACTIVE,
                    "10",
                    "123-456-7890",
                    "Large",
                    "A wonderful garden",
                    LocalDate.now(),
                    LocalDate.now().plusDays(30),
                    "화장실, 농기구 등",
                    1L,
                    false,
                    0
                ));
        }

        @DisplayName("텃밭의 신청 링크가 null 혹은 빈값이면 예외를 던진다.")
        @ParameterizedTest
        @NullAndEmptySource
        void testInvalidLinkForRequest(String invalidLinkForRequest) {
            assertThrows(IllegalArgumentException.class,
                () -> Garden.of(
                    "인천시 서구 가정동",
                    LATITUDE,
                    LONGITUDE,
                    GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                    "진겸이네 텃밭",
                    GardenType.PUBLIC,
                    GardenStatus.ACTIVE,
                    "10",
                    "123-456-7890",
                    "Large",
                    "A wonderful garden",
                    LocalDate.now(),
                    LocalDate.now().plusDays(30),
                    "화장실, 농기구 등",
                    1L,
                    false,
                    0
                ));
        }

        @DisplayName("텃밭의 가격가 null 혹은 빈값이면 예외를 던진다.")
        @ParameterizedTest
        @NullAndEmptySource
        void testInvalidPrice(String invalidPrice) {
            assertThrows(IllegalArgumentException.class,
                () -> Garden.of(
                    "인천시 서구 가정동",
                    LATITUDE,
                    LONGITUDE,
                    GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                    "진겸이네 텃밭",
                    GardenType.PUBLIC,
                    GardenStatus.ACTIVE,
                    invalidPrice,
                    "123-456-7890",
                    "Large",
                    "A wonderful garden",
                    LocalDate.now(),
                    LocalDate.now().plusDays(30),
                    "화장실, 농기구 등",
                    1L,
                    false,
                    0
                ));
        }

        @DisplayName("텃밭의 전화번호가 null 혹은 빈값이면 예외를 던진다.")
        @ParameterizedTest
        @NullAndEmptySource
        void testInvalidContact(String invalidContact) {
            assertThrows(IllegalArgumentException.class,
                () -> Garden.of(
                    "인천시 서구 가정동",
                    LATITUDE,
                    LONGITUDE,
                    GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                    "진겸이네 텃밭",
                    GardenType.PUBLIC,
                    GardenStatus.ACTIVE,
                    "20000",
                    invalidContact,
                    "Large",
                    "A wonderful garden",
                    LocalDate.now(),
                    LocalDate.now().plusDays(30),
                    "화장실, 농기구 등",
                    1L,
                    false,
                    0
                ));
        }

        @DisplayName("텃밭의 크기가 null 혹은 빈값이면 예외를 던진다.")
        @ParameterizedTest
        @NullAndEmptySource
        void testInvalidSize(String invalidSize) {
            assertThrows(IllegalArgumentException.class,
                () -> Garden.of(
                    "인천시 서구 가정동",
                    LATITUDE,
                    LONGITUDE,
                    GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                    "진겸이네 텃밭",
                    GardenType.PUBLIC,
                    GardenStatus.ACTIVE,
                    "20000",
                    "000-00000-0000",
                    invalidSize,
                    "A wonderful garden",
                    LocalDate.now(),
                    LocalDate.now().plusDays(30),
                    "화장실, 농기구 등",
                    1L,
                    false,
                    0
                ));
        }

    }


    @DisplayName("음수, 기본 길이 등 엣지 케이스에서 에외가 발생하는 상황을 검증합니다.")
    @Nested
    class EdgeCase {

        @DisplayName("텃밭의 가격이 음수일 때 예외를 던진다.")
        @ParameterizedTest
        @ValueSource(strings = {"-1", "-10000"})
        void testInvalidPrice(String invalidPrice) {
            assertThrows(IllegalArgumentException.class,
                () -> Garden.of(
                    "인천시 서구 가정동",
                    LATITUDE,
                    LONGITUDE,
                    GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                    "진겸이네 텃밭",
                    GardenType.PUBLIC,
                    GardenStatus.ACTIVE,
                    invalidPrice,
                    "123-456-7890",
                    "Large",
                    "A wonderful garden",
                    LocalDate.now(),
                    LocalDate.now().plusDays(30),
                    "화장실, 농기구 등",
                    1L,
                    false,
                    0
                ));
        }

        @DisplayName("텃밭의 크기가 음수일 때 예외를 던진다.")
        @ParameterizedTest
        @ValueSource(strings = {"-1", "-100000"})
        void testInvalidSize(String invalidSize) {
            assertThrows(IllegalArgumentException.class,
                () -> Garden.of(
                    "인천시 서구 가정동",
                    LATITUDE,
                    LONGITUDE,
                    GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                    "진겸이네 텃밭",
                    GardenType.PUBLIC,
                    GardenStatus.ACTIVE,
                    "20000",
                    "000-00000-0000",
                    invalidSize,
                    "A wonderful garden",
                    LocalDate.now(),
                    LocalDate.now().plusDays(30),
                    "화장실, 농기구 등",
                    1L,
                    false,
                    0
                ));
        }

        @DisplayName("텃밭의 설명이 최대 글자 수보다 크면 예외를 던진다.")
        @ParameterizedTest
        @MethodSource("provideInvalidDescriptions")
        void testInvalidDescriptionLength(String invalidDescription) {
            assertThrows(IllegalArgumentException.class,
                () -> Garden.of(
                    "인천시 서구 가정동",
                    LATITUDE,
                    LONGITUDE,
                    GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                    "진겸이네 텃밭",
                    GardenType.PUBLIC,
                    GardenStatus.ACTIVE,
                    "20000",
                    "000-00000-0000",
                    "100",
                    invalidDescription,
                    LocalDate.now(),
                    LocalDate.now().plusDays(30),
                    "화장실, 농기구 등",
                    1L,
                    false,
                    0
                ));
        }

        static Stream<String> provideInvalidDescriptions() {
            return Stream.of(generateStringWithLength(102));
        }

        private static String generateStringWithLength(int length) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append("a");
            }
            return sb.toString();
        }
    }
}
