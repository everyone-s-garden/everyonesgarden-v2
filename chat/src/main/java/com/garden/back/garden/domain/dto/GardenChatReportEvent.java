package com.garden.back.garden.domain.dto;

import com.garden.back.garden.domain.GardenChatReport;
import com.garden.back.member.ChatReportEvent;

public record GardenChatReportEvent(
    Long reporterId,
    int reportScore
) {
    public static ChatReportEvent toChatReportEvent(GardenChatReport gardenChatReport) {
        return new ChatReportEvent(
            gardenChatReport.getReporterId(),
            (float) gardenChatReport.getReportScore()
        );
    }
}
