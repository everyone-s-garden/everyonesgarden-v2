package com.garden.back.chat.gardenchat.controller.dto.request;

import com.garden.back.garden.service.dto.request.GardenChatReportParam;
import com.garden.back.global.ChatReportType;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.global.validation.EnumValue;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

public record GardenChatReportRequest(
        String reportContent,

        @NotNull(message = "신고 타입을 입력해주세요.")
        @EnumValue(enumClass = ChatReportType.class, message = "잘못된 신고 항목입니다.")
        String reportType
) {

    public GardenChatReportParam toGardenChatReportRequest(
            LoginUser loginUser,
            Long roomId,
            List<MultipartFile> images) {
        if(images == null) {
            images = Collections.emptyList();
        }
        return new GardenChatReportParam(
                loginUser.memberId(),
                roomId,
                ChatReportType.valueOf(reportType),
                images,
                reportContent
        );
    }
}
