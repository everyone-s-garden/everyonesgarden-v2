package com.garden.back.repository.chatentry;

import com.garden.back.domain.ChatType;

import java.util.Set;

public interface ChatRoomEntryRepository {

    void addMemberToRoom(Long roomId, ChatType chatType, Long memberId);

    void removeMemberFromRoom(Long roomId, ChatType chatType, Long memberId);

    Set<Long> getMembersInRoom(Long roomId, ChatType chatType);

    boolean isMemberInRoom(Long roomId, ChatType chatType, Long memberId);

    void deleteChatRoomEntryByRoomId(Long roomId, ChatType chatType);

}
