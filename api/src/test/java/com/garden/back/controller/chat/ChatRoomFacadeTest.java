package com.garden.back.controller.chat;

import com.garden.back.chat.facade.ChatRoomFacade;
import com.garden.back.chat.facade.GardenChatRoomEnterFacadeRequest;
import com.garden.back.chat.facade.GardenChatRoomEnterFacadeResponse;
import com.garden.back.domain.garden.GardenChatRoom;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.garden.service.dto.response.GardenChatRoomInfoResult;
import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.member.service.MemberService;
import com.garden.back.repository.chatmessage.garden.GardenChatMessageRepository;
import com.garden.back.repository.chatroom.garden.GardenChatRoomRepository;
import com.garden.back.service.garden.dto.request.GardenChatRoomEntryParam;
import com.garden.back.service.garden.dto.response.GardenChatRoomEntryResult;
import com.garden.back.service.garden.dto.request.GardenChatRoomCreateParam;
import com.garden.back.service.garden.GardenChatRoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class ChatRoomFacadeTest extends IntegrationTestSupport {

    @Autowired
    private GardenChatRoomService gardenChatRoomService;

    @Autowired
    private GardenReadService gardenReadService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ChatRoomFacade chatRoomFacade;

    @Autowired
    private GardenChatRoomRepository gardenChatRoomRepository;

    @Autowired
    private GardenChatMessageRepository gardenChatMessageRepository;

    @DisplayName("채팅방 입장할 때 보여주는 텃밭 분양 정보 및 상대방 별명을 확인할 수 있다.")
    @Test
    void enterGardenChatRoom() {
        // Given
        Long memberId = 1L;

        GardenChatRoomCreateParam chatRoomCreateParam = ChatRoomFixture.chatRoomCreateParam();
        Long chatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);

        GardenChatRoom gardenChatRoom = gardenChatRoomRepository.findById(chatRoomId).get();
        gardenChatMessageRepository.save(ChatRoomFixture.partnerFirstGardenChatMessage(gardenChatRoom));
        gardenChatMessageRepository.save(ChatRoomFixture.partnerSecondGardenChatMessage(gardenChatRoom));

        GardenChatRoomEnterFacadeRequest request = new GardenChatRoomEnterFacadeRequest(chatRoomId, memberId);

        GardenChatRoomEntryParam gardenChatRoomEntryParam = new GardenChatRoomEntryParam(chatRoomId, memberId);

        // When
        GardenChatRoomEnterFacadeResponse response = chatRoomFacade.enterGardenChatRoom(request);

        String nickname = memberService.findNickname(chatRoomCreateParam.viewerId());
        GardenChatRoomInfoResult gardenChatRoomInfo = gardenReadService.getGardenChatRoomInfo(chatRoomCreateParam.postId());
        GardenChatRoomEntryResult gardenChatRoomEntryResult = gardenChatRoomService.enterGardenChatRoom(gardenChatRoomEntryParam);

        // Then
        assertThat(response)
                .extracting("gardenName", "price", "gardenStatus", "images", "postId")
                .containsExactly(
                        gardenChatRoomInfo.gardenName(),
                        gardenChatRoomInfo.price(),
                        gardenChatRoomInfo.gardenStatus(),
                        gardenChatRoomInfo.imageUrls(),
                        gardenChatRoomInfo.postId()
                );
        assertThat(response.partnerNickname()).isEqualTo(nickname);
        assertThat(response.partnerId()).isEqualTo(gardenChatRoomEntryResult.partnerId());
    }
}