package com.garden.back.member.service.dto;

import com.garden.back.member.repository.MyPageMemberRepositoryResponse;

public record MemberMyPageResult(
    String nickname,
    String imageUrl,
    String memberMannerGrade
) {
    public static MemberMyPageResult to(MyPageMemberRepositoryResponse response) {
        return new MemberMyPageResult(
            response.getNickname(),
            response.getProfileImage(),
            response.getMemberMannerGrade().name()
        );
    }
}
