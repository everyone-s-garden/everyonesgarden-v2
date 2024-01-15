package com.garden.back.service;

import com.garden.back.domain.garden.GardenChatMessage;
import com.garden.back.domain.garden.GardenChatRoom;
import com.garden.back.repository.chatentry.SessionId;
import com.garden.back.service.crop.request.CropChatRoomCreateParam;
import com.garden.back.service.garden.dto.request.GardenChatRoomCreateParam;
import com.garden.back.service.garden.dto.request.GardenSessionCreateParam;

public class ChatRoomFixture {

    public static GardenChatRoomCreateParam chatRoomCreateParam() {
        return new GardenChatRoomCreateParam(
                1L,
                2L,
                1L
        );
    }

    public static GardenChatMessage partnerFirstGardenChatMessage(GardenChatRoom gardenChatRoom) {
        return GardenChatMessage.of(
                gardenChatRoom,
                2L,
                "안녕하세요",
                false
        );
    }

    public static GardenChatMessage partnerSecondGardenChatMessage(GardenChatRoom gardenChatRoom) {
        return GardenChatMessage.of(
                gardenChatRoom,
                2L,
                "분양가는 한 달에 100000원입니다.",
                false
        );
    }

    public static CropChatRoomCreateParam cropChatRoomCreateParam() {
        return new CropChatRoomCreateParam(
                1L,
                2L,
                1L
        );
    }

    public static GardenSessionCreateParam gardenSessionCreateParam() {
        return new GardenSessionCreateParam(
                SessionId.of("1L"),
                1L,
                1L
        );
    }

}
