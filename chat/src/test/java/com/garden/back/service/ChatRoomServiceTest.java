package com.graden.back.service;

import com.garden.back.domain.garden.GardenChatRoomInfo;
import com.garden.back.repository.chatroom.garden.GardenChatRoomRepository;
import com.garden.back.repository.chatroominfo.garden.GardenChatRoomInfoRepository;
import com.garden.back.service.ChatRoomService;
import com.garden.back.service.dto.request.GardenChatRoomCreateParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ChatRoomServiceTest extends IntegrationTestSupport{

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private GardenChatRoomRepository chatRoomRepository;

    @Autowired
    private GardenChatRoomInfoRepository chatRoomInfoRepository;

    @DisplayName("해당 게시글에 대한 채팅방을 생성할 수 있다.")
    @Test
    void createGardenChatRoom() {
        // Given
        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();

        // When
        Long chatRoomId = chatRoomService.createGardenChatRoom(chatRoomCreateParam);
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
        chatRoomService.createGardenChatRoom(chatRoomCreateParam);

        // Then
        assertThatThrownBy(()->chatRoomService.createGardenChatRoom(chatRoomCreateParam))
                .isInstanceOf(IllegalArgumentException.class);
    }

}