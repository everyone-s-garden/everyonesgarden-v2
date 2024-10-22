package com.garden.back.garden.repository.chatroominfo;

import com.garden.back.garden.domain.GardenChatRoomInfo;
import com.garden.back.garden.repository.chatroom.dto.ChatRoomCreateRepositoryParam;
import com.garden.back.garden.repository.chatroominfo.dto.GardenChatRoomEnterRepositoryResponse;
import com.garden.back.global.exception.EntityNotFoundException;
import com.garden.back.global.exception.ErrorCode;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
public class GardenChatRoomInfoRepositoryImpl implements GardenChatRoomInfoRepository {

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

    @Override
    public Long getChatRoomId(Long memberId, Long postId) {
        return gardenChatRoomInfoJpaRepository.findChatRoomId(memberId, postId)
            .orElse(NOT_EXISTED_ROOM_ID);
    }

    @Override
    public int getExitedChatRoomMember(Long chatRoomId) {
        if(findByRoomId(chatRoomId).isEmpty()) {
            throw new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY);
        }
        return gardenChatRoomInfoJpaRepository.getSizeExitedChatRoomMember(chatRoomId);
    }

}
