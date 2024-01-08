package com.garden.back.repository.chatentry;

import com.garden.back.domain.ChatType;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Chat room entry repository implementation using local storage.
 */
@Component
public class ChatRoomRoomEntryLocalRepository implements ChatRoomEntryRepository {
    private final Map<String, Set<Long>> chatEntries = new ConcurrentHashMap<>();

    public void addMemberToRoom(Long roomId, ChatType chatType, Long memberId) {
        chatEntries.computeIfAbsent(
                        generateRoomKey(roomId, chatType.getChatTypeId()), key -> ConcurrentHashMap.newKeySet())
                .add(memberId);
    }

    public void removeMemberFromRoom(Long roomId, ChatType chatType, Long memberId) {
        chatEntries.get(
                        generateRoomKey(roomId, chatType.getChatTypeId()))
                .remove(memberId);
    }

    public Set<Long> getMembersInRoom(Long roomId, ChatType chatType) {
        return chatEntries.getOrDefault(
                generateRoomKey(roomId, chatType.getChatTypeId()),
                Collections.emptySet());
    }

    public boolean isMemberInRoom(Long roomId, ChatType chatType, Long memberId) {
        return chatEntries.get(
                        generateRoomKey(roomId, chatType.getChatTypeId()))
                .contains(memberId);
    }

    public void deleteChatRoomEntryByRoomId(Long roomId, ChatType chatType) {
        chatEntries.remove(generateRoomKey(roomId, chatType.getChatTypeId()));
    }

    private String generateRoomKey(Long roomId, Long chatTypeId) {
        return roomId + ":" + chatTypeId;
    }

}
