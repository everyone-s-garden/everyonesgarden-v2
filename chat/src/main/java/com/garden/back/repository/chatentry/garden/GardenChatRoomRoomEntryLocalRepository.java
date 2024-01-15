package com.garden.back.repository.chatentry.garden;

import com.garden.back.repository.chatentry.ChatRoomEntry;
import com.garden.back.repository.chatentry.SessionId;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Chat room entry repository implementation using local storage.
 */
@Component
public class GardenChatRoomRoomEntryLocalRepository {
    private final Map<SessionId, ChatRoomEntry.ChatRoomEntryInfo> chatEntries = new ConcurrentHashMap<>();

    public void addMemberToRoom(ChatRoomEntry chatRoomEntry) {
        chatEntries.put(chatRoomEntry.sessionId(), chatRoomEntry.chatRoomEntryInfo());
    }

    public void removeMemberFromRoom(SessionId sessionId) {
        chatEntries.remove(sessionId);
    }

    public boolean isMemberInRoom(ChatRoomEntry chatRoomEntry) {
        if (chatEntries.get(chatRoomEntry.sessionId()) == null) {
            return false;
        }
        return chatEntries.get(chatRoomEntry.sessionId()).equals(chatRoomEntry.chatRoomEntryInfo());
    }

    public boolean isContainsRoomIdAndMember(Long roomId, Long memberId) {
        return chatEntries.values()
                .stream()
                .anyMatch(entryInfo -> Objects.equals(entryInfo.roomId(), roomId)
                        && Objects.equals(entryInfo.memberId(), memberId));
    }

    public void deleteChatRoomEntryByRoomId(SessionId sessionId) {
        chatEntries.remove(sessionId);
    }

}
