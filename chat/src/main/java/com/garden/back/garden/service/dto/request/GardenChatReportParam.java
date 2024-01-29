package com.garden.back.garden.service.dto.request;

import com.garden.back.garden.domain.dto.GardenChatReportDomainParam;
import com.garden.back.global.ChatReportType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record GardenChatReportParam (
        Long reporterId,
        Long chatRoomId,
        ChatReportType chatReportType,
        List<MultipartFile> images,
        String reportContents
) {

    public GardenChatReportDomainParam toGardenChatReportDomainParam() {
        return new GardenChatReportDomainParam(
                reporterId,
                chatRoomId,
                chatReportType,
                reportContents
        );
    }

}
