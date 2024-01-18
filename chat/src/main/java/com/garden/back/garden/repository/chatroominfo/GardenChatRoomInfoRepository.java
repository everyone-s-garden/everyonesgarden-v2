package com.garden.back.garden.repository.chatroominfo;

import com.garden.back.garden.domain.GardenChatRoomInfo;
import com.garden.back.garden.repository.chatroom.dto.ChatRoomCreateRepositoryParam;
import com.garden.back.garden.repository.chatroominfo.dto.GardenChatRoomEnterRepositoryResponse;

import java.util.List;

public interface GardenChatRoomInfoRepository {

    boolean existsByParams(ChatRoomCreateRepositoryParam param);

    GardenChatRoomEnterRepositoryResponse findPartnerId(Long chatRoomId,
                                                        Long memberId);

    List<GardenChatRoomInfo> findByRoomId(Long chatRoomId);

    void deleteAll(Long chatRoomId);

    List<GardenChatRoomInfo> saveAll(List<GardenChatRoomInfo> gardenChatRoomInfos);

    List<GardenChatRoomInfo> findAll();
}
