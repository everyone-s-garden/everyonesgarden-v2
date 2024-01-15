package com.garden.back.repository.chatentry.garden;

import com.garden.back.repository.chatentry.ChatRoomEntry;
import com.garden.back.repository.chatentry.SessionId;

public interface GardenChatRoomEntryRepository {

    void addMemberToRoom(ChatRoomEntry chatRoomEntry);

    void removeMemberFromRoom(SessionId sessionId);


    boolean isMemberInRoom(ChatRoomEntry chatRoomEntry);

    void deleteChatRoomEntryByRoomId(SessionId sessionId);

    boolean isContainsRoomIdAndMember(Long roomId, Long memberId);

}
