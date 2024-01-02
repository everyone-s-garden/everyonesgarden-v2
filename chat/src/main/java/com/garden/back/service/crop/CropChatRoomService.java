package com.garden.back.service.crop;

import com.garden.back.domain.ChatType;
import com.garden.back.domain.crop.CropChatRoom;
import com.garden.back.domain.crop.CropChatRoomInfo;
import com.garden.back.exception.ChatRoomAccessException;
import com.garden.back.repository.chatentry.ChatRoomEntryRepository;
import com.garden.back.repository.chatmessage.crop.CropChatMessageRepository;
import com.garden.back.repository.chatroom.crop.CropChatRoomRepository;
import com.garden.back.repository.chatroominfo.crop.CropChatRoomInfoRepository;
import com.garden.back.service.dto.request.ChatRoomEntryParam;
import com.garden.back.service.dto.request.CropChatRoomCreateParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.garden.back.global.exception.ErrorCode.CHAT_ROOM_ACCESS_ERROR;

@Service
public class CropChatRoomService {

    private final CropChatRoomRepository cropChatRoomRepository;
    private final CropChatRoomInfoRepository cropChatRoomInfoRepository;
    private final ChatRoomEntryRepository chatRoomEntryRepository;
    private final CropChatMessageRepository cropChatMessageRepository;

    public CropChatRoomService(CropChatRoomRepository cropChatRoomRepository, CropChatRoomInfoRepository cropChatRoomInfoRepository, ChatRoomEntryRepository chatRoomEntryRepository, CropChatMessageRepository cropChatMessageRepository) {
        this.cropChatRoomRepository = cropChatRoomRepository;
        this.cropChatRoomInfoRepository = cropChatRoomInfoRepository;
        this.chatRoomEntryRepository = chatRoomEntryRepository;
        this.cropChatMessageRepository = cropChatMessageRepository;
    }

    @Transactional
    public Long createCropChatRoom(CropChatRoomCreateParam param) {
        if (cropChatRoomInfoRepository.existsByParams(param.toChatRoomCreateRepositoryParam())) {
            throw new IllegalArgumentException("해당 게시글과 유저에 관한 채팅방은 이미 존재하여 새롭게 생성할 수 없습니다.");
        }

        CropChatRoom savedChatRoom = cropChatRoomRepository.save(param.toChatRoom());

        CropChatRoomInfo chatRoomInfoToWriter = param.toChatRoomInfoToWriter();
        CropChatRoomInfo chatRoomInfoToViewer = param.toChatRoomInfoToViewer();
        chatRoomInfoToWriter.create(savedChatRoom);
        chatRoomInfoToViewer.create(savedChatRoom);
        cropChatRoomInfoRepository.saveAll(List.of(chatRoomInfoToWriter, chatRoomInfoToViewer));

        return savedChatRoom.getChatRoomId();
    }

    @Transactional
    public void enterGardenChatRoom(ChatRoomEntryParam param) {
        if (!chatRoomEntryRepository.isMemberInRoom(
                param.roomId(),
                ChatType.CROP,
                param.memberId())) {
            throw new ChatRoomAccessException(CHAT_ROOM_ACCESS_ERROR);
        }

        chatRoomEntryRepository.addMemberToRoom(
                param.roomId(),
                ChatType.CROP,
                param.memberId());
        readAllGardenMessages(param);
    }

    private void readAllGardenMessages(ChatRoomEntryParam param) {
        Long partnerId = cropChatRoomInfoRepository.findPartnerId(
                param.roomId(),
                param.memberId());

        cropChatMessageRepository.markMessagesAsRead(
                param.roomId(),
                partnerId);
    }
}
