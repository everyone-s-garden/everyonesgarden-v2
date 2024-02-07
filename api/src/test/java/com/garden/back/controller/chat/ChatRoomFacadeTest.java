package com.garden.back.controller.chat;

import com.garden.back.chat.gardenchat.facade.*;
import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.domain.GardenChatRoom;
import com.garden.back.garden.domain.GardenImage;
import com.garden.back.garden.repository.chatmessage.GardenChatMessageRepository;
import com.garden.back.garden.repository.chatroom.garden.GardenChatRoomRepository;
import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.repository.gardenimage.GardenImageRepository;
import com.garden.back.garden.service.GardenChatRoomService;
import com.garden.back.garden.service.GardenChatService;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.garden.service.dto.request.GardenChatMessageSendParam;
import com.garden.back.garden.service.dto.request.GardenChatRoomCreateParam;
import com.garden.back.garden.service.dto.request.GardenChatRoomEntryParam;
import com.garden.back.garden.service.dto.request.GardenSessionCreateParam;
import com.garden.back.garden.service.dto.response.GardenChatRoomEntryResult;
import com.garden.back.garden.service.dto.response.GardenChatRoomInfoResult;
import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.member.Member;
import com.garden.back.member.repository.MemberRepository;
import com.garden.back.member.service.MemberService;
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
    private GardenChatService gardenChatService;

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

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GardenRepository gardenRepository;

    @Autowired
    private GardenImageRepository gardenImageRepository;

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

    @DisplayName("채팅방 목록을 확인할 때 상대방의 별명과 관련 게시글의 이미지 정보들을 확인할 수 있다.")
    @Test
    void getGardenChatRoomInMember() {
        // Given
        Member me = memberRepository.save(ChatRoomFixture.memberAboutMe());
        Member partner = memberRepository.save(ChatRoomFixture.memberAboutPartner());

        Garden garden = gardenRepository.save(ChatRoomFixture.garden(me.getId()));
        GardenImage firstGardenImage = gardenImageRepository.save(ChatRoomFixture.firstGardenImage(garden));
        GardenImage secondGardenImage = gardenImageRepository.save(ChatRoomFixture.secondGardenImage(garden));

        GardenChatRoomCreateParam chatRoomCreateParam = new GardenChatRoomCreateParam(
                me.getId(),
                partner.getId(),
                garden.getGardenId());
        Long gardenChatRoomId = gardenChatRoomService.createGardenChatRoom(chatRoomCreateParam);

        // 나의 세션 생성
        GardenSessionCreateParam gardenSessionCreateParamAboutMe = ChatRoomFixture.gardenSessionCreateParamAboutMe(gardenChatRoomId, partner.getId());
        gardenChatRoomService.createSessionInfo(gardenSessionCreateParamAboutMe);

        // 내 메세지 전송
        GardenChatMessageSendParam gardenChatMessageSendParamFirstByMe = ChatRoomFixture.gardenChatMessageSendParamFirstByMe(gardenChatRoomId, partner.getId());
        GardenChatMessageSendParam gardenChatMessageSendParamSecondByMe = ChatRoomFixture.gardenChatMessageSendParamSecondByMe(gardenChatRoomId, partner.getId());
        gardenChatService.saveMessage(gardenChatMessageSendParamFirstByMe);
        gardenChatService.saveMessage(gardenChatMessageSendParamSecondByMe);

        GardenChatRoomsFindFacadeRequest request = new GardenChatRoomsFindFacadeRequest(me.getId(), 0);

        // When
        GardenChatRoomsFindFacadeResponses chatRoomsInMember = chatRoomFacade.findChatRoomsInMember(request);

        // Then
        assertThat(chatRoomsInMember.responses().get(0).postInfo().postId()).isEqualTo(garden.getGardenId());
        assertThat(chatRoomsInMember.responses().get(0).postInfo().images()).contains(firstGardenImage.getImageUrl(), secondGardenImage.getImageUrl());
        assertThat(chatRoomsInMember.responses().get(0).partnerInfo().partnerId()).isEqualTo(partner.getId());
        assertThat(chatRoomsInMember.responses().get(0).partnerInfo().nickName()).isEqualTo(partner.getNickname());
        assertThat(chatRoomsInMember.responses().get(0).partnerInfo().memberMannerGrade()).isEqualTo(partner.getMemberMannerGrade().toString());

    }
}