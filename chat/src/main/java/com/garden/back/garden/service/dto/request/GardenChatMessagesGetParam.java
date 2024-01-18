package com.garden.back.garden.service.dto.request;

public record GardenChatMessagesGetParam(
        Long memberId,
        Long chatRoomId,
        int pageNumber
) {
    public static GardenChatMessagesGetParam of(
            Long memberId,
            Long chatRoomId,
            int pageNumber
    ){
        return new GardenChatMessagesGetParam(
                memberId,
                chatRoomId,
                pageNumber
        );
    }
}
