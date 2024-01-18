package com.garden.back.docs.chat.crop;

import com.garden.back.chat.cropchat.controller.CropChatRoomController;
import com.garden.back.chat.cropchat.controller.dto.request.CropChatRoomCreateRequest;
import com.garden.back.crop.service.CropChatRoomService;
import com.garden.back.docs.RestDocsSupport;
import com.garden.back.docs.chat.global.ChatRoomFixture;
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

    CropChatRoomService cropChatRoomService = mock(CropChatRoomService.class);

    @Override
    protected Object initController() {
        return new CropChatRoomController(cropChatRoomService);
    }

    @DisplayName("작물 거래 관련 채팅방을 생성할 수 있다.")
    @Test
    void createCropChatRoom() throws Exception {
        CropChatRoomCreateRequest cropChatRoomCreateRequest = ChatRoomFixture.chatRoomCropCreateRequest();
        given(cropChatRoomService.createCropChatRoom(any())).willReturn(1L);

        mockMvc.perform(post("/crop-chats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cropChatRoomCreateRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-crop-chat-room",
                        requestFields(
                                fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("본 게시글의 작성자 아이디"),
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("본 게시글 아이디")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("생성된 채팅방 id를 포함한 url")
                        )));
    }

}
