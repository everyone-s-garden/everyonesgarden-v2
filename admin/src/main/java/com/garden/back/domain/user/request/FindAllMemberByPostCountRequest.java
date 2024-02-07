package com.garden.back.domain.user.request;

public record FindAllMemberByPostCountRequest(
    Integer offset,
    Integer limit
) {
}
