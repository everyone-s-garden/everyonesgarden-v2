package com.garden.back.chat.gardenchat.controller.dto.response;

public record GardenChatRoomGetExitedResponse(
    boolean isExitedPartner
) {
    public static GardenChatRoomGetExitedResponse of(
        boolean isExitedPartner
    ) {
        return new GardenChatRoomGetExitedResponse(
            isExitedPartner
        );
    }
}
