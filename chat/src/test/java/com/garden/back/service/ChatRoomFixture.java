package com.graden.back.service;

import com.garden.back.domain.ChatType;
import com.garden.back.domain.MessageType;
import com.garden.back.domain.garden.GardenChatMessage;
import com.garden.back.domain.garden.GardenChatRoom;
import com.garden.back.service.dto.request.GardenChatRoomCreateParam;

public class ChatRoomFixture {

    public static GardenChatRoomCreateParam chatRoomCreateParam() {
        return new GardenChatRoomCreateParam(
                1L,
                2L,
                1L,
                ChatType.GARDEN
        );
    }

    public static GardenChatMessage partnerFirstGardenChatMessage(GardenChatRoom gardenChatRoom) {
        return GardenChatMessage.of(
                gardenChatRoom,
                1L,
                "안녕하세요",
                false,
                MessageType.TALK
        );
    }

    public static GardenChatMessage partnerSecondGardenChatMessage(GardenChatRoom gardenChatRoom) {
        return GardenChatMessage.of(
                gardenChatRoom,
                1L,
                "분양가는 한 달에 100000원입니다.",
                false,
                MessageType.TALK
        );
    }

}
