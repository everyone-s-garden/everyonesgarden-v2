package com.garden.back.service;

import com.garden.back.domain.garden.GardenChatMessage;
import com.garden.back.domain.garden.GardenChatRoom;
import com.garden.back.repository.chatentry.SessionId;
import com.garden.back.service.crop.request.CropChatRoomCreateParam;
import com.garden.back.service.garden.dto.request.GardenChatMessageSendParam;
import com.garden.back.service.garden.dto.request.GardenChatRoomCreateParam;
import com.garden.back.service.garden.dto.request.GardenChatRoomDeleteParam;
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

    public static GardenSessionCreateParam gardenSessionCreateParamAboutMe() {
        return new GardenSessionCreateParam(
                SessionId.of("1L"),
                1L,
                1L
        );
    }

    public static GardenSessionCreateParam gardenSessionCreateParamAboutPartner() {
        return new GardenSessionCreateParam(
                SessionId.of("2L"),
                1L,
                2L
        );
    }

    public static GardenChatMessageSendParam gardenChatMessageSendParamFirst() {
        return new GardenChatMessageSendParam(
                SessionId.of("1L"),
                1L,
                1L,
                "안녕하세요. 분양 정보 보고 문의드립니다.");
    }

    public static GardenChatMessageSendParam gardenChatMessageSendParamSecond() {
        return new GardenChatMessageSendParam(
                SessionId.of("1L"),
                1L,
                1L,
                "근처에 화장실이 구비되어 있나요?");
    }

    public static GardenChatRoomDeleteParam gardenChatRoomDeleteParam() {
        return new GardenChatRoomDeleteParam(
                1L,
                1L
        );
    }

    public static GardenChatRoomDeleteParam gardenChatRoomDeleteParamAboutPartner() {
        return new GardenChatRoomDeleteParam(
                1L,
                2L
        );
    }

}
