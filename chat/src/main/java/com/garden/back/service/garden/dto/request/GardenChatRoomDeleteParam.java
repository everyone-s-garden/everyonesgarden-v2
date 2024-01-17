package com.garden.back.service.garden.dto.request;

public record GardenChatRoomDeleteParam(
        Long chatRoomId,
        Long deleteRequestMemberId
) {
}
