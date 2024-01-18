package com.garden.back.garden.service.dto.request;

import com.garden.back.garden.domain.dto.GardenChatMessageDomainParam;
import com.garden.back.garden.repository.chatentry.ChatRoomEntry;
import com.garden.back.garden.repository.chatentry.SessionId;

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