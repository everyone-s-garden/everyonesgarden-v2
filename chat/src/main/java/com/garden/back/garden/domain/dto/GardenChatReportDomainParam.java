package com.garden.back.garden.domain.dto;

import com.garden.back.global.ChatReportType;

public record GardenChatReportDomainParam(
    Long reportedMemberId,
    Long reporterId,
    Long roomId,
    ChatReportType commentReportType,
    String reportContents
) {
}
