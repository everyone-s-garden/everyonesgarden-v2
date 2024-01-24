package com.garden.back.service;

import com.garden.back.garden.domain.GardenChatMessage;
import com.garden.back.garden.repository.chatentry.garden.GardenChatRoomEntryRepository;
import com.garden.back.garden.repository.chatmessage.GardenChatMessageRepository;
import com.garden.back.garden.service.GardenChatRoomService;
import com.garden.back.garden.service.GardenChatService;
import com.garden.back.garden.service.dto.request.GardenChatMessageSendParam;
import com.garden.back.garden.service.dto.request.GardenChatRoomCreateParam;
import com.garden.back.garden.service.dto.request.GardenSessionCreateParam;
import com.garden.back.garden.service.dto.response.GardenChatRoomsFindResults;
import com.garden.back.garden.service.dto.response.GardenChatMessageSendResult;
import com.garden.back.garden.service.dto.response.GardenChatMessagesGetResults;
import com.garden.back.global.exception.ChatRoomAccessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        Long gardenChatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);

        GardenSessionCreateParam gardenSessionCreateParamAboutMe = ChatRoomFixture.gardenSessionCreateParamAboutMe(gardenChatRoomId);
        GardenSessionCreateParam gardenSessionCreateParamAboutPartner = ChatRoomFixture.gardenSessionCreateParamAboutPartner(gardenChatRoomId);

        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutMe);
        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutPartner);

        GardenChatMessageSendParam gardenChatMessageSendParamFirst = ChatRoomFixture.gardenChatMessageSendParamFirstByMe(gardenChatRoomId);
        GardenChatMessageSendParam gardenChatMessageSendParamSecond = ChatRoomFixture.gardenChatMessageSendParamSecondByMe(gardenChatRoomId);

        // When
        GardenChatMessageSendResult firstGardenChatMessageSendResult = gardenChatService.saveMessage(gardenChatMessageSendParamFirst);
        GardenChatMessageSendResult secondGardenChatMessageSendResult = gardenChatService.saveMessage(gardenChatMessageSendParamSecond);
        GardenChatMessage firstGardenChatMessage = gardenChatMessageRepository.getById(firstGardenChatMessageSendResult.chatMessageId());
        GardenChatMessage secondGardenChatMessage = gardenChatMessageRepository.getById(secondGardenChatMessageSendResult.chatMessageId());

        // Then
        assertThat(firstGardenChatMessage.isReadOrNot()).isTrue();
        assertThat(secondGardenChatMessage.isReadOrNot()).isTrue();
    }

    @DisplayName("텃밭 분양 관련 채팅 메세지를 보낼 때 상대방 유저가 채팅 세션에 접속 중이지 않으면 모두 읽지 않음으로 처리된다.")
    @Test
    void saveMessage_notExitedPartner_notReadMessage() {
        //Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long gardenChatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);

        GardenSessionCreateParam gardenSessionCreateParamAboutMe = ChatRoomFixture.gardenSessionCreateParamAboutMe(gardenChatRoomId);
        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutMe);

        GardenChatMessageSendParam gardenChatMessageSendParamFirst = ChatRoomFixture.gardenChatMessageSendParamFirstByMe(gardenChatRoomId);
        GardenChatMessageSendParam gardenChatMessageSendParamSecond = ChatRoomFixture.gardenChatMessageSendParamSecondByMe(gardenChatRoomId);

        // When
        GardenChatMessageSendResult firstGardenChatMessageSendResult = gardenChatService.saveMessage(gardenChatMessageSendParamFirst);
        GardenChatMessageSendResult secondGardenChatMessageSendResult = gardenChatService.saveMessage(gardenChatMessageSendParamSecond);
        GardenChatMessage firstGardenChatMessage = gardenChatMessageRepository.getById(firstGardenChatMessageSendResult.chatMessageId());
        GardenChatMessage secondGardenChatMessage = gardenChatMessageRepository.getById(secondGardenChatMessageSendResult.chatMessageId());

        // Then
        assertThat(firstGardenChatMessage.isReadOrNot()).isFalse();
        assertThat(secondGardenChatMessage.isReadOrNot()).isFalse();
    }

    @DisplayName("메세지를 보낸 유저가 세션에 접속중이지 않으면 예외를 던진다.")
    @Test
    void saveMessage_notExistedMe_throwException() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long gardenChatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);

        GardenSessionCreateParam gardenSessionCreateParamAboutPartner = ChatRoomFixture.gardenSessionCreateParamAboutPartner(gardenChatRoomId);
        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutPartner);

        GardenChatMessageSendParam gardenChatMessageSendParamFirst = ChatRoomFixture.gardenChatMessageSendParamFirstByMe(gardenChatRoomId);

        // When_Then
        assertThatThrownBy(() -> gardenChatService.saveMessage(gardenChatMessageSendParamFirst)).
                isInstanceOf(ChatRoomAccessException.class);
    }

    @DisplayName("채팅방을 나가는 경우 세션 정보가 삭제된다.")
    @Test
    void leaveChatRoom() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long gardenChatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);

        GardenSessionCreateParam gardenSessionCreateParamAboutMe = ChatRoomFixture.gardenSessionCreateParamAboutMe(gardenChatRoomId);
        GardenSessionCreateParam gardenSessionCreateParamAboutPartner = ChatRoomFixture.gardenSessionCreateParamAboutPartner(gardenChatRoomId);

        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutMe);
        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutPartner);

        // When
        gardenChatService.leaveChatRoom(gardenSessionCreateParamAboutMe.sessionId());

        // Then
        assertThatThrownBy(() -> gardenChatRoomEntryRepository.isMemberInRoom(gardenSessionCreateParamAboutMe.toChatRoomEntry()))
                .isInstanceOf(ChatRoomAccessException.class);
    }

    @DisplayName("채팅방에 생성된 메세지를 최신순으로 볼 수 있으며 내가 보낸 메세지는 isMine에 true라고 표시된다.")
    @Test
    void getGardenChatMessages() {
        // Given
        Long memberIdAboutMe = 1L;
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long gardenChatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);

        // 상대방과 나의 세션 생성
        GardenSessionCreateParam gardenSessionCreateParamAboutMe = ChatRoomFixture.gardenSessionCreateParamAboutMe(gardenChatRoomId);
        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutMe);
        GardenSessionCreateParam gardenSessionCreateParamAboutPartner = ChatRoomFixture.gardenSessionCreateParamAboutPartner(gardenChatRoomId);
        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutPartner);

        // 내 메세지 전송
        GardenChatMessageSendParam gardenChatMessageSendParamFirstByMe = ChatRoomFixture.gardenChatMessageSendParamFirstByMe(gardenChatRoomId);
        GardenChatMessageSendParam gardenChatMessageSendParamSecondByMe = ChatRoomFixture.gardenChatMessageSendParamSecondByMe(gardenChatRoomId);
        GardenChatMessageSendResult firstGardenChatMessageByMe = gardenChatService.saveMessage(gardenChatMessageSendParamFirstByMe);
        GardenChatMessageSendResult secondGardenChatMessageByMe = gardenChatService.saveMessage(gardenChatMessageSendParamSecondByMe);

        // 상대방 메세지 전송
        GardenChatMessageSendParam gardenChatMessageSendParamFirstByPart = ChatRoomFixture.gardenChatMessageSendParamFirstByPartner(gardenChatRoomId);
        GardenChatMessageSendParam gardenChatMessageSendParamSecondByPart = ChatRoomFixture.gardenChatMessageSendParamSecondByPartner(gardenChatRoomId);
        GardenChatMessageSendResult firstGardenChatMessageByPart = gardenChatService.saveMessage(gardenChatMessageSendParamFirstByPart);
        GardenChatMessageSendResult secondGardenChatMessageByPart = gardenChatService.saveMessage(gardenChatMessageSendParamSecondByPart);

        // When
        GardenChatMessagesGetResults chatRoomMessages = gardenChatService.getChatRoomMessages(ChatRoomFixture.gardenChatMessagesGetParam(gardenChatRoomId));

        // Then
        assertThat(chatRoomMessages.gardenChatMessagesGetResponses().stream().toList()).extracting("createdAt")
                .containsExactly(
                        firstGardenChatMessageByMe.createdAt(),
                        secondGardenChatMessageByMe.createdAt(),
                        firstGardenChatMessageByPart.createdAt(),
                        secondGardenChatMessageByPart.createdAt()
                );

        chatRoomMessages.gardenChatMessagesGetResponses().stream()
                .filter(gardenChatMessagesGetResponse -> Objects.equals(gardenChatMessagesGetResponse.memberId(), memberIdAboutMe))
                .forEach(
                        gardenChatMessagesGetResponse -> assertThat(gardenChatMessagesGetResponse.isMine()).isTrue()
                );
    }

    @DisplayName("내가 생성한 채팅방 목록을 확인할 수 있다. 각 채팅방 목록에는 상대방의 아이디, 최근에 보낸 메세지, 읽지 않은 메세지 개수, 최근에 보낸 시간를 확인할 수 있다.")
    @Test
    void findChatMessageInRooms_existedSessionPartner() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long gardenChatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);
        Long myId = chatRoomCreateParam.writerId();
        Long partnerId = chatRoomCreateParam.viewerId();

        // 상대방과 나의 세션 생성
        GardenSessionCreateParam gardenSessionCreateParamAboutMe = ChatRoomFixture.gardenSessionCreateParamAboutMe(gardenChatRoomId);
        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutMe);
        GardenSessionCreateParam gardenSessionCreateParamAboutPartner = ChatRoomFixture.gardenSessionCreateParamAboutPartner(gardenChatRoomId);
        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutPartner);

        // 내 메세지 전송
        GardenChatMessageSendParam gardenChatMessageSendParamFirstByMe = ChatRoomFixture.gardenChatMessageSendParamFirstByMe(gardenChatRoomId);
        GardenChatMessageSendParam gardenChatMessageSendParamSecondByMe = ChatRoomFixture.gardenChatMessageSendParamSecondByMe(gardenChatRoomId);
        gardenChatService.saveMessage(gardenChatMessageSendParamFirstByMe);
        gardenChatService.saveMessage(gardenChatMessageSendParamSecondByMe);

        // 상대방 메세지 전송
        GardenChatMessageSendParam gardenChatMessageSendParamFirstByPart = ChatRoomFixture.gardenChatMessageSendParamFirstByPartner(gardenChatRoomId);
        GardenChatMessageSendParam gardenChatMessageSendParamSecondByPart = ChatRoomFixture.gardenChatMessageSendParamSecondByPartner(gardenChatRoomId);
        gardenChatService.saveMessage(gardenChatMessageSendParamFirstByPart);
        GardenChatMessageSendResult secondGardenChatMessageByPart = gardenChatService.saveMessage(gardenChatMessageSendParamSecondByPart);

        // When
        GardenChatRoomsFindResults chatMessagesInRooms = gardenChatService.findChatMessagesInRooms(ChatRoomFixture.gardenChatMessageFindParam(myId));
        GardenChatRoomsFindResults.GardenChatRoomsFindResult gardenChatRoomsFindResult = chatMessagesInRooms.gardenChatRoomsFindResults().get(0);

        // Then
        assertThat(gardenChatRoomsFindResult.recentContents()).isEqualTo(secondGardenChatMessageByPart.message());
        assertThat(gardenChatRoomsFindResult.readNotCnt()).isZero();

        assertThat(gardenChatRoomsFindResult.createdAt().getDayOfYear()).isEqualTo(secondGardenChatMessageByPart.createdAt().getDayOfYear());
        assertThat(gardenChatRoomsFindResult.createdAt().getMonth()).isEqualTo(secondGardenChatMessageByPart.createdAt().getMonth());
        assertThat(gardenChatRoomsFindResult.createdAt().getDayOfMonth()).isEqualTo(secondGardenChatMessageByPart.createdAt().getDayOfMonth());
        assertThat(gardenChatRoomsFindResult.createdAt().getHour()).isEqualTo(secondGardenChatMessageByPart.createdAt().getHour());
        assertThat(gardenChatRoomsFindResult.createdAt().getMinute()).isEqualTo(secondGardenChatMessageByPart.createdAt().getMinute());
        assertThat(gardenChatRoomsFindResult.createdAt().getSecond()).isEqualTo(secondGardenChatMessageByPart.createdAt().getSecond());

        assertThat(gardenChatRoomsFindResult.chatMessageId()).isEqualTo(secondGardenChatMessageByPart.chatMessageId());
        assertThat(gardenChatRoomsFindResult.chatRoomId()).isEqualTo(gardenChatRoomId);
        assertThat(gardenChatRoomsFindResult.partnerId()).isEqualTo(partnerId);
    }

    @DisplayName("내가 생성한 채팅방 목록을 확인할 수 있다. 각 채팅방 목록에는 상대방의 아이디, 최근에 보낸 메세지, 읽지 않은 메세지 개수, 최근에 보낸 시간를 확인할 수 있다. " +
            "상대방이 세션에 없어 메세지를 읽지 않았다면 이는 상대방의 채팅방 목록에 읽지 않은 메세지의 수에 표현된다.")
    @Test
    void findChatMessageInRooms_notExistedSessionPartner() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long gardenChatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);
        Long myId = chatRoomCreateParam.writerId();
        Long partnerId = chatRoomCreateParam.viewerId();

        // 상대방과 나의 세션 생성
        GardenSessionCreateParam gardenSessionCreateParamAboutMe = ChatRoomFixture.gardenSessionCreateParamAboutMe(gardenChatRoomId);
        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutMe);
        GardenSessionCreateParam gardenSessionCreateParamAboutPartner = ChatRoomFixture.gardenSessionCreateParamAboutPartner(gardenChatRoomId);
        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutPartner);

        // 내 메세지 전송
        GardenChatMessageSendParam gardenChatMessageSendParamFirstByMe = ChatRoomFixture.gardenChatMessageSendParamFirstByMe(gardenChatRoomId);
        GardenChatMessageSendParam gardenChatMessageSendParamSecondByMe = ChatRoomFixture.gardenChatMessageSendParamSecondByMe(gardenChatRoomId);
        gardenChatService.saveMessage(gardenChatMessageSendParamFirstByMe);
        gardenChatService.saveMessage(gardenChatMessageSendParamSecondByMe);

        // 나는 방에서 나감
        gardenChatService.leaveChatRoom(gardenSessionCreateParamAboutMe.sessionId());

        // 상대방 메세지 전송
        GardenChatMessageSendParam gardenChatMessageSendParamFirstByPart = ChatRoomFixture.gardenChatMessageSendParamFirstByPartner(gardenChatRoomId);
        GardenChatMessageSendParam gardenChatMessageSendParamSecondByPart = ChatRoomFixture.gardenChatMessageSendParamSecondByPartner(gardenChatRoomId);
        gardenChatService.saveMessage(gardenChatMessageSendParamFirstByPart);
        GardenChatMessageSendResult secondGardenChatMessageByPart = gardenChatService.saveMessage(gardenChatMessageSendParamSecondByPart);

        // When
        GardenChatRoomsFindResults chatMessagesInRooms = gardenChatService.findChatMessagesInRooms(ChatRoomFixture.gardenChatMessageFindParam(myId));
        GardenChatRoomsFindResults.GardenChatRoomsFindResult gardenChatRoomsFindResult = chatMessagesInRooms.gardenChatRoomsFindResults().get(0);

        // Then
        assertThat(gardenChatRoomsFindResult.recentContents()).isEqualTo(secondGardenChatMessageByPart.message());
        assertThat(gardenChatRoomsFindResult.readNotCnt()).isEqualTo(2);

        assertThat(gardenChatRoomsFindResult.createdAt().getDayOfYear()).isEqualTo(secondGardenChatMessageByPart.createdAt().getDayOfYear());
        assertThat(gardenChatRoomsFindResult.createdAt().getMonth()).isEqualTo(secondGardenChatMessageByPart.createdAt().getMonth());
        assertThat(gardenChatRoomsFindResult.createdAt().getDayOfMonth()).isEqualTo(secondGardenChatMessageByPart.createdAt().getDayOfMonth());
        assertThat(gardenChatRoomsFindResult.createdAt().getHour()).isEqualTo(secondGardenChatMessageByPart.createdAt().getHour());
        assertThat(gardenChatRoomsFindResult.createdAt().getMinute()).isEqualTo(secondGardenChatMessageByPart.createdAt().getMinute());
        assertThat(gardenChatRoomsFindResult.createdAt().getSecond()).isEqualTo(secondGardenChatMessageByPart.createdAt().getSecond());

        assertThat(gardenChatRoomsFindResult.chatMessageId()).isEqualTo(secondGardenChatMessageByPart.chatMessageId());
        assertThat(gardenChatRoomsFindResult.chatRoomId()).isEqualTo(gardenChatRoomId);
        assertThat(gardenChatRoomsFindResult.partnerId()).isEqualTo(partnerId);
    }

}