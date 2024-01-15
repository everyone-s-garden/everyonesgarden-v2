package com.garden.back.service.garden.dto.request;

import com.garden.back.repository.chatentry.ChatRoomEntry;
import com.garden.back.repository.chatentry.SessionId;

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
