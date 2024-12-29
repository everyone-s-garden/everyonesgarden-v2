package com.garden.back.service;

import com.garden.back.garden.domain.GardenChatMessage;
import com.garden.back.garden.domain.GardenChatRoom;
import com.garden.back.garden.domain.GardenChatRoomInfo;
import com.garden.back.garden.repository.chatentry.garden.GardenChatRoomEntryRepository;
import com.garden.back.garden.repository.chatmessage.GardenChatMessageRepository;
import com.garden.back.garden.repository.chatroom.garden.GardenChatRoomRepository;
import com.garden.back.garden.repository.chatroominfo.GardenChatRoomInfoRepository;
import com.garden.back.garden.service.GardenChatRoomService;
import com.garden.back.garden.service.dto.request.*;
import com.garden.back.global.exception.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Transactional
class GardenChatRoomServiceTest extends IntegrationTestSupport{

    @Autowired
    private GardenChatRoomService gardenChatRoomService;

    @Autowired
    private GardenChatRoomRepository gardenChatRoomRepository;

    @Autowired
    private GardenChatRoomInfoRepository gardenChatRoomInfoRepository;

    @Autowired
    private GardenChatMessageRepository gardenChatMessageRepository;

    @Autowired
    private GardenChatRoomEntryRepository gardenChatRoomEntryRepository;

    @DisplayName("해당 게시글에 대한 채팅방을 생성할 수 있다.")
    @Test
    void createGardenChatRoom() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();

        // When
        Long chatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);
        List<GardenChatRoomInfo> chatRoomInfos = gardenChatRoomInfoRepository.findAll();

        // Then
        chatRoomInfos.stream()
                .filter(GardenChatRoomInfo::isWriter)
                .forEach(
                        chatRoomInfo -> {
                            assertThat(chatRoomInfo.getPostId()).isEqualTo(chatRoomCreateParam.postId());
                            assertThat(chatRoomInfo.getMemberId()).isEqualTo(chatRoomCreateParam.writerId());
                            assertThat(chatRoomInfo.getChatRoom().getChatRoomId()).isEqualTo(chatRoomId);
                        }
                );

        chatRoomInfos.stream()
                .filter(chatRoomInfo -> !chatRoomInfo.isWriter())
                .forEach(
                        chatRoomInfo -> {
                            assertThat(chatRoomInfo.getPostId()).isEqualTo(chatRoomCreateParam.postId());
                            assertThat(chatRoomInfo.getMemberId()).isEqualTo(chatRoomCreateParam.viewerId());
                            assertThat(chatRoomInfo.getChatRoom().getChatRoomId()).isEqualTo(chatRoomId);
                        }
                );
    }

    @DisplayName("해당 게시글에 대한 채팅방을 중복 생성하면 예외를 던진다.")
    @Test
    void createChatRoom_existedChatRoom_throwException() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();

        // When
        gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);

        // Then
        assertThatThrownBy(() -> gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("채팅방에 입장하면 읽지 않은 메세지들이 모두 읽음처리 된다.")
    @Test
    void enterChatRoom_allMessages_read() {
        // Given
        Long memberId = 1L;

        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long chatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);
        GardenChatRoomEntryParam gardenChatRoomEntryParam = new GardenChatRoomEntryParam(chatRoomId, memberId);

        GardenChatRoom gardenChatRoom = gardenChatRoomRepository.findById(chatRoomId).get();
        gardenChatMessageRepository.save(ChatRoomFixture.partnerFirstGardenChatMessage(gardenChatRoom));
        gardenChatMessageRepository.save(ChatRoomFixture.partnerSecondGardenChatMessage(gardenChatRoom));

        // When
        gardenChatRoomService.enterGardenChatRoom(gardenChatRoomEntryParam);
        List<GardenChatMessage> allPartnerMessages = gardenChatMessageRepository.findAll();

        // Then
        allPartnerMessages.forEach(
                gardenChatMessage -> assertThat(gardenChatMessage.isReadOrNot()).isTrue()
        );
    }

    @DisplayName("세션 정보를 만들 수 있다.")
    @Test
    void createSessionInfo() {
        // Given
        GardenSessionCreateParam gardenSessionCreateParam = ChatRoomFixture.gardenSessionCreateParamAboutMe(1L);

        // When
        gardenChatRoomService.createSessionInfo(gardenSessionCreateParam);

        // Then
        assertThat(gardenChatRoomEntryRepository.isMemberInRoom(gardenSessionCreateParam.toChatRoomEntry())).isTrue();
    }

    @DisplayName("채팅방을 영구적으로 나가는 경우 한쪽만 나가는 경우에는 상대방 채팅방은 살아있다.")
    @Test
    void deleteChatRoom_onePerson() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long gardenChatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);

        GardenChatRoomDeleteParam gardenChatRoomDeleteParam = ChatRoomFixture.gardenChatRoomDeleteParam(gardenChatRoomId);

        // When
        gardenChatRoomService.deleteChatRoom(gardenChatRoomDeleteParam);

        // Then
        assertThat(gardenChatRoomRepository.findById(gardenChatRoomDeleteParam.chatRoomId())).isNotEmpty();
        assertThat(gardenChatRoomInfoRepository.findByRoomId(gardenChatRoomDeleteParam.chatRoomId()).stream()
                .filter(GardenChatRoomInfo::isDeleted).toList().size()).isEqualTo(1);
    }

    @DisplayName("채팅방을 영구적으로 나가는 경우 모두 나가는 경우에는 채팅방은 삭제된다.")
    @Test
    void deleteChatRoom_twoPerson() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long gardenChatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);

        GardenChatRoomDeleteParam gardenChatRoomDeleteParam = ChatRoomFixture.gardenChatRoomDeleteParam(gardenChatRoomId);
        GardenChatRoomDeleteParam gardenChatRoomDeleteParamAboutPartner = ChatRoomFixture.gardenChatRoomDeleteParamAboutPartner(gardenChatRoomId);

        // When
        gardenChatRoomService.deleteChatRoom(gardenChatRoomDeleteParam);
        gardenChatRoomService.deleteChatRoom(gardenChatRoomDeleteParamAboutPartner);

        // Then
        assertThatThrownBy(()-> gardenChatRoomRepository.getById(gardenChatRoomDeleteParam.chatRoomId())).isInstanceOf(EntityNotFoundException.class);
        assertThat(gardenChatRoomInfoRepository.findByRoomId(gardenChatRoomDeleteParam.chatRoomId()).size()).isZero();
    }

    @DisplayName("채팅방에 대해 신고를 하면 해당 방은 신고된 상태로 전환된다.")
    @Test
    void reportChatRoom() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long chatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);
        GardenChatReportParam gardenChatReportParam = ChatRoomFixture.gardenChatReportParam(chatRoomId);

        // When
        gardenChatRoomService.reportChatRoom(gardenChatReportParam);
        GardenChatRoom gardenChatRoom = gardenChatRoomRepository.getById(chatRoomId);

        // Then
        assertThat(gardenChatRoom.isReported()).isTrue();
    }

    @DisplayName("채팅방에 대해 신고기 중복되면 예외를 던진다.")
    @Test
    void reportChatRoom_existed_throwException() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long chatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);
        GardenChatReportParam gardenChatReportParam = ChatRoomFixture.gardenChatReportParam(chatRoomId);

        // When
        gardenChatRoomService.reportChatRoom(gardenChatReportParam);

        // Then
        assertThatThrownBy(()-> gardenChatRoomService.reportChatRoom(gardenChatReportParam))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("텃밭 분양 게시글의 아이디와 이를 보고 있는 해당 사용자의 아이디가 주어질 때 " +
        "해당 게시글의 채팅방이 없는 경우 채팅방 아이디로 -1를 반환한다.")
    @Test
    void getChatRoomId_notExisted() {
        // Given
        GardenChatRoomInfoGetParam gardenChatRoomInfoGetParam = ChatRoomFixture.gardenChatRoomInfoGetParam();

        // When
        Long roomId = gardenChatRoomService.getRoomId(gardenChatRoomInfoGetParam);

        // Then
        assertThat(roomId).isEqualTo(-1L);
    }

    @DisplayName("텃밭 분양 게시글의 아이디와 이를 보고 있는 해당 사용자의 아이디가 주어질 때 " +
        "해당 게시글의 채팅방이 있는 경우 채팅방 아이디를 반환한다.")
    @Test
    void getChatRoomId_existed() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long chatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);

        GardenChatRoomInfoGetParam gardenChatRoomInfoGetParam = new GardenChatRoomInfoGetParam(
            chatRoomCreateParam.writerId(),
            chatRoomCreateParam.postId());

        // When
        Long roomId = gardenChatRoomService.getRoomId(gardenChatRoomInfoGetParam);

        // Then
        assertThat(roomId).isEqualTo(chatRoomId);
    }

    @DisplayName("상대방이 나간 상태라면 나갔다고 응답한다.")
    @Test
    void isExitedPartner_exitPartner() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long gardenChatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);
        Long partnerId = chatRoomCreateParam.viewerId();

        //when
        gardenChatRoomService.deleteChatRoom(new GardenChatRoomDeleteParam(gardenChatRoomId, partnerId));
        boolean exitedPartner = gardenChatRoomService.isExitedPartner(gardenChatRoomId);

        // Then
        assertThat(exitedPartner).isTrue();
    }

    @DisplayName("상대방이 나가지 않았다면 나가지 않았다고 응답한다.")
    @Test
    void isExitedPartner_notExitPartner() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long gardenChatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);
        Long partnerId = chatRoomCreateParam.viewerId();

        //when
        boolean exitedPartner = gardenChatRoomService.isExitedPartner(gardenChatRoomId);

        // Then
        assertThat(exitedPartner).isFalse();
    }

}
