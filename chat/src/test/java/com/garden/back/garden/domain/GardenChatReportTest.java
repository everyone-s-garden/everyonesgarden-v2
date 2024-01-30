package com.garden.back.garden.domain;

import com.garden.back.garden.domain.dto.GardenChatReportDomainParam;
import com.garden.back.global.ChatReportType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GardenChatReportTest {

    @DisplayName("신고당한 사람의 아이디는 null일 수 없습니다.")
    @ParameterizedTest
    @NullSource
    void throwException_nullReceivedId(Long receivedId) {
        assertThrows(IllegalArgumentException.class,
            () -> GardenChatReport.create(
                new GardenChatReportDomainParam(
                    receivedId,
                    1L,
                    1L,
                    ChatReportType.DISPUTE,
                    "사기 당했습니다."
                )
            )
        );
    }

    @DisplayName("신고한 사람의 아이디는 null일 수 없습니다.")
    @ParameterizedTest
    @NullSource
    void throwException_nullReporterId(Long reporterId) {
        assertThrows(IllegalArgumentException.class,
            () -> GardenChatReport.create(
                new GardenChatReportDomainParam(
                    1L,
                    reporterId,
                    1L,
                    ChatReportType.DISPUTE,
                    "사기 당했습니다."
                )
            )
        );
    }

    @DisplayName("채팅방의 아이디는 null일 수 없습니다.")
    @ParameterizedTest
    @NullSource
    void throwException_nullRoomId(Long roomId) {
        assertThrows(IllegalArgumentException.class,
            () -> GardenChatReport.create(
                new GardenChatReportDomainParam(
                    1L,
                    1L,
                    roomId,
                    ChatReportType.DISPUTE,
                    "사기 당했습니다."
                )
            )
        );
    }

    @DisplayName("신고당한 사람의 아이디는 0이거나 음수일 수 없습니다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    void throwException_negativeOrZeroRoomId(Long roomId) {
        assertThrows(IllegalArgumentException.class,
            () -> GardenChatReport.create(
                new GardenChatReportDomainParam(
                    1L,
                    1L,
                    roomId,
                    ChatReportType.DISPUTE,
                    "사기 당했습니다."
                )
            )
        );
    }

    @DisplayName("신고한 사람의 아이디는 0이거나 음수일 수 없습니다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    void throwException_negativeOrZeroReporterId(Long reporterId) {
        assertThrows(IllegalArgumentException.class,
            () -> GardenChatReport.create(
                new GardenChatReportDomainParam(
                    1L,
                    reporterId,
                    1L,
                    ChatReportType.DISPUTE,
                    "사기 당했습니다."
                )
            )
        );
    }

    @DisplayName("채팅방의 아이디는 0이거나 음수일 수 없습니다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    void throwException_negativeOrZeroReceivedId(Long receivedId) {
        assertThrows(IllegalArgumentException.class,
            () -> GardenChatReport.create(
                new GardenChatReportDomainParam(
                    receivedId,
                    1L,
                    1L,
                    ChatReportType.DISPUTE,
                    "사기 당했습니다."
                )
            )
        );
    }

}
