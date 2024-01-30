package com.garden.back.chat.gardenchat.controller.dto.request;

import com.garden.back.garden.service.dto.request.GardenChatReportParam;
import com.garden.back.global.ChatReportType;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.global.validation.EnumValue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

public record GardenChatReportRequest(
    @NotNull(message = "신고 당한 사람의 아이디는 null일 수 없습니다.")
    @Positive(message = "신고 당한 사람의 아이디는 양수여야 합니다.")
    Long reportedMemberId,
    String reportContent,

    @NotNull(message = "신고 타입을 입력해주세요.")
    @EnumValue(enumClass = ChatReportType.class, message = "잘못된 신고 항목입니다.")
    String reportType
) {

    public GardenChatReportParam toGardenChatReportRequest(
        LoginUser loginUser,
        Long roomId,
        List<MultipartFile> images) {
        if (images == null) {
            images = Collections.emptyList();
        }
        return new GardenChatReportParam(
            reportedMemberId,
            loginUser.memberId(),
            roomId,
            ChatReportType.valueOf(reportType),
            images,
            reportContent
        );
    }
}
