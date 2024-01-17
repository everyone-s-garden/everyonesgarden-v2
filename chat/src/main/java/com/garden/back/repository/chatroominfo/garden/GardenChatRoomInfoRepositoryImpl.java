package com.garden.back.repository.chatroominfo.garden;

import com.garden.back.domain.garden.GardenChatRoomInfo;
import com.garden.back.repository.chatroom.dto.ChatRoomCreateRepositoryParam;
import com.garden.back.repository.chatroominfo.garden.dto.GardenChatRoomEnterRepositoryResponse;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
public class GardenChatRoomInfoRepositoryImpl implements GardenChatRoomInfoRepository{

    private final GardenChatRoomInfoJpaRepository gardenChatRoomInfoJpaRepository;

    public GardenChatRoomInfoRepositoryImpl(GardenChatRoomInfoJpaRepository gardenChatRoomInfoJpaRepository) {
        this.gardenChatRoomInfoJpaRepository = gardenChatRoomInfoJpaRepository;
    }

    @Override
    public boolean existsByParams(ChatRoomCreateRepositoryParam param) {
        return gardenChatRoomInfoJpaRepository.existsByParams(param);
    }

    @Override
    public GardenChatRoomEnterRepositoryResponse findPartnerId(Long chatRoomId, Long memberId) {
        return gardenChatRoomInfoJpaRepository.findPartnerId(chatRoomId, memberId);
    }

    @Override
    public List<GardenChatRoomInfo> findByRoomId(Long chatRoomId) {
        List<GardenChatRoomInfo> gardenChatRoomInfos = gardenChatRoomInfoJpaRepository.findByRoomId(chatRoomId);
        return Objects.requireNonNullElse(gardenChatRoomInfos, Collections.emptyList());
    }

    @Override
    public void deleteAll(Long chatRoomId) {
        gardenChatRoomInfoJpaRepository.deleteAll(chatRoomId);
    }

    @Override
    public List<GardenChatRoomInfo> saveAll(List<GardenChatRoomInfo> gardenChatRoomInfos) {
        return gardenChatRoomInfoJpaRepository.saveAll(gardenChatRoomInfos);
    }

    @Override
    public List<GardenChatRoomInfo> findAll() {
        return gardenChatRoomInfoJpaRepository.findAll();
    }

}
