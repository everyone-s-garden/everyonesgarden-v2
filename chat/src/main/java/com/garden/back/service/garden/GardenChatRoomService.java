package com.garden.back.service;

import com.garden.back.domain.ChatType;
import com.garden.back.domain.crop.CropChatRoom;
import com.garden.back.domain.crop.CropChatRoomInfo;
import com.garden.back.domain.garden.GardenChatRoom;
import com.garden.back.domain.garden.GardenChatRoomInfo;
import com.garden.back.exception.ChatRoomAccessException;
import com.garden.back.repository.chatentry.ChatRoomEntryRepository;
import com.garden.back.repository.chatmessage.garden.GardenChatMessageRepository;
import com.garden.back.repository.chatroom.garden.GardenChatRoomRepository;
import com.garden.back.repository.chatroominfo.garden.GardenChatRoomInfoRepository;
import com.garden.back.service.dto.request.ChatRoomEntryParam;
import com.garden.back.service.dto.request.CropChatRoomCreateParam;
import com.garden.back.service.dto.request.GardenChatRoomCreateParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.garden.back.global.exception.ErrorCode.CHAT_ROOM_ACCESS_ERROR;

@Service
public class ChatRoomService {

    private final GardenChatRoomRepository gardenChatRoomRepository;
    private final GardenChatRoomInfoRepository gardenChatRoomInfoRepository;
    private final ChatRoomEntryRepository chatRoomEntryRepository;
    private final GardenChatMessageRepository gardenChatMessageRepository;
    private final CropChatRoomR

    public ChatRoomService(
            GardenChatRoomRepository gardenChatRoomRepository,
            GardenChatRoomInfoRepository gardenChatRoomInfoRepository,
            ChatRoomEntryRepository chatRoomEntryRepository,
            GardenChatMessageRepository gardenChatMessageRepository) {
        this.gardenChatRoomRepository = gardenChatRoomRepository;
        this.gardenChatRoomInfoRepository = gardenChatRoomInfoRepository;
        this.chatRoomEntryRepository = chatRoomEntryRepository;
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
    public Long createCropChatRoom(CropChatRoomCreateParam param) {
        if (gardenChatRoomInfoRepository.existsByParams(param.toChatRoomCreateRepositoryParam())) {
            throw new IllegalArgumentException("해당 게시글과 유저에 관한 채팅방은 이미 존재하여 새롭게 생성할 수 없습니다.");
        }

        CropChatRoom savedChatRoom = gardenChatRoomRepository.save(param.toChatRoom());

        CropChatRoomInfo chatRoomInfoToWriter = param.toChatRoomInfoToWriter();
        CropChatRoomInfo chatRoomInfoToViewer = param.toChatRoomInfoToViewer();
        chatRoomInfoToWriter.create(savedChatRoom);
        chatRoomInfoToViewer.create(savedChatRoom);
        gardenChatRoomInfoRepository.saveAll(List.of(chatRoomInfoToWriter, chatRoomInfoToViewer));

        return savedChatRoom.getChatRoomId();
    }

    @Transactional
    public void enterGardenChatRoom(ChatRoomEntryParam param) {
        if (!chatRoomEntryRepository.isMemberInRoom(
                param.roomId(),
                ChatType.GARDEN,
                param.memberId())) {
            throw new ChatRoomAccessException(CHAT_ROOM_ACCESS_ERROR);
        }

        chatRoomEntryRepository.addMemberToRoom(
                param.roomId(),
                ChatType.GARDEN,
                param.memberId());
        readAllGardenMessages(param);
    }

    private void readAllGardenMessages(ChatRoomEntryParam param) {
        Long partnerId = gardenChatRoomInfoRepository.findPartnerId(
                param.roomId(),
                param.memberId());

        gardenChatMessageRepository.markMessagesAsRead(
                param.roomId(),
                partnerId);
    }



}
