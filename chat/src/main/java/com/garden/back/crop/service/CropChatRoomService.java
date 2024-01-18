package com.garden.back.crop.service;

import com.garden.back.crop.domain.CropChatRoom;
import com.garden.back.crop.domain.CropChatRoomInfo;
import com.garden.back.crop.repository.chatroom.CropChatRoomRepository;
import com.garden.back.crop.repository.chatroomInfo.CropChatRoomInfoRepository;
import com.garden.back.crop.service.request.CropChatRoomCreateParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CropChatRoomService {

    private final CropChatRoomRepository cropChatRoomRepository;
    private final CropChatRoomInfoRepository cropChatRoomInfoRepository;

    public CropChatRoomService(CropChatRoomRepository cropChatRoomRepository, CropChatRoomInfoRepository cropChatRoomInfoRepository) {
        this.cropChatRoomRepository = cropChatRoomRepository;
        this.cropChatRoomInfoRepository = cropChatRoomInfoRepository;
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

}
