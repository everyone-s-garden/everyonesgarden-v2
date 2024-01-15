package com.garden.back.repository.chatentry;

public record ChatRoomEntry(
        Long sessionId,
        ChatRoomEntryInfo chatRoomEntryInfo
) {
    public record ChatRoomEntryInfo(
            Long roomId,
            Long memberId
    ){}
}
