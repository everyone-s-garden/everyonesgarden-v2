package com.garden.back.repository.chatentry.garden;

import com.garden.back.repository.chatentry.ChatRoomEntry;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Chat room entry repository implementation using local storage.
 */
@Component
public class GardenChatRoomRoomEntryLocalRepository {
    private final Map<Long, ChatRoomEntry.ChatRoomEntryInfo> chatEntries = new ConcurrentHashMap<>();

    public void addMemberToRoom(ChatRoomEntry chatRoomEntry) {
        chatEntries.put(chatRoomEntry.sessionId(), chatRoomEntry.chatRoomEntryInfo());
    }

    public void removeMemberFromRoom(Long sessionId) {
        chatEntries.remove(sessionId);
    }

    public boolean isMemberInRoom(ChatRoomEntry chatRoomEntry) {
        return chatEntries.get(chatRoomEntry.sessionId()).equals(chatRoomEntry.chatRoomEntryInfo());
    }

    public boolean isContainsRoomIdAndMember(Long roomId, Long memberId) {
        return chatEntries.values()
                .stream()
                .anyMatch(entryInfo -> Objects.equals(entryInfo.roomId(), roomId)
                        && Objects.equals(entryInfo.memberId(), memberId));
    }

    public void deleteChatRoomEntryByRoomId(Long sessionId) {
        chatEntries.remove(sessionId);
    }

}
