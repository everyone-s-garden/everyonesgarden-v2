package com.garden.back.service.garden;

import com.garden.back.domain.garden.GardenChatMessage;
import com.garden.back.repository.chatentry.garden.GardenChatRoomEntryRepository;
import com.garden.back.repository.chatmessage.garden.GardenChatMessageRepository;
import com.garden.back.repository.chatroominfo.garden.GardenChatRoomInfoRepository;
import com.garden.back.service.garden.dto.request.GardenChatMessageSendParam;
import com.garden.back.service.garden.dto.response.GardenChatMessageSendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GardenChatService {
    private final GardenChatMessageRepository gardenChatMessageRepository;
    private final GardenChatRoomInfoRepository gardenChatRoomInfoRepository;
    private final GardenChatRoomEntryRepository gardenChatRoomEntryRepository;


    public GardenChatService(GardenChatMessageRepository gardenChatMessageRepository, GardenChatRoomInfoRepository gardenChatRoomInfoRepository, GardenChatRoomEntryRepository gardenChatRoomEntryRepository) {
        this.gardenChatMessageRepository = gardenChatMessageRepository;
        this.gardenChatRoomInfoRepository = gardenChatRoomInfoRepository;
        this.gardenChatRoomEntryRepository = gardenChatRoomEntryRepository;
    }

    @Transactional
    public GardenChatMessageSendResult saveMessage(GardenChatMessageSendParam param) {
        Long partnerId = gardenChatRoomInfoRepository.findPartnerId(param.roomId(), param.memberId()).getMemberId();

        gardenChatRoomEntryRepository.isMemberInRoom(param.toChatRoomEntry());
        gardenChatRoomEntryRepository.isContainsRoomIdAndMember(param.roomId(), partnerId);

       return GardenChatMessageSendResult.to(
                gardenChatMessageRepository.save(
                GardenChatMessage.toReadGardenChatMessage(
                        param.toReadGardenChatMessage()))
        );
    }

}
