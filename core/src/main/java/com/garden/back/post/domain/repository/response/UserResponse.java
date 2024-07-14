package com.garden.back.post.domain.repository.response;

import com.garden.back.member.MemberMannerGrade;

public record UserResponse(
    Long userId,
    String profile,
    String name,
    MemberMannerGrade memberMannerGrade,
    String email
) {
}
