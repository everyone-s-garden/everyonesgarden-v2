package com.garden.back.garden.service;

import com.garden.back.garden.domain.GardenChatMessage;
import com.garden.back.garden.domain.dto.GardenChatMessageDomainParam;
import com.garden.back.garden.repository.chatentry.SessionId;
import com.garden.back.garden.repository.chatentry.garden.GardenChatRoomEntryRepository;
import com.garden.back.garden.repository.chatmessage.garden.GardenChatMessageRepository;
import com.garden.back.garden.repository.chatroominfo.garden.GardenChatRoomInfoRepository;
import com.garden.back.garden.service.dto.request.GardenChatMessageSendParam;
import com.garden.back.garden.service.dto.request.GardenChatMessagesGetParam;
import com.garden.back.garden.service.dto.response.GardenChatMessageSendResult;
import com.garden.back.garden.service.dto.response.GardenChatMessagesGetResults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GardenChatService {
    private final int GARDEN_CHAT_MESSAGE_PAGE_SIZE = 10;
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

        gardenChatRoomEntryRepository.isMemberInRoom(param.toChatRoomEntry());

        GardenChatMessageDomainParam gardenChatMessageDomainParam = param.toGardenChatMessageDomainParam();
        Long partnerId = gardenChatRoomInfoRepository.findPartnerId(param.roomId(), param.memberId()).getMemberId();
        if (gardenChatRoomEntryRepository.isContainsRoomIdAndMember(param.roomId(), partnerId)) {
            return GardenChatMessageSendResult.to(
                    gardenChatMessageRepository.save(
                            GardenChatMessage.toReadGardenChatMessage(gardenChatMessageDomainParam))
            );
        }

        return GardenChatMessageSendResult.to(
                gardenChatMessageRepository.save(
                        GardenChatMessage.toNotReadGardenChatMessage(gardenChatMessageDomainParam)
                )
        );

    }

    @Transactional
    public void leaveChatRoom(SessionId sessionId) {
        gardenChatRoomEntryRepository.deleteChatRoomEntryByRoomId(sessionId);
    }

    @Transactional
    public GardenChatMessagesGetResults getChatRoomMessages(GardenChatMessagesGetParam param) {

        Pageable pageable = PageRequest.of(param.pageNumber(), GARDEN_CHAT_MESSAGE_PAGE_SIZE);
        Slice<GardenChatMessage> gardenChatMessage = gardenChatMessageRepository.getGardenChatMessage(param.chatRoomId(), pageable);

        return GardenChatMessagesGetResults.to(gardenChatMessage, param.memberId());
    }

}
