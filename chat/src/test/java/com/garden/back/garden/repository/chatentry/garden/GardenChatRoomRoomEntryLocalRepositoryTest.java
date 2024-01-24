package com.garden.back.garden.repository.chatentry.garden;

import com.garden.back.garden.repository.chatentry.ChatRoomEntry;
import com.garden.back.garden.repository.chatentry.SessionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GardenChatRoomRoomEntryLocalRepositoryTest {

    private ChatRoomEntry chatRoomEntry;
    @Autowired
    private GardenChatRoomRoomEntryLocalRepository repository;

    @BeforeEach
    void setUp() {
        chatRoomEntry = new ChatRoomEntry(SessionId.of("111"), new ChatRoomEntry.ChatRoomEntryInfo(1L, 1L));
        repository = new GardenChatRoomRoomEntryLocalRepository();
    }

    @Test
    void addMemberToRoom() {
        // When
        repository.addMemberToRoom(chatRoomEntry);

        // Then
        assertTrue(repository.isMemberInRoom(chatRoomEntry));
    }

    @Test
    void removeMemberFromRoom() {
        // Given
        repository.addMemberToRoom(chatRoomEntry);

        // When
        repository.removeMemberFromRoom(chatRoomEntry.sessionId());

        // Then
        assertFalse(repository.isMemberInRoom(chatRoomEntry));
    }

    @Test
    void isMemberInRoom() {
        // Given
        assertFalse(repository.isMemberInRoom(chatRoomEntry));
        repository.addMemberToRoom(chatRoomEntry);

        // When & Then
        assertTrue(repository.isMemberInRoom(chatRoomEntry));
    }

    @Test
    void isContainsRoomIdAndMember() {
        // Given
        Long roomId = 2L;
        Long memberId = 2L;

        repository.addMemberToRoom(chatRoomEntry);

        // When & Then
        assertFalse(repository.isContainsRoomIdAndMember(roomId, memberId));
        assertTrue(repository.isContainsRoomIdAndMember(
                chatRoomEntry.chatRoomEntryInfo().roomId(),
                chatRoomEntry.chatRoomEntryInfo().memberId()));
    }

    @Test
    void deleteChatRoomEntryByRoomId() {
        // Given
        repository.addMemberToRoom(chatRoomEntry);

        // When
        repository.deleteChatRoomEntryByRoomId(chatRoomEntry.sessionId());

        // Then
        assertFalse(repository.isMemberInRoom(chatRoomEntry));
    }
}