package com.garden.back.repository.chatentry;

public record ChatRoomEntry(
        SessionId sessionId,
        ChatRoomEntryInfo chatRoomEntryInfo
) {
    public record ChatRoomEntryInfo(
            Long roomId,
            Long memberId
    ){}
}
