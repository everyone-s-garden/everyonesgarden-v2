package com.garden.back.api.user.request;

import com.garden.back.domain.user.MemberMannerGrade;
import com.garden.back.domain.user.request.FindAllMemberByMannerGradeRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record GetMemberByMannerGradeOrderRequest(
    @NotNull(message = "memberMannerGrade를 입력해 주세요.")
    MemberMannerGrade memberMannerGrade,

    @PositiveOrZero(message = "0 이상의 수를 입력해 주세요.")
    Integer offset,

    @Positive(message = "양수를 입력해 주세요.")
    Integer limit
) {
    public FindAllMemberByMannerGradeRequest toServiceRequest() {
        return new FindAllMemberByMannerGradeRequest(memberMannerGrade, offset, limit);
    }
}
