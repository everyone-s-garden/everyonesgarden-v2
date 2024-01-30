package com.garden.back.appointment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @ParameterizedTest
    @MethodSource("provideParametersForConstructorTest")
    @DisplayName("Appointment 생성자 검증 테스트")
    void testAppointmentConstructor(LocalDateTime appointmentDate, Long assignerId, Long receiverId, Integer reminderBeforeMinutes, boolean expectException) {
        if (expectException) {
            assertThrows(IllegalArgumentException.class, () -> {
                new Appointment(appointmentDate, assignerId, receiverId, reminderBeforeMinutes);
            });
        } else {
            assertDoesNotThrow(() -> {
                Appointment appointment = new Appointment(appointmentDate, assignerId, receiverId, reminderBeforeMinutes);
                assertNotNull(appointment);
            });
        }
    }

    private static Stream<Arguments> provideParametersForConstructorTest() {
        return Stream.of(
            // 테스트 케이스 형식: appointmentDate, assignerId, receiverId, reminderBeforeMinutes, expectException
            Arguments.of(LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusDays(1), 1L, 2L, 30, false), // 정상 케이스
            Arguments.of(LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(1), 1L, 2L, 30, true), // 잘못된 날짜(과거)
            Arguments.of(LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusDays(1), null, 2L, 30, true), // assignerId가 null
            Arguments.of(LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusDays(1), 1L, null, 30, true), // receiverId가 null
            Arguments.of(LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusDays(1), 1L, 2L, null, true) // reminderBeforeMinutes가 null
        );
    }
}
