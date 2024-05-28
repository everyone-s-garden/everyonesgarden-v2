package com.garden.back.chat.gardenchat.controller.dto.response;

public record GardenChatRoomCreateResponse(
    Long chatRoomId
) {
    public static GardenChatRoomCreateResponse to(
        Long chatRoomId
    ) {
        return new GardenChatRoomCreateResponse(chatRoomId);
    }
}
