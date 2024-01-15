package com.garden.back.service.crop;

import com.garden.back.domain.crop.CropChatRoom;
import com.garden.back.domain.crop.CropChatRoomInfo;
import com.garden.back.repository.chatentry.garden.GardenChatRoomEntryRepository;
import com.garden.back.repository.chatmessage.crop.CropChatMessageRepository;
import com.garden.back.repository.chatroom.crop.CropChatRoomRepository;
import com.garden.back.repository.chatroominfo.crop.CropChatRoomInfoRepository;
import com.garden.back.service.crop.request.CropChatRoomCreateParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CropChatRoomService {

    private final CropChatRoomRepository cropChatRoomRepository;
    private final CropChatRoomInfoRepository cropChatRoomInfoRepository;
    private final GardenChatRoomEntryRepository gardenChatRoomEntryRepository;
    private final CropChatMessageRepository cropChatMessageRepository;

    public CropChatRoomService(CropChatRoomRepository cropChatRoomRepository, CropChatRoomInfoRepository cropChatRoomInfoRepository, GardenChatRoomEntryRepository gardenChatRoomEntryRepository, CropChatMessageRepository cropChatMessageRepository) {
        this.cropChatRoomRepository = cropChatRoomRepository;
        this.cropChatRoomInfoRepository = cropChatRoomInfoRepository;
        this.gardenChatRoomEntryRepository = gardenChatRoomEntryRepository;
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

}
