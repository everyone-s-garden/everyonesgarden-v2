package com.garden.back.member;

public record ChatReportEvent(
    Long reportedMemberId,
    Float score
) {
}
