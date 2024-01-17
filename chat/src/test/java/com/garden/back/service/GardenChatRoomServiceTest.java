package com.garden.back.service;

import com.garden.back.garden.domain.GardenChatMessage;
import com.garden.back.garden.domain.GardenChatRoom;
import com.garden.back.garden.domain.GardenChatRoomInfo;
import com.garden.back.garden.service.GardenChatRoomService;
import com.garden.back.global.exception.EntityNotFoundException;
import com.garden.back.garden.repository.chatentry.garden.GardenChatRoomEntryRepository;
import com.garden.back.garden.repository.chatmessage.garden.GardenChatMessageRepository;
import com.garden.back.garden.repository.chatroom.garden.GardenChatRoomRepository;
import com.garden.back.garden.repository.chatroominfo.garden.GardenChatRoomInfoRepository;
import com.garden.back.garden.service.dto.request.GardenChatRoomCreateParam;
import com.garden.back.garden.service.dto.request.GardenChatRoomDeleteParam;
import com.garden.back.garden.service.dto.request.GardenChatRoomEntryParam;
import com.garden.back.garden.service.dto.request.GardenSessionCreateParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
        assertThat(gardenChatRoomEntryRepository.isMemberInRoom(gardenSessionCreateParam.toChatRoomEntry())).isEqualTo(true);
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
        assertThat(gardenChatRoomInfoRepository.findByRoomId(gardenChatRoomDeleteParam.chatRoomId()).size()).isEqualTo(0);
    }

}