package com.garden.back.garden.service;

import com.garden.back.garden.domain.GardenChatRoom;
import com.garden.back.garden.domain.GardenChatRoomInfo;
import com.garden.back.garden.service.dto.request.GardenChatRoomCreateParam;
import com.garden.back.garden.service.dto.request.GardenChatRoomDeleteParam;
import com.garden.back.garden.service.dto.request.GardenChatRoomEntryParam;
import com.garden.back.global.exception.EntityNotFoundException;
import com.garden.back.global.exception.ErrorCode;
import com.garden.back.garden.repository.chatentry.garden.GardenChatRoomEntryRepository;
import com.garden.back.garden.repository.chatmessage.GardenChatMessageRepository;
import com.garden.back.garden.repository.chatroom.garden.GardenChatRoomRepository;
import com.garden.back.garden.repository.chatroominfo.GardenChatRoomInfoRepository;
import com.garden.back.garden.repository.chatroominfo.dto.GardenChatRoomEnterRepositoryResponse;
import com.garden.back.garden.service.dto.request.GardenSessionCreateParam;
import com.garden.back.garden.service.dto.response.GardenChatRoomEntryResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GardenChatRoomService {

    private final GardenChatRoomRepository gardenChatRoomRepository;
    private final GardenChatRoomInfoRepository gardenChatRoomInfoRepository;
    private final GardenChatRoomEntryRepository gardenChatRoomEntryRepository;
    private final GardenChatMessageRepository gardenChatMessageRepository;

    public GardenChatRoomService(
            GardenChatRoomRepository gardenChatRoomRepository,
            GardenChatRoomInfoRepository gardenChatRoomInfoRepository,
            GardenChatRoomEntryRepository gardenChatRoomEntryRepository,
            GardenChatMessageRepository gardenChatMessageRepository) {
        this.gardenChatRoomRepository = gardenChatRoomRepository;
        this.gardenChatRoomInfoRepository = gardenChatRoomInfoRepository;
        this.gardenChatRoomEntryRepository = gardenChatRoomEntryRepository;
        this.gardenChatMessageRepository = gardenChatMessageRepository;
    }

    @Transactional
    public Long createGardenChatRoom(GardenChatRoomCreateParam param) {
        if (gardenChatRoomInfoRepository.existsByParams(param.toChatRoomCreateRepositoryParam())) {
            throw new IllegalArgumentException("해당 게시글과 유저에 관한 채팅방은 이미 존재하여 새롭게 생성할 수 없습니다.");
        }

        GardenChatRoom savedChatRoom = gardenChatRoomRepository.save(param.toChatRoom());

        GardenChatRoomInfo chatRoomInfoToWriter = param.toChatRoomInfoToWriter();
        GardenChatRoomInfo chatRoomInfoToViewer = param.toChatRoomInfoToViewer();
        chatRoomInfoToWriter.create(savedChatRoom);
        chatRoomInfoToViewer.create(savedChatRoom);
        gardenChatRoomInfoRepository.saveAll(List.of(chatRoomInfoToWriter, chatRoomInfoToViewer));

        return savedChatRoom.getChatRoomId();
    }

    @Transactional
    public GardenChatRoomEntryResult enterGardenChatRoom(GardenChatRoomEntryParam param) {
        return GardenChatRoomEntryResult.to(readAllGardenMessages(param));
    }

    private GardenChatRoomEnterRepositoryResponse readAllGardenMessages(GardenChatRoomEntryParam param) {
        GardenChatRoomEnterRepositoryResponse response
                = gardenChatRoomInfoRepository.findPartnerId(
                param.roomId(),
                param.memberId());

        gardenChatMessageRepository.markMessagesAsRead(
                param.roomId(),
                response.getMemberId());
        return response;
    }

    public void createSessionInfo(GardenSessionCreateParam param) {
        gardenChatRoomEntryRepository.addMemberToRoom(param.toChatRoomEntry());
    }

    @Transactional
    public void deleteChatRoom(GardenChatRoomDeleteParam param) {
        GardenChatRoom gardenChatRoom = gardenChatRoomRepository.findById(param.chatRoomId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY));
        gardenChatRoomInfoRepository.findByRoomId(param.chatRoomId()).forEach(
                gardenChatRoomInfo -> gardenChatRoomInfo.deleteChatRoomInfo(param.deleteRequestMemberId()));

        if (gardenChatRoom.isRoomEmpty()) {
            gardenChatRoomInfoRepository.deleteAll(param.chatRoomId());
            gardenChatRoomRepository.deleteById(param.chatRoomId());
        }
    }
}