package com.garden.back.garden.service.dto.request;

public record GardenChatRoomsFindParam(
        Long memberId,
        int pageNumber
) {
}
