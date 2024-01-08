package com.garden.back.member.service.dto;

import com.garden.back.member.repository.MyPageMemberRepositoryResponse;

public record MemberMyPageResult(
        String nickname
){
    public static MemberMyPageResult to(MyPageMemberRepositoryResponse response) {
        return new MemberMyPageResult(
                response.getNickname()
        );
    }
}
