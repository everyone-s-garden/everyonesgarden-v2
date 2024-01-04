package com.garden.back.service;

import com.garden.back.domain.garden.GardenChatMessage;
import com.garden.back.domain.garden.GardenChatRoom;
import com.garden.back.domain.garden.GardenChatRoomInfo;
import com.garden.back.repository.chatentry.ChatRoomEntryRepository;
import com.garden.back.repository.chatmessage.garden.GardenChatMessageRepository;
import com.garden.back.repository.chatroom.garden.GardenChatRoomRepository;
import com.garden.back.repository.chatroominfo.garden.GardenChatRoomInfoRepository;
import com.garden.back.service.dto.request.ChatRoomEntryParam;
import com.garden.back.service.dto.request.GardenChatRoomCreateParam;
import com.garden.back.service.garden.GardenChatRoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Transactional
class GardenChatRoomServiceTest extends IntegrationTestSupport {

    @Autowired
    private GardenChatRoomService gardenChatRoomService;

    @Autowired
    private GardenChatRoomRepository gardenChatRoomRepository;

    @Autowired
    private GardenChatRoomInfoRepository chatRoomInfoRepository;

    @Autowired
    private GardenChatMessageRepository gardenChatMessageRepository;

    @Autowired
    private ChatRoomEntryRepository chatRoomEntryRepository;

    @DisplayName("해당 게시글에 대한 채팅방을 생성할 수 있다.")
    @Test
    void createGardenChatRoom() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();

        // When
        Long chatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);
        List<GardenChatRoomInfo> chatRoomInfos = chatRoomInfoRepository.findAll();

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
        ChatRoomEntryParam chatRoomEntryParam = new ChatRoomEntryParam(chatRoomId, memberId);

        GardenChatRoom gardenChatRoom = gardenChatRoomRepository.findById(chatRoomId).get();
        gardenChatMessageRepository.save(ChatRoomFixture.partnerFirstGardenChatMessage(gardenChatRoom));
        gardenChatMessageRepository.save(ChatRoomFixture.partnerSecondGardenChatMessage(gardenChatRoom));

        // When
        gardenChatRoomService.enterGardenChatRoom(chatRoomEntryParam);
        List<GardenChatMessage> allPartnerMessages = gardenChatMessageRepository.findAll();

        // Then
        allPartnerMessages.forEach(
                gardenChatMessage -> assertThat(gardenChatMessage.isReadOrNot()).isTrue()
        );
    }

}