package com.garden.back.service.garden.dto.request;

import com.garden.back.domain.garden.dto.GardenChatMessageDomainParam;
import com.garden.back.repository.chatentry.ChatRoomEntry;
import com.garden.back.repository.chatentry.SessionId;

public record GardenChatMessageSendParam(
        SessionId sessionId,
        Long memberId,
        Long roomId,
        String content
) {

    public ChatRoomEntry toChatRoomEntry() {
        return new ChatRoomEntry(
                sessionId,
                new ChatRoomEntry.ChatRoomEntryInfo(
                        roomId,
                        memberId
                )
        );
    }

    public GardenChatMessageDomainParam toGardenChatMessageDomainParam() {
        return new GardenChatMessageDomainParam(
                roomId,
                memberId,
                content
        );
    }
}