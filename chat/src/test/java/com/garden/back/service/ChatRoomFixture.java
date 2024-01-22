package com.garden.back.service;

import com.garden.back.crop.service.request.CropChatRoomCreateParam;
import com.garden.back.garden.domain.GardenChatMessage;
import com.garden.back.garden.domain.GardenChatRoom;
import com.garden.back.garden.repository.chatentry.SessionId;
import com.garden.back.garden.service.dto.request.*;

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

    public static GardenSessionCreateParam gardenSessionCreateParamAboutMe(Long gardenChatRoomId) {
        return new GardenSessionCreateParam(
                SessionId.of("1L"),
                gardenChatRoomId,
                1L
        );
    }

    public static GardenSessionCreateParam gardenSessionCreateParamAboutPartner(Long gardenChatRoomId) {
        return new GardenSessionCreateParam(
                SessionId.of("2L"),
                gardenChatRoomId,
                2L
        );
    }

    public static GardenChatMessageSendParam gardenChatMessageSendParamFirstByMe(Long gardenChatRoomId) {
        return new GardenChatMessageSendParam(
                SessionId.of("1L"),
                1L,
                gardenChatRoomId,
                "안녕하세요. 분양 정보 보고 문의드립니다.");
    }

    public static GardenChatMessageSendParam gardenChatMessageSendParamSecondByMe(Long gardenChatRoomId) {
        return new GardenChatMessageSendParam(
                SessionId.of("1L"),
                1L,
                gardenChatRoomId,
                "근처에 화장실이 구비되어 있나요?");
    }

    public static GardenChatMessageSendParam gardenChatMessageSendParamFirstByPartner(Long gardenChatRoomId) {
        return new GardenChatMessageSendParam(
                SessionId.of("2L"),
                2L,
                gardenChatRoomId,
                "반갑습니다.");
    }

    public static GardenChatMessageSendParam gardenChatMessageSendParamSecondByPartner(Long gardenChatRoomId) {
        return new GardenChatMessageSendParam(
                SessionId.of("2L"),
                2L,
                gardenChatRoomId,
                "구비되어 있어요");
    }

    public static GardenChatRoomDeleteParam gardenChatRoomDeleteParam(Long gardenChatRoomId) {
        return new GardenChatRoomDeleteParam(
                gardenChatRoomId,
                1L
        );
    }

    public static GardenChatRoomDeleteParam gardenChatRoomDeleteParamAboutPartner(Long gardenChatRoomId) {
        return new GardenChatRoomDeleteParam(
                gardenChatRoomId,
                2L
        );
    }

    public static GardenChatMessagesGetParam gardenChatMessagesGetParam(Long gardenChatRoomId) {
        return new GardenChatMessagesGetParam(
                1L,
                gardenChatRoomId,
                0
        );
    }

    public static GardenChatMessageFindParam gardenChatMessageFindParam(Long memberId) {
        return new GardenChatMessageFindParam(
                memberId,
                0
        );
    }
}
