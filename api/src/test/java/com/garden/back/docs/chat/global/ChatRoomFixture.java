package com.garden.back.docs.chat.global;

import com.garden.back.chat.cropchat.controller.dto.request.CropChatRoomCreateRequest;
import com.garden.back.chat.gardenchat.controller.dto.request.GardenChatReportRequest;
import com.garden.back.chat.gardenchat.controller.dto.request.GardenChatRoomCreateRequest;
import com.garden.back.chat.gardenchat.controller.dto.request.GardenSessionCreateRequest;
import com.garden.back.chat.gardenchat.facade.GardenChatRoomEnterFacadeResponse;
import com.garden.back.chat.gardenchat.facade.GardenChatRoomsFindFacadeResponses;
import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.service.dto.response.GardenChatMessagesGetResults;

import java.time.LocalDateTime;
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

    public static GardenChatMessagesGetResults gardenChatMessageGetResponses() {
        return new GardenChatMessagesGetResults(
                List.of(
                        new GardenChatMessagesGetResults.GardenChatMessagesGetResult(1L, 101L, "안녕하세요", LocalDateTime.of(2023, 1, 1, 12, 20), false, true),
                        new GardenChatMessagesGetResults.GardenChatMessagesGetResult(2L, 102L, "텃밭 분양글 보고 연락드렸어요", LocalDateTime.of(2023, 1, 1, 12, 21), true, false)
                ),
                false
        );
    }

    public static GardenChatRoomsFindFacadeResponses gardenChatRoomsFindFacadeResponses() {
        return new GardenChatRoomsFindFacadeResponses(
                List.of(
                        new GardenChatRoomsFindFacadeResponses.GardenChatRoomsFindFacadeResponse(
                                1L,
                                LocalDateTime.of(2024, 1, 21, 1, 21, 11),
                                2,
                                1L,
                                "한 달에 드는 비용은 3만원입니다.",
                                new GardenChatRoomsFindFacadeResponses.PartnerInfo(
                                        1L,
                                        "불가사리",
                                        "https://kr.object.ncloudstorage.com/every-garden/images/garden/background.jpg"
                                ),
                                new GardenChatRoomsFindFacadeResponses.PostInfo(
                                        1L,
                                        List.of("https://kr.object.ncloudstorage.com/every-garden/images/garden/background.jpg")
                                )

                        )

                ), false
        );
    }

    public static GardenChatReportRequest gardenChatReportRequest() {
        return new GardenChatReportRequest(
                "계속 욕설을 하셔서 신고합니다.",
                "DISPUTE"
        );
    }

}
