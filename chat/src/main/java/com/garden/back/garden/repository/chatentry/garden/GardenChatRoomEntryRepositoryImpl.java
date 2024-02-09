package com.garden.back.garden.repository.chatentry.garden;

import com.garden.back.garden.repository.chatentry.ChatRoomEntry;
import com.garden.back.garden.repository.chatentry.SessionId;
import com.garden.back.global.exception.ChatRoomAccessException;
import org.springframework.stereotype.Component;

import static com.garden.back.global.exception.ErrorCode.CHAT_ROOM_SESSION_ACCESS_ERROR;

@Component
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
    public void removeMemberFromRoom(SessionId sessionId) {
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
    public void deleteChatRoomEntryByRoomId(SessionId sessionId) {
        gardenChatRoomRoomEntryLocalRepository.deleteChatRoomEntryByRoomId(sessionId);
    }

    @Override
    public boolean isContainsRoomIdAndMember(Long roomId, Long memberId) {
        return gardenChatRoomRoomEntryLocalRepository.isContainsRoomIdAndMember(roomId, memberId);
    }

}
