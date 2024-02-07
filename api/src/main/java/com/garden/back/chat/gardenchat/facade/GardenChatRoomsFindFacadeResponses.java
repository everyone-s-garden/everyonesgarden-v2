package com.garden.back.chat.gardenchat.facade;

import com.garden.back.garden.service.dto.response.GardenChatRoomsFindResults;
import com.garden.back.member.service.dto.MemberMyPageResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record GardenChatRoomsFindFacadeResponses(
    List<GardenChatRoomsFindFacadeResponse> responses,
    boolean hasNext
) {

    public static GardenChatRoomsFindFacadeResponses to(
        GardenChatRoomsFindResults chatMessagesInRooms,
        Map<Long, List<String>> imageUrlsById,
        Map<Long, MemberMyPageResult> memberInfoById
    ) {
        return new GardenChatRoomsFindFacadeResponses(
            chatMessagesInRooms.gardenChatRoomsFindResults().stream()
                .map(result -> GardenChatRoomsFindFacadeResponse.to(
                    result,
                    imageUrlsById.get(result.chatMessageId()),
                    memberInfoById.get(result.chatMessageId())
                )).toList(),
            chatMessagesInRooms.hasNest()
        );
    }

    public record GardenChatRoomsFindFacadeResponse(
        Long chatMessageId,
        LocalDateTime createdAt,
        int readNotCnt,
        Long chatRoomId,
        String recentContents,
        PartnerInfo partnerInfo,
        PostInfo postInfo

    ) {
        public static GardenChatRoomsFindFacadeResponse to(
            GardenChatRoomsFindResults.GardenChatRoomsFindResult result,
            List<String> imageUrls,
            MemberMyPageResult memberMyPageResult
        ) {
            return new GardenChatRoomsFindFacadeResponse(
                result.chatMessageId(),
                result.createdAt(),
                result.readNotCnt(),
                result.chatRoomId(),
                result.recentContents(),
                new PartnerInfo(
                    result.partnerId(),
                    memberMyPageResult.nickname(),
                    memberMyPageResult.imageUrl(),
                    memberMyPageResult.memberMannerGrade()),
                new PostInfo(
                    result.postId(),
                    imageUrls)
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
