package com.garden.back.member.repository.response;

import com.garden.back.member.MemberMannerGrade;

public record MemberInfoResponse(
    String nickname,
    String profileImageUrl,
    MemberMannerGrade memberMannerGrade
) {
}
