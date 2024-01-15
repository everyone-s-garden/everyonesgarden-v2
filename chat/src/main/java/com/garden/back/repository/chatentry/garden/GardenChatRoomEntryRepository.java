package com.garden.back.repository.chatentry.garden;

import com.garden.back.repository.chatentry.ChatRoomEntry;

public interface GardenChatRoomEntryRepository {

    void addMemberToRoom(ChatRoomEntry chatRoomEntry);

    void removeMemberFromRoom(Long sessionId);


    boolean isMemberInRoom(ChatRoomEntry chatRoomEntry);

    void deleteChatRoomEntryByRoomId(Long sessionId);

    boolean isContainsRoomIdAndMember(Long roomId, Long memberId);

}
