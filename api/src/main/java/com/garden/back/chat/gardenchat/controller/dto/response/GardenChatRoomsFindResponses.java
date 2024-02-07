package com.garden.back.chat.gardenchat.controller.dto.response;

import com.garden.back.chat.gardenchat.facade.GardenChatRoomsFindFacadeResponses;

import java.time.LocalDateTime;
import java.util.List;

public record GardenChatRoomsFindResponses(
    List<GardenChatRoomsFindResponse> responses,
    boolean hasNext
) {
    public static GardenChatRoomsFindResponses to(GardenChatRoomsFindFacadeResponses responses) {
        return new GardenChatRoomsFindResponses(
            responses.responses().stream()
                .map(GardenChatRoomsFindResponse::to)
                .toList(),
            responses.hasNext()
        );
    }

    public record GardenChatRoomsFindResponse(
        Long chatMessageId,
        LocalDateTime createdAt,
        int readNotCnt,
        Long chatRoomId,
        String recentContents,
        PartnerInfo partnerInfo,
        PostInfo postInfo
    ) {
        public static GardenChatRoomsFindResponse to(GardenChatRoomsFindFacadeResponses.GardenChatRoomsFindFacadeResponse response) {
            return new GardenChatRoomsFindResponse(
                response.chatMessageId(),
                response.createdAt(),
                response.readNotCnt(),
                response.chatRoomId(),
                response.recentContents(),
                new PartnerInfo(
                    response.partnerInfo().partnerId(),
                    response.partnerInfo().nickName(),
                    response.partnerInfo().imageUrl(),
                    response.partnerInfo().memberMannerGrade()),
                new PostInfo(
                    response.postInfo().postId(),
                    response.postInfo().images())
            );
        }

    }

    public record PartnerInfo(
        Long partnerId,
        String nickName,
        String imageUrl,
        String memberMannerGrade
    ) {

    }

    public record PostInfo(
        Long postId,
        List<String> images
    ) {

    }
}
