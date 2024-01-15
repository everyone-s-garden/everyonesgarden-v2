package com.garden.back.service;

import com.garden.back.domain.garden.GardenChatMessage;
import com.garden.back.repository.chatentry.garden.GardenChatRoomEntryRepository;
import com.garden.back.repository.chatmessage.garden.GardenChatMessageRepository;
import com.garden.back.service.garden.GardenChatRoomService;
import com.garden.back.service.garden.GardenChatService;
import com.garden.back.service.garden.dto.request.GardenChatMessageSendParam;
import com.garden.back.service.garden.dto.request.GardenChatRoomCreateParam;
import com.garden.back.service.garden.dto.request.GardenSessionCreateParam;
import com.garden.back.service.garden.dto.response.GardenChatMessageSendResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class GardenChatServiceTest extends IntegrationTestSupport {

    @Autowired
    private GardenChatRoomService gardenChatRoomService;

    @Autowired
    private GardenChatService gardenChatService;

    @Autowired
    private GardenChatMessageRepository gardenChatMessageRepository;

    @Autowired
    private GardenChatRoomEntryRepository gardenChatRoomEntryRepository;

    @DisplayName("텃밭 분양 관련 채팅 메세지를 보낼 때 상대방 유저가 채팅 세션에 접속 중이면 모두 읽음 메세지로 표시된다.")
    @Test
    void saveMessage_exitedPartner_allReadMessage() {
        //Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);

        GardenSessionCreateParam gardenSessionCreateParamAboutMe = ChatRoomFixture.gardenSessionCreateParamAboutMe();
        GardenSessionCreateParam gardenSessionCreateParamAboutPartner = ChatRoomFixture.gardenSessionCreateParamAboutPartner();


        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutMe);
        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutPartner);

        GardenChatMessageSendParam gardenChatMessageSendParamFirst = ChatRoomFixture.gardenChatMessageSendParamFirst();
        GardenChatMessageSendParam gardenChatMessageSendParamSecond = ChatRoomFixture.gardenChatMessageSendParamSecond();

        // When
        GardenChatMessageSendResult firstGardenChatMessageSendResult = gardenChatService.saveMessage(gardenChatMessageSendParamFirst);
        GardenChatMessageSendResult secondGardenChatMessageSendResult = gardenChatService.saveMessage(gardenChatMessageSendParamSecond);
        GardenChatMessage firstGardenChatMessage = gardenChatMessageRepository.getById(firstGardenChatMessageSendResult.chatMessageId());
        GardenChatMessage secondGardenChatMessage = gardenChatMessageRepository.getById(secondGardenChatMessageSendResult.chatMessageId());

        // Then
        assertThat(firstGardenChatMessage.isReadOrNot()).isEqualTo(true);
        assertThat(secondGardenChatMessage.isReadOrNot()).isEqualTo(true);
    }

    @DisplayName("텃밭 분양 관련 채팅 메세지를 보낼 때 상대방 유저가 채팅 세션에 접속 중이지 않으면 모두 읽지 않음으로 처리된다.")
    @Test
    void saveMessage_notExitedPartner_notReadMessage() {
        //Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);

        GardenSessionCreateParam gardenSessionCreateParamAboutMe = ChatRoomFixture.gardenSessionCreateParamAboutMe();
        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutMe);

        GardenChatMessageSendParam gardenChatMessageSendParamFirst = ChatRoomFixture.gardenChatMessageSendParamFirst();
        GardenChatMessageSendParam gardenChatMessageSendParamSecond = ChatRoomFixture.gardenChatMessageSendParamSecond();

        // When
        GardenChatMessageSendResult firstGardenChatMessageSendResult = gardenChatService.saveMessage(gardenChatMessageSendParamFirst);
        GardenChatMessageSendResult secondGardenChatMessageSendResult = gardenChatService.saveMessage(gardenChatMessageSendParamSecond);
        GardenChatMessage firstGardenChatMessage = gardenChatMessageRepository.getById(firstGardenChatMessageSendResult.chatMessageId());
        GardenChatMessage secondGardenChatMessage = gardenChatMessageRepository.getById(secondGardenChatMessageSendResult.chatMessageId());

        // Then
        assertThat(firstGardenChatMessage.isReadOrNot()).isEqualTo(false);
        assertThat(secondGardenChatMessage.isReadOrNot()).isEqualTo(false);
    }

}