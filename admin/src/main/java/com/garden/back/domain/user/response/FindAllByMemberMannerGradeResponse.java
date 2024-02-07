package com.garden.back.domain.user.response;

import java.util.List;

public record FindAllByMemberMannerGradeResponse(
    List<Result> results
) {
    public record Result(
        Long id,
        String nickname
    ) {
        public static Result from(MemberInfoResponse memberInfoResponse) {
            return new Result(memberInfoResponse.getId(), memberInfoResponse.getNickname());
        }
    }

    public static FindAllByMemberMannerGradeResponse from(List<MemberInfoResponse> memberInfoResponses) {
        return new FindAllByMemberMannerGradeResponse(memberInfoResponses.stream().map(Result::from).toList());
    }
}
