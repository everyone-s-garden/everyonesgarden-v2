package com.garden.back.facade;

import com.garden.back.domain.garden.GardenChatRoom;
import com.garden.back.facade.dto.GardenChatRoomEnterFacadeRequest;
import com.garden.back.facade.dto.GardenChatRoomEnterFacadeResponse;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.garden.service.dto.response.GardenChatRoomInfoResult;
import com.garden.back.member.MemberService;
import com.garden.back.repository.chatmessage.garden.GardenChatMessageRepository;
import com.garden.back.repository.chatroom.garden.GardenChatRoomRepository;
import com.garden.back.service.ChatRoomFixture;
import com.garden.back.service.IntegrationTestSupport;
import com.garden.back.service.dto.request.ChatRoomEntryParam;
import com.garden.back.service.dto.request.ChatRoomEntryResult;
import com.garden.back.service.dto.request.GardenChatRoomCreateParam;
import com.garden.back.service.garden.GardenChatRoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

        ChatRoomEntryParam chatRoomEntryParam = new ChatRoomEntryParam(chatRoomId, memberId);

        // When
        GardenChatRoomEnterFacadeResponse response = chatRoomFacade.enterGardenChatRoom(request);

        String nickname = memberService.findNickname(chatRoomCreateParam.viewerId());
        GardenChatRoomInfoResult gardenChatRoomInfo = gardenReadService.getGardenChatRoomInfo(chatRoomCreateParam.postId());
        ChatRoomEntryResult chatRoomEntryResult = gardenChatRoomService.enterGardenChatRoom(chatRoomEntryParam);

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
        assertThat(response.partnerId()).isEqualTo(chatRoomEntryResult.partnerId());
    }
}