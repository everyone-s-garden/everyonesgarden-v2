package com.garden.back.chat.gardenchat.controller.dto.response;

public record GardenChatReportResponse(
        Long reportId
) {
    public static GardenChatReportResponse of(
            Long reportId
    ) {
        return new GardenChatReportResponse(
                reportId
        );
    }
}
