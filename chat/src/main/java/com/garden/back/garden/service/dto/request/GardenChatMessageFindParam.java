package com.garden.back.garden.service.dto.request;

public record GardenChatMessageFindParam(
        Long memberId,
        int pageNumber
) {
}
