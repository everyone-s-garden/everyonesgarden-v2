package com.garden.back.repository.chatroominfo.garden;

import com.garden.back.domain.garden.GardenChatRoomInfo;
import com.garden.back.repository.chatroom.dto.ChatRoomCreateRepositoryParam;
import com.garden.back.repository.chatroominfo.garden.dto.GardenChatRoomEnterRepositoryResponse;

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
