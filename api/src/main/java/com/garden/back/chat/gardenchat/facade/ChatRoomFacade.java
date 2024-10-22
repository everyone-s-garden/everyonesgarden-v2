package com.garden.back.chat.gardenchat.facade;

import com.garden.back.garden.service.GardenChatRoomService;
import com.garden.back.garden.service.GardenChatService;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.garden.service.dto.response.GardenChatRoomsFindResults;
import com.garden.back.garden.service.dto.response.GardenChatRoomEntryResult;
import com.garden.back.garden.service.dto.response.GardenChatRoomInfoResult;
import com.garden.back.member.service.MemberService;
import com.garden.back.member.service.dto.MemberMyPageResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChatRoomFacade {

    private final GardenChatRoomService gardenChatRoomService;
    private final GardenChatService gardenChatService;
    private final GardenReadService gardenReadService;
    private final MemberService memberService;

    public ChatRoomFacade(GardenChatRoomService gardenChatRoomService, GardenChatService gardenChatService, GardenReadService gardenReadService, MemberService memberService) {
        this.gardenChatRoomService = gardenChatRoomService;
        this.gardenChatService = gardenChatService;
        this.gardenReadService = gardenReadService;
        this.memberService = memberService;
    }

    @Transactional
    public GardenChatRoomEnterFacadeResponse enterGardenChatRoom(GardenChatRoomEnterFacadeRequest request) {
        GardenChatRoomEntryResult gardenChatRoomEntryResult = gardenChatRoomService.enterGardenChatRoom(request.to());
        GardenChatRoomInfoResult gardenChatRoomInfo = gardenReadService.getGardenChatRoomInfo(gardenChatRoomEntryResult.postId());
        MemberMyPageResult partnerInfo = memberService.getMyMember(gardenChatRoomEntryResult.partnerId());

        return GardenChatRoomEnterFacadeResponse.to(
            gardenChatRoomEntryResult,
            gardenChatRoomInfo,
            partnerInfo
        );
    }

    @Transactional
    public GardenChatRoomsFindFacadeResponses findChatRoomsInMember(GardenChatRoomsFindFacadeRequest param) {
        GardenChatRoomsFindResults chatMessagesInRooms = gardenChatService.findChatMessagesInRooms(param.toGardenChatRoomsFindParam());

        Map<Long, List<String>> imageUrlsById = new HashMap<>();
        Map<Long, MemberMyPageResult> memberInfoById = new HashMap<>();
        chatMessagesInRooms.gardenChatRoomsFindResults()
            .forEach(
                result -> {
                    imageUrlsById.put(result.chatMessageId(), gardenReadService.getGardenImages(result.postId()));
                    memberInfoById.put(result.chatMessageId(), memberService.getMyMember(result.partnerId()));
                }
            );

        return GardenChatRoomsFindFacadeResponses.to(chatMessagesInRooms, imageUrlsById, memberInfoById);
    }

}
