package com.garden.back.docs.chat;

import com.garden.back.chat.controller.dto.request.CropChatRoomCreateRequest;
import com.garden.back.chat.controller.dto.request.GardenChatRoomCreateRequest;
import com.garden.back.chat.controller.dto.request.GardenSessionCreateRequest;
import com.garden.back.chat.facade.GardenChatRoomEnterFacadeResponse;
import com.garden.back.garden.domain.vo.GardenStatus;

import java.util.List;

public class ChatRoomFixture {

    public static GardenChatRoomCreateRequest chatRoomCreateRequest() {
        return new GardenChatRoomCreateRequest(
                1L,
                1L
        );
    }

    public static CropChatRoomCreateRequest chatRoomCropCreateRequest() {
        return new CropChatRoomCreateRequest(
                1L,
                1L
        );
    }

    public static GardenChatRoomEnterFacadeResponse gardenChatRoomEnterFacadeResponse() {
        return new GardenChatRoomEnterFacadeResponse(
                2L,
                "임이라다",
                1L,
                GardenStatus.ACTIVE.name(),
                "이라네 주말농장",
                "100000",
                List.of("https://kr.object.ncloudstorage.com/every-garden/images/garden/background.jpg")
        );
    }

    public static GardenSessionCreateRequest gardenSessionCreateRequest() {
        return new GardenSessionCreateRequest(
                "234",
                1L
        );
    }

}
