package com.graden.back.service;

import com.graden.back.domain.ChatRoomInfo;
import com.graden.back.repository.chatroom.ChatRoomRepository;
import com.graden.back.repository.chatroominfo.ChatRoomInfoRepository;
import com.graden.back.service.dto.request.ChatRoomCreateParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = NONE)
class ChatRoomServiceTest {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRoomInfoRepository chatRoomInfoRepository;

    @DisplayName("해당 게시글에 대한 채팅방을 생성할 수 있다.")
    @Test
    void createChatRoom() {
        // Given
        ChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();

        // When
        Long chatRoomId = chatRoomService.createChatRoom(chatRoomCreateParam);
        List<ChatRoomInfo> chatRoomInfos = chatRoomInfoRepository.findAll();

        // Then
        chatRoomInfos.stream()
                .filter(ChatRoomInfo::isWriter)
                .forEach(
                        chatRoomInfo -> {
                            assertThat(chatRoomInfo.getChatType()).isEqualTo(chatRoomCreateParam.chatType());
                            assertThat(chatRoomInfo.getPostId()).isEqualTo(chatRoomCreateParam.postId());
                            assertThat(chatRoomInfo.getMemberId()).isEqualTo(chatRoomCreateParam.writerId());
                            assertThat(chatRoomInfo.getChatRoom().getChatRoomId()).isEqualTo(chatRoomId);
                        }
                );

        chatRoomInfos.stream()
                .filter(chatRoomInfo -> !chatRoomInfo.isWriter())
                .forEach(
                        chatRoomInfo -> {
                            assertThat(chatRoomInfo.getChatType()).isEqualTo(chatRoomCreateParam.chatType());
                            assertThat(chatRoomInfo.getPostId()).isEqualTo(chatRoomCreateParam.postId());
                            assertThat(chatRoomInfo.getMemberId()).isEqualTo(chatRoomCreateParam.viewerId());
                            assertThat(chatRoomInfo.getChatRoom().getChatRoomId()).isEqualTo(chatRoomId);
                        }
                );
    }

}