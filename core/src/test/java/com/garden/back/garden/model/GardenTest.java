package com.garden.back.garden.model;

import com.garden.back.garden.model.vo.GardenStatus;
import com.garden.back.garden.model.vo.GardenType;
import com.garden.back.global.GeometryUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

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
                    () -> new Garden(
                            invalidAddress,
                            LATITUDE,
                            LONGITUDE,
                            GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                            "Beautiful Garden",
                            GardenType.PUBLIC,
                            GardenStatus.ACTIVE,
                            "http://example.com/request",
                            "10",
                            "123-456-7890",
                            "Large",
                            "A wonderful garden",
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(30),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(365),
                            true,
                            false,
                            true,
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
                    () -> new Garden(
                            "인천시 서구 가정동",
                            LATITUDE,
                            LONGITUDE,
                            GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                            invalidGardenName,
                            GardenType.PUBLIC,
                            GardenStatus.ACTIVE,
                            "http://example.com/request",
                            "10",
                            "123-456-7890",
                            "Large",
                            "A wonderful garden",
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(30),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(365),
                            true,
                            false,
                            true,
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
                    () -> new Garden(
                            "인천시 서구 가정동",
                            LATITUDE,
                            LONGITUDE,
                            GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                            "진겸이네 텃밭",
                            GardenType.PUBLIC,
                            GardenStatus.ACTIVE,
                            invalidLinkForRequest,
                            "10",
                            "123-456-7890",
                            "Large",
                            "A wonderful garden",
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(30),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(365),
                            true,
                            false,
                            true,
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
                    () -> new Garden(
                            "인천시 서구 가정동",
                            LATITUDE,
                            LONGITUDE,
                            GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                            "진겸이네 텃밭",
                            GardenType.PUBLIC,
                            GardenStatus.ACTIVE,
                            "www.every-garden.com",
                            invalidPrice,
                            "123-456-7890",
                            "Large",
                            "A wonderful garden",
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(30),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(365),
                            true,
                            false,
                            true,
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
                    () -> new Garden(
                            "인천시 서구 가정동",
                            LATITUDE,
                            LONGITUDE,
                            GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                            "진겸이네 텃밭",
                            GardenType.PUBLIC,
                            GardenStatus.ACTIVE,
                            "www.every-garden.com",
                            "20000",
                            invalidContact,
                            "Large",
                            "A wonderful garden",
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(30),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(365),
                            true,
                            false,
                            true,
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
                    () -> new Garden(
                            "인천시 서구 가정동",
                            LATITUDE,
                            LONGITUDE,
                            GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                            "진겸이네 텃밭",
                            GardenType.PUBLIC,
                            GardenStatus.ACTIVE,
                            "www.every-garden.com",
                            "20000",
                            "000-00000-0000",
                            invalidSize,
                            "A wonderful garden",
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(30),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(365),
                            true,
                            false,
                            true,
                            1L,
                            false,
                            0
                    ));
        }

        @DisplayName("텃밭의 설명이 null 혹은 빈값이면 예외를 던진다.")
        @ParameterizedTest
        @NullAndEmptySource
        void testInvalidDescription(String invalidDescription) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Garden(
                            "인천시 서구 가정동",
                            LATITUDE,
                            LONGITUDE,
                            GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                            "진겸이네 텃밭",
                            GardenType.PUBLIC,
                            GardenStatus.ACTIVE,
                            "www.every-garden.com",
                            "20000",
                            "000-00000-0000",
                            "100",
                            invalidDescription,
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(30),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(365),
                            true,
                            false,
                            true,
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
                    () -> new Garden(
                            "인천시 서구 가정동",
                            LATITUDE,
                            LONGITUDE,
                            GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                            "진겸이네 텃밭",
                            GardenType.PUBLIC,
                            GardenStatus.ACTIVE,
                            "www.every-garden.com",
                            invalidPrice,
                            "123-456-7890",
                            "Large",
                            "A wonderful garden",
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(30),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(365),
                            true,
                            false,
                            true,
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
                    () -> new Garden(
                            "인천시 서구 가정동",
                            LATITUDE,
                            LONGITUDE,
                            GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                            "진겸이네 텃밭",
                            GardenType.PUBLIC,
                            GardenStatus.ACTIVE,
                            "www.every-garden.com",
                            "20000",
                            "000-00000-0000",
                            invalidSize,
                            "A wonderful garden",
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(30),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(365),
                            true,
                            false,
                            true,
                            1L,
                            false,
                            0
                    ));
        }

        @DisplayName("텃밭의 설명이 최소 글자 수보다 작으면 예외를 던진다.")
        @ParameterizedTest
        @ValueSource(strings = {"123456789"})
        void testInvalidDescriptionLength(String invalidDescription) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Garden(
                            "인천시 서구 가정동",
                            LATITUDE,
                            LONGITUDE,
                            GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                            "진겸이네 텃밭",
                            GardenType.PUBLIC,
                            GardenStatus.ACTIVE,
                            "www.every-garden.com",
                            "20000",
                            "000-00000-0000",
                            "100",
                            invalidDescription,
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(30),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusDays(365),
                            true,
                            false,
                            true,
                            1L,
                            false,
                            0
                    ));
        }


    }
}
