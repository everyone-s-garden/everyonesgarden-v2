package com.garden.back.docs;

import com.garden.back.controller.ChatRoomController;
import com.garden.back.controller.dto.GardenChatRoomCreateRequest;
import com.garden.back.service.ChatRoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatDocsTest extends RestDocsSupport {

    ChatRoomService chatRoomService = mock(ChatRoomService.class);

    @Override
    protected Object initController() {
        return new ChatRoomController(chatRoomService);
    }

    @DisplayName("텃밭 분양 관련 채팅방을 생성할 수 있다.")
    @Test
    void createChatRoom() throws Exception {
        GardenChatRoomCreateRequest gardenChatRoomCreateRequest = ChatRoomFixture.chatRoomCreateRequest();
        given(chatRoomService.createGardenChatRoom(any())).willReturn(1L);

        mockMvc.perform(post("/chats/gardens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gardenChatRoomCreateRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-garden-chat-room",
                        requestFields(
                                fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("본 게시글의 작성자 아이디"),
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("본 게시글 아이디"),
                                fieldWithPath("chatType").type(JsonFieldType.STRING).description("채팅 타입 : GARDEN(텃밭 분양), HARVEST(농작물 거래)")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("생성된 채팅방 id를 포함한 url")
                        )));
    }
}
