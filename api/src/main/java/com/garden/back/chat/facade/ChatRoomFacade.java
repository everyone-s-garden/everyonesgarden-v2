package com.garden.back.chat.facade;

import com.garden.back.garden.service.GardenReadService;
import com.garden.back.garden.service.dto.response.GardenChatRoomInfoResult;
import com.garden.back.member.service.MemberService;
import com.garden.back.service.garden.dto.response.GardenChatRoomEntryResult;
import com.garden.back.service.garden.GardenChatRoomService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ChatRoomFacade {

    private final GardenChatRoomService gardenChatRoomService;
    private final GardenReadService gardenReadService;
    private final MemberService memberService;

    public ChatRoomFacade(GardenChatRoomService gardenChatRoomService, GardenReadService gardenReadService, MemberService memberService) {
        this.gardenChatRoomService = gardenChatRoomService;
        this.gardenReadService = gardenReadService;
        this.memberService = memberService;
    }

    @Transactional
    public GardenChatRoomEnterFacadeResponse enterGardenChatRoom(GardenChatRoomEnterFacadeRequest request) {
        GardenChatRoomEntryResult gardenChatRoomEntryResult = gardenChatRoomService.enterGardenChatRoom(request.to());
        GardenChatRoomInfoResult gardenChatRoomInfo = gardenReadService.getGardenChatRoomInfo(gardenChatRoomEntryResult.postId());
        String nickname = memberService.findNickname(gardenChatRoomEntryResult.postId());

        return GardenChatRoomEnterFacadeResponse.to(
                gardenChatRoomEntryResult,
                gardenChatRoomInfo,
                nickname
        );
    }

}
