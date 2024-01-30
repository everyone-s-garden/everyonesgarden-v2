package com.garden.back.member;

public record ChatReportEvent(
    Long reporterId,
    Float score
) {
}
