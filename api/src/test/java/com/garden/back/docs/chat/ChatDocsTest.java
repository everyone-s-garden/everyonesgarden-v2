package com.garden.back.docs.chat;

import com.garden.back.chat.controller.ChatRoomController;
import com.garden.back.chat.controller.dto.request.CropChatRoomCreateRequest;
import com.garden.back.chat.controller.dto.request.GardenChatRoomCreateRequest;
import com.garden.back.chat.controller.dto.request.GardenSessionCreateRequest;
import com.garden.back.chat.facade.ChatRoomFacade;
import com.garden.back.chat.facade.GardenChatRoomEnterFacadeResponse;
import com.garden.back.docs.RestDocsSupport;
import com.garden.back.service.crop.CropChatRoomService;
import com.garden.back.service.garden.GardenChatRoomService;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatDocsTest extends RestDocsSupport {

    GardenChatRoomService gardenChatRoomService = mock(GardenChatRoomService.class);
    CropChatRoomService cropChatRoomService = mock(CropChatRoomService.class);
    ChatRoomFacade chatRoomFacade = mock(ChatRoomFacade.class);

    @Override
    protected Object initController() {
        return new ChatRoomController(gardenChatRoomService, cropChatRoomService, chatRoomFacade);
    }

    @DisplayName("텃밭 분양 관련 채팅방을 생성할 수 있다.")
    @Test
    void createGardenChatRoom() throws Exception {
        GardenChatRoomCreateRequest gardenChatRoomCreateRequest = ChatRoomFixture.chatRoomCreateRequest();
        given(gardenChatRoomService.createGardenChatRoom(any())).willReturn(1L);

        mockMvc.perform(post("/chats/gardens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gardenChatRoomCreateRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-garden-chat-room",
                        requestFields(
                                fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("본 게시글의 작성자 아이디"),
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("본 게시글 아이디")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("생성된 채팅방 id를 포함한 url")
                        )));
    }

    @DisplayName("작물 거래 관련 채팅방을 생성할 수 있다.")
    @Test
    void createCropChatRoom() throws Exception {
        CropChatRoomCreateRequest cropChatRoomCreateRequest = ChatRoomFixture.chatRoomCropCreateRequest();
        given(cropChatRoomService.createCropChatRoom(any())).willReturn(1L);

        mockMvc.perform(post("/chats/crops")
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

    @DisplayName("텃밭 분양 채팅방에 입장한다.")
    @Test
    void enterGardenChatRoom() throws Exception {
        Long roomId = 1L;
        GardenChatRoomEnterFacadeResponse response = ChatRoomFixture.gardenChatRoomEnterFacadeResponse();
        given(chatRoomFacade.enterGardenChatRoom(any())).willReturn(response);

        mockMvc.perform(patch("/chats/gardens/{roomId}",roomId))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("enter-garden-chat-room",
                        pathParameters(
                                parameterWithName("roomId").description("채팅방 id")
                        ),
                        responseFields(
                                fieldWithPath("partnerId").type(JsonFieldType.NUMBER).description("상대방 아이디"),
                                fieldWithPath("partnerNickname").type(JsonFieldType.STRING).description("상대방 별명"),
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                                fieldWithPath("gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                                fieldWithPath("price").type(JsonFieldType.STRING).description("텃밭 가격"),
                                fieldWithPath("images").type(JsonFieldType.ARRAY).description("텃밭 이미지들")
                        )));
    }

    @DisplayName("텃밭 분양 관련 채팅방 세션을 생성할 수 있다.")
    @Test
    void createGardenChatSession() throws Exception {
        GardenSessionCreateRequest gardenSessionCreateRequest = ChatRoomFixture.gardenSessionCreateRequest();

        mockMvc.perform(post("/chats/gardens/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gardenSessionCreateRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-garden-chat-session",
                        requestFields(
                                fieldWithPath("sessionId").type(JsonFieldType.STRING).description("채팅 세션 아이디"),
                                fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("채팅방 아이디")
                        )));
    }


}
