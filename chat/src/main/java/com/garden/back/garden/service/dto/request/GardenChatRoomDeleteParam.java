package com.garden.back.garden.service.dto.request;

public record GardenChatRoomDeleteParam(
        Long chatRoomId,
        Long deleteRequestMemberId
) {
    public static GardenChatRoomDeleteParam of(
            Long chatRoomId,
            Long deleteRequestMemberId
    ){
        return new GardenChatRoomDeleteParam(
                chatRoomId,
                deleteRequestMemberId
        );
    }
}
