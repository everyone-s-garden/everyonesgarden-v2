package com.garden.back.garden.service;

import com.garden.back.garden.domain.GardenChatMessage;
import com.garden.back.garden.domain.dto.GardenChatMessageDomainParam;
import com.garden.back.garden.repository.chatentry.SessionId;
import com.garden.back.garden.repository.chatentry.garden.GardenChatRoomEntryRepository;
import com.garden.back.garden.repository.chatmessage.ChatRoomFindRepositoryResponse;
import com.garden.back.garden.repository.chatmessage.GardenChatMessageRepository;
import com.garden.back.garden.repository.chatroominfo.GardenChatRoomInfoRepository;
import com.garden.back.garden.repository.websocketinfo.WebSocketInfoRepository;
import com.garden.back.garden.service.dto.request.GardenChatRoomsFindParam;
import com.garden.back.garden.service.dto.request.GardenChatMessageSendParam;
import com.garden.back.garden.service.dto.request.GardenChatMessagesGetParam;
import com.garden.back.garden.service.dto.response.GardenChatRoomsFindResults;
import com.garden.back.garden.service.dto.response.GardenChatMessageSendResult;
import com.garden.back.garden.service.dto.response.GardenChatMessagesGetResults;
import com.garden.back.util.PageMaker;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GardenChatService {
    private final GardenChatMessageRepository gardenChatMessageRepository;
    private final GardenChatRoomInfoRepository gardenChatRoomInfoRepository;
    private final GardenChatRoomEntryRepository gardenChatRoomEntryRepository;
    private final WebSocketInfoRepository webSocketInfoRepository;

    public GardenChatService(GardenChatMessageRepository gardenChatMessageRepository, GardenChatRoomInfoRepository gardenChatRoomInfoRepository, GardenChatRoomEntryRepository gardenChatRoomEntryRepository, WebSocketInfoRepository webSocketInfoRepository) {
        this.gardenChatMessageRepository = gardenChatMessageRepository;
        this.gardenChatRoomInfoRepository = gardenChatRoomInfoRepository;
        this.gardenChatRoomEntryRepository = gardenChatRoomEntryRepository;
        this.webSocketInfoRepository = webSocketInfoRepository;
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

    public void leaveChatRoom(SessionId sessionId) {
        gardenChatRoomEntryRepository.deleteChatRoomEntryByRoomId(sessionId);
    }

    public void saveSocketInfo(String sessionId, Long memberId) {
        webSocketInfoRepository.save(sessionId, memberId);
    }

    public Long getWebSocketInfo(String sessionId) {
        return webSocketInfoRepository.getMemberId(sessionId);
    }

    @Transactional
    public GardenChatMessagesGetResults getChatRoomMessages(GardenChatMessagesGetParam param) {
        Pageable pageable = PageMaker.makePage(param.pageNumber());
        Slice<GardenChatMessage> gardenChatMessage = gardenChatMessageRepository.getGardenChatMessage(param.chatRoomId(), pageable);

        return GardenChatMessagesGetResults.to(gardenChatMessage, param.memberId());
    }

    @Transactional(readOnly = true)
    public GardenChatRoomsFindResults findChatMessagesInRooms(GardenChatRoomsFindParam param) {
        Pageable pageable = PageMaker.makePage(param.pageNumber());
        Slice<ChatRoomFindRepositoryResponse> chatRooms = gardenChatMessageRepository.findChatRooms(param.memberId(), pageable);

        Map<Long, String> messageById = chatRooms.stream()
            .collect(Collectors.toMap(
                ChatRoomFindRepositoryResponse::getChatMessageId,
                response -> getMessageContent(response.getChatMessageId())
            ));

        return GardenChatRoomsFindResults.to(chatRooms, messageById);
    }

    private String getMessageContent(Long chatMessageId) {
        return gardenChatMessageRepository.getContentsById(chatMessageId);
    }

}
