package com.garden.back.member.dto;

import com.garden.back.member.service.dto.MemberMyPageResult;

public record MemberMyPageResponse(
    String nickname,
    String profileImage,
    String memberMannerGrade
) {
    public static MemberMyPageResponse to(MemberMyPageResult result) {
        return new MemberMyPageResponse(
            result.nickname(),
            result.imageUrl(),
            result.memberMannerGrade()
        );
    }
}
