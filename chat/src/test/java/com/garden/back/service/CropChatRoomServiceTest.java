package com.garden.back.service;

import com.garden.back.crop.domain.CropChatRoomInfo;
import com.garden.back.crop.repository.chatroom.CropChatRoomRepository;
import com.garden.back.crop.repository.chatroomInfo.CropChatRoomInfoRepository;
import com.garden.back.crop.service.CropChatRoomService;
import com.garden.back.crop.service.request.CropChatRoomCreateParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Transactional
class CropChatRoomServiceTest extends IntegrationTestSupport{

    @Autowired
    private CropChatRoomService cropChatRoomService;

    @Autowired
    private CropChatRoomRepository cropChatRoomRepository;

    @Autowired
    private CropChatRoomInfoRepository cropChatRoomInfoRepository;

    @DisplayName("해당 게시글에 대한 채팅방을 생성할 수 있다.")
    @Test
    void createGardenChatRoom() {
        // Given
        CropChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.cropChatRoomCreateParam();

        // When
        Long chatRoomId = cropChatRoomService.createCropChatRoom(chatRoomCreateParam);
        List<CropChatRoomInfo> chatRoomInfos = cropChatRoomInfoRepository.findAll();

        // Then
        chatRoomInfos.stream()
                .filter(CropChatRoomInfo::isWriter)
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
        CropChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.cropChatRoomCreateParam();

        // When
        cropChatRoomService.createCropChatRoom(chatRoomCreateParam);

        // Then
        assertThatThrownBy(() -> cropChatRoomService.createCropChatRoom(chatRoomCreateParam))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
