package com.garden.back.service.garden.dto.request;

import com.garden.back.repository.chatentry.ChatRoomEntry;

public record GardenSessionCreateParam(
        Long sessionId,
        Long roomId,
        Long memberId
) {
    public ChatRoomEntry toChatRoomEntry(){
        return new ChatRoomEntry(
                sessionId,
                new ChatRoomEntry.ChatRoomEntryInfo(
                        roomId,
                        memberId
                )
        );
    }
}
