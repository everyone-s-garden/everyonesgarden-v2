package com.garden.back.docs.chat.garden;

import com.garden.back.chat.gardenchat.controller.GardenChatController;
import com.garden.back.chat.gardenchat.facade.ChatRoomFacade;
import com.garden.back.chat.gardenchat.facade.GardenChatRoomsFindFacadeResponses;
import com.garden.back.docs.RestDocsSupport;
import com.garden.back.docs.chat.global.ChatRoomFixture;
import com.garden.back.garden.service.GardenChatService;
import com.garden.back.garden.service.dto.response.GardenChatMessagesGetResults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatMessageDocsTest extends RestDocsSupport {

    GardenChatService gardenChatService = mock(GardenChatService.class);
    ChatRoomFacade chatRoomFacade = mock(ChatRoomFacade.class);

    @Override
    protected Object initController() {
        return new GardenChatController(gardenChatService, chatRoomFacade);
    }

    @DisplayName("텃밭 분양 채팅방 메세지 목록을 확인한다.")
    @Test
    void getGardenChatMessages() throws Exception {
        Long roomId = 1L;
        GardenChatMessagesGetResults gardenChatMessageGetResponses = ChatRoomFixture.gardenChatMessageGetResponses();
        given(gardenChatService.getChatRoomMessages(any())).willReturn(gardenChatMessageGetResponses);

        mockMvc.perform(get("/garden-chats/{roomId}/messages", roomId)
                        .param("pageNumber", String.valueOf(0)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-garden-chat-messages",
                        pathParameters(
                                parameterWithName("roomId").description("채팅방 id")
                        ),
                        queryParameters(
                                parameterWithName("pageNumber").description("페이지 번호")
                        ),
                        responseFields(
                                fieldWithPath("gardenChatMessageResponses[].chatMessageId").type(JsonFieldType.NUMBER).description("메세지 아이디"),
                                fieldWithPath("gardenChatMessageResponses[].memberId").type(JsonFieldType.NUMBER).description("보낸사람 아이디"),
                                fieldWithPath("gardenChatMessageResponses[].contents").type(JsonFieldType.STRING).description("메세지 내용"),
                                fieldWithPath("gardenChatMessageResponses[].createdAt").type(JsonFieldType.ARRAY).description("메세지 생성 날짜"),
                                fieldWithPath("gardenChatMessageResponses[].readOrNot").type(JsonFieldType.BOOLEAN).description("읽음 여부"),
                                fieldWithPath("gardenChatMessageResponses[].isMine").type(JsonFieldType.BOOLEAN).description("내가 보낸 메세지 여부"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"))
                ));
    }

    @DisplayName("해당 유저의 텃밭 분양 채팅방 목록을 확인한다.")
    @Test
    void getGardenChatRoomsInMember() throws Exception {
        GardenChatRoomsFindFacadeResponses gardenChatRoomsFindFacadeResponses = ChatRoomFixture.gardenChatRoomsFindFacadeResponses();
        given(chatRoomFacade.findChatRoomsInMember(any())).willReturn(gardenChatRoomsFindFacadeResponses);

        mockMvc.perform(get("/garden-chats")
                        .param("pageNumber", String.valueOf(0)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-garden-chat-rooms-in-member",
                        queryParameters(
                                parameterWithName("pageNumber").description("페이지 번호")
                        ),
                        responseFields(
                                fieldWithPath("responses[].chatMessageId").type(JsonFieldType.NUMBER).description("메세지 아이디"),
                                fieldWithPath("responses[].createdAt").type(JsonFieldType.ARRAY).description("최근 보낸 메세지 생성일"),
                                fieldWithPath("responses[].readNotCnt").type(JsonFieldType.NUMBER).description("읽지 않은 메세지 건수"),
                                fieldWithPath("responses[].chatRoomId").type(JsonFieldType.NUMBER).description("채팅방 아이디"),
                                fieldWithPath("responses[].recentContents").type(JsonFieldType.STRING).description("최근 보낸 메시지 내용"),
                                fieldWithPath("responses[].partnerInfo.partnerId").type(JsonFieldType.NUMBER).description("상대방 아이디"),
                                fieldWithPath("responses[].partnerInfo.nickName").type(JsonFieldType.STRING).description("상대방 별명"),
                                fieldWithPath("responses[].partnerInfo.imageUrl").type(JsonFieldType.STRING).description("상대방 사진 URL"),
                                fieldWithPath("responses[].postInfo.postId").type(JsonFieldType.NUMBER).description("채팅방 관련 텃밭 분양글 아이디"),
                                fieldWithPath("responses[].postInfo.images").type(JsonFieldType.ARRAY).description("채팅방 관련 텃밭 분양글 사진 URL"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"))
                ));
    }
}
