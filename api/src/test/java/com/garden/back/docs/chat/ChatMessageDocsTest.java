package com.garden.back.docs.chat;

import com.garden.back.chat.controller.ChatController;
import com.garden.back.docs.RestDocsSupport;
import com.garden.back.service.garden.GardenChatService;
import com.garden.back.service.garden.dto.response.GardenChatMessagesGetResults;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatMessageDocsTest extends RestDocsSupport {

    GardenChatService gardenChatService = mock(GardenChatService.class);

    @Override
    protected Object initController() {
        return new ChatController(gardenChatService);
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
}
