package com.garden.back.service.garden;

import com.garden.back.domain.ChatType;
import com.garden.back.domain.garden.GardenChatRoom;
import com.garden.back.domain.garden.GardenChatRoomInfo;
import com.garden.back.repository.chatentry.ChatRoomEntryRepository;
import com.garden.back.repository.chatmessage.garden.GardenChatMessageRepository;
import com.garden.back.repository.chatroom.garden.GardenChatRoomRepository;
import com.garden.back.repository.chatroominfo.garden.GardenChatRoomInfoRepository;
import com.garden.back.repository.chatroominfo.garden.dto.GardenChatRoomEnterRepositoryResponse;
import com.garden.back.service.dto.request.ChatRoomEntryParam;
import com.garden.back.service.dto.request.ChatRoomEntryResult;
import com.garden.back.service.dto.request.GardenChatRoomCreateParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GardenChatRoomService {

    private final GardenChatRoomRepository gardenChatRoomRepository;
    private final GardenChatRoomInfoRepository gardenChatRoomInfoRepository;
    private final ChatRoomEntryRepository chatRoomEntryRepository;
    private final GardenChatMessageRepository gardenChatMessageRepository;

    public GardenChatRoomService(
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
    public ChatRoomEntryResult enterGardenChatRoom(ChatRoomEntryParam param) {
        chatRoomEntryRepository.addMemberToRoom(
                param.roomId(),
                ChatType.GARDEN,
                param.memberId());

        return ChatRoomEntryResult.to(readAllGardenMessages(param));
    }

    private GardenChatRoomEnterRepositoryResponse readAllGardenMessages(ChatRoomEntryParam param) {
        GardenChatRoomEnterRepositoryResponse response
                = gardenChatRoomInfoRepository.findPartnerId(
                param.roomId(),
                param.memberId());

        gardenChatMessageRepository.markMessagesAsRead(
                param.roomId(),
                response.getMemberId());
        return response;
    }

}
