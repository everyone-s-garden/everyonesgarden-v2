package com.garden.back.repository.chatentry.garden;

import com.garden.back.exception.ChatRoomAccessException;
import com.garden.back.repository.chatentry.ChatRoomEntry;
import org.springframework.stereotype.Repository;

import static com.garden.back.global.exception.ErrorCode.CHAT_ROOM_SESSION_ACCESS_ERROR;

@Repository
public class GardenChatRoomEntryRepositoryImpl implements GardenChatRoomEntryRepository{

    private final GardenChatRoomRoomEntryLocalRepository gardenChatRoomRoomEntryLocalRepository;

    public GardenChatRoomEntryRepositoryImpl(GardenChatRoomRoomEntryLocalRepository gardenChatRoomRoomEntryLocalRepository) {
        this.gardenChatRoomRoomEntryLocalRepository = gardenChatRoomRoomEntryLocalRepository;
    }

    @Override
    public void addMemberToRoom(ChatRoomEntry chatRoomEntry) {
        gardenChatRoomRoomEntryLocalRepository.addMemberToRoom(chatRoomEntry);
    }

    @Override
    public void removeMemberFromRoom(Long sessionId) {
        gardenChatRoomRoomEntryLocalRepository.removeMemberFromRoom(sessionId);
    }

    @Override
    public boolean isMemberInRoom(ChatRoomEntry chatRoomEntry) {
        if(!gardenChatRoomRoomEntryLocalRepository.isMemberInRoom(chatRoomEntry)) {
            throw new ChatRoomAccessException(CHAT_ROOM_SESSION_ACCESS_ERROR);
        }
        return true;
    }

    @Override
    public void deleteChatRoomEntryByRoomId(Long sessionId) {
        gardenChatRoomRoomEntryLocalRepository.deleteChatRoomEntryByRoomId(sessionId);
    }

    @Override
    public boolean isContainsRoomIdAndMember(Long roomId, Long memberId) {
        if(!gardenChatRoomRoomEntryLocalRepository.isContainsRoomIdAndMember(roomId, memberId)) {
            throw new ChatRoomAccessException(CHAT_ROOM_SESSION_ACCESS_ERROR);
        }
        return true;
    }

}
