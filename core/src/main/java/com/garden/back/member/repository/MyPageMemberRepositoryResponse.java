package com.garden.back.member.repository;

import com.garden.back.member.MemberMannerGrade;

public interface MyPageMemberRepositoryResponse {

    String getNickname();

    String getProfileImage();

    MemberMannerGrade getMemberMannerGrade();

    String getEmail();
}
