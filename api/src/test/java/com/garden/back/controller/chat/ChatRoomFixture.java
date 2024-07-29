package com.garden.back.controller.chat;

import com.garden.back.crop.domain.CropChatMessage;
import com.garden.back.crop.domain.CropChatRoom;
import com.garden.back.crop.service.request.CropChatRoomCreateParam;
import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.domain.GardenChatMessage;
import com.garden.back.garden.domain.GardenChatRoom;
import com.garden.back.garden.domain.GardenImage;
import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.domain.vo.GardenType;
import com.garden.back.garden.repository.chatentry.SessionId;
import com.garden.back.garden.service.dto.request.GardenChatMessageSendParam;
import com.garden.back.garden.service.dto.request.GardenChatRoomCreateParam;
import com.garden.back.garden.service.dto.request.GardenSessionCreateParam;
import com.garden.back.global.GeometryUtil;
import com.garden.back.global.MessageType;
import com.garden.back.member.Member;
import com.garden.back.member.Role;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;


public class ChatRoomFixture {

    private static final String FIRST_GARDEN_IMAGE_URL = "https://kr.object.ncloudstorage.com/every-garden/images/garden/background.jpg";
    private static final String SECOND_GARDEN_IMAGE_URL = "https://kr.object.ncloudstorage.com/every-garden/images/garden/view.jpg";
    private static final double LATITUDE = 37.4449168;
    private static final double LONGITUDE = 127.1388684;
    private static final LocalDate RECRUIT_START_DATE = LocalDate.of(2023, 11, 1);
    private static final LocalDate RECRUIT_END_DATE = LocalDate.of(2023, 12, 7);
    private static final LocalDate USE_START_DATE = LocalDate.of(2024, 12, 7);
    private static final LocalDate USE_END_DATE = LocalDate.of(2024, 12, 15);

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

    public static Member memberAboutMe() {
        return Member.create(
            "j3333@naver.com",
            "불가사리",
            Role.USER
        );
    }

    public static Member memberAboutPartner() {
        return Member.create(
            "j4444@naver.com",
            "진겸",
            Role.USER
        );
    }

    public static Garden garden(Long writerId) {
        return Garden.of(
            "인천광역시 서구 만수동 200",
            LATITUDE,
            LONGITUDE,
            GeometryUtil.createPoint(LATITUDE, LONGITUDE),
            "모두의 텃밭",
            GardenType.PRIVATE,
            GardenStatus.ACTIVE,
            "100",
            "000-000-000",
            "200.23",
            "화장실이 깨끗하고 농기구를 빌려줍니다.",
            RECRUIT_START_DATE,
            RECRUIT_END_DATE,
            "화장실 등",
            writerId,
            false,
            0
        );
    }

    public static GardenImage firstGardenImage(Garden garden) {
        return GardenImage.of(FIRST_GARDEN_IMAGE_URL, garden);
    }

    public static GardenImage secondGardenImage(Garden garden) {
        return GardenImage.of(SECOND_GARDEN_IMAGE_URL, garden);
    }

    public static GardenSessionCreateParam gardenSessionCreateParamAboutMe(Long gardenChatRoomId, Long memberId) {
        return new GardenSessionCreateParam(
            SessionId.of("1L"),
            gardenChatRoomId,
            memberId
        );
    }

    public static GardenChatMessageSendParam gardenChatMessageSendParamFirstByMe(Long gardenChatRoomId, Long memberId) {
        return new GardenChatMessageSendParam(
            SessionId.of("1L"),
            memberId,
            gardenChatRoomId,
            "안녕하세요. 분양 정보 보고 문의드립니다.");
    }

    public static GardenChatMessageSendParam gardenChatMessageSendParamSecondByMe(Long gardenChatRoomId, Long memberId) {
        return new GardenChatMessageSendParam(
            SessionId.of("1L"),
            memberId,
            gardenChatRoomId,
            "근처에 화장실이 구비되어 있나요?");
    }

}
