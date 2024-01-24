package com.garden.back.garden.service.dto.request;

import com.garden.back.garden.repository.chatentry.ChatRoomEntry;
import com.garden.back.garden.repository.chatentry.SessionId;

public record GardenSessionCreateParam(
        SessionId sessionId,
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
