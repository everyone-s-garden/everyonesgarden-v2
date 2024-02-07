package com.garden.back.domain.user.request;

import com.garden.back.domain.user.MemberMannerGrade;

public record FindAllMemberByMannerGradeRequest(
    MemberMannerGrade memberMannerGrade,
    Integer offset,
    Integer limit
) {
}
