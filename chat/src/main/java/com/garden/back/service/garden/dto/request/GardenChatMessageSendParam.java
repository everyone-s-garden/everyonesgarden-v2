package com.garden.back.service.garden.dto.request;

import com.garden.back.domain.garden.dto.ReadGardenChatMessage;
import com.garden.back.repository.chatentry.ChatRoomEntry;
import com.garden.back.repository.chatentry.SessionId;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record GardenChatMessageSendParam(
        SessionId sessionId,
        Long memberId,
        Long roomId,
        String content,
        List<MultipartFile> images
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

    public ReadGardenChatMessage toReadGardenChatMessage() {
        return new ReadGardenChatMessage(
                roomId,
                memberId,
                content,
                true
        );
    }
}