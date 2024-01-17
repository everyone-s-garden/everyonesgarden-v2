package com.garden.back.garden.repository.chatentry.garden;

import com.garden.back.garden.repository.chatentry.SessionId;
import com.garden.back.garden.repository.chatentry.ChatRoomEntry;

public interface GardenChatRoomEntryRepository {

    void addMemberToRoom(ChatRoomEntry chatRoomEntry);

    void removeMemberFromRoom(SessionId sessionId);


    boolean isMemberInRoom(ChatRoomEntry chatRoomEntry);

    void deleteChatRoomEntryByRoomId(SessionId sessionId);

    boolean isContainsRoomIdAndMember(Long roomId, Long memberId);

}
