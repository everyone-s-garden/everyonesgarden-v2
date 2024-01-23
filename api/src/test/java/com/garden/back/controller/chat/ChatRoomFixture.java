package com.garden.back.controller.chat;

import com.garden.back.crop.domain.CropChatMessage;
import com.garden.back.crop.domain.CropChatRoom;
import com.garden.back.crop.service.request.CropChatRoomCreateParam;
import com.garden.back.garden.domain.GardenChatMessage;
import com.garden.back.garden.domain.GardenChatRoom;
import com.garden.back.garden.service.dto.request.GardenChatRoomCreateParam;
import com.garden.back.global.MessageType;


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

    public static CropChatMessage partnerFirstCropChatMessage(CropChatRoom cropChatRoom) {
        return CropChatMessage.of(
                cropChatRoom,
                1L,
                "안녕하세요",
                false,
                MessageType.TALK
        );
    }

    public static CropChatMessage partnerSecondCropChatMessage(CropChatRoom cropChatRoom) {
        return CropChatMessage.of(
                cropChatRoom,
                1L,
                "분양가는 한 달에 100000원입니다.",
                false,
                MessageType.TALK
        );
    }

}
