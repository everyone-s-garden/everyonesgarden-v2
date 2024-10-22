package com.garden.back.docs.chat.garden;

import com.garden.back.chat.gardenchat.controller.GardenChatRoomController;
import com.garden.back.chat.gardenchat.controller.dto.request.GardenChatReportRequest;
import com.garden.back.chat.gardenchat.controller.dto.request.GardenChatRoomCreateRequest;
import com.garden.back.chat.gardenchat.facade.ChatRoomFacade;
import com.garden.back.chat.gardenchat.facade.GardenChatRoomEnterFacadeResponse;
import com.garden.back.docs.RestDocsSupport;
import com.garden.back.docs.chat.global.ChatRoomFixture;
import com.garden.back.garden.service.GardenChatRoomService;
import com.garden.back.global.ChatReportType;
import com.garden.back.report.domain.garden.GardenReportType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatDocsTest extends RestDocsSupport {

    GardenChatRoomService gardenChatRoomService = mock(GardenChatRoomService.class);
    ChatRoomFacade chatRoomFacade = mock(ChatRoomFacade.class);

    @Override
    protected Object initController() {
        return new GardenChatRoomController(gardenChatRoomService, chatRoomFacade);
    }

    @DisplayName("텃밭 분양 관련 채팅방을 생성할 수 있다.")
    @Test
    void createGardenChatRoom() throws Exception {
        GardenChatRoomCreateRequest gardenChatRoomCreateRequest = ChatRoomFixture.chatRoomCreateRequest();
        given(gardenChatRoomService.createGardenChatRoom(any())).willReturn(1L);

        mockMvc.perform(post("/garden-chats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gardenChatRoomCreateRequest)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-garden-chat-room",
                requestFields(
                    fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("본 게시글의 작성자 아이디"),
                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("본 게시글 아이디")
                ),
                responseFields(
                    fieldWithPath("chatRoomId").type(JsonFieldType.NUMBER).description("생성된 채팅방 ID")
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

        mockMvc.perform(patch("/garden-chats/{roomId}", roomId))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("enter-garden-chat-room",
                pathParameters(
                    parameterWithName("roomId").description("채팅방 id")
                ),
                responseFields(
                    fieldWithPath("partnerId").type(JsonFieldType.NUMBER).description("상대방 아이디"),
                    fieldWithPath("partnerNickname").type(JsonFieldType.STRING).description("상대방 별명"),
                    fieldWithPath("partnerMannerGrade").type(JsonFieldType.STRING).description("상대방 매너 등급"),
                    fieldWithPath("partnerProfileImage").type(JsonFieldType.STRING).description("상대방 프로필 이미지"),
                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                    fieldWithPath("gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                    fieldWithPath("gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                    fieldWithPath("price").type(JsonFieldType.STRING).description("텃밭 가격"),
                    fieldWithPath("images").type(JsonFieldType.ARRAY).description("텃밭 이미지들")
                )));
    }

    @DisplayName("텃밭 분양 채팅방을 영구적으로 삭제한다.")
    @Test
    void deleteGardenChatRoom() throws Exception {
        Long roomId = 1L;
        mockMvc.perform(delete("/garden-chats/{roomId}", roomId))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document("delete-garden-chat-room",
                pathParameters(
                    parameterWithName("roomId").description("채팅방 id")
                )
            ));

    }

    @DisplayName("텃밭 분양 관련 채팅방을 신고할 수 있다.")
    @Test
    void reportGardenChatRoom() throws Exception {
        GardenChatReportRequest gardenChatReportRequest = ChatRoomFixture.gardenChatReportRequest();
        MockMultipartFile reportImages = new MockMultipartFile(
            "reportImages",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );
        MockMultipartFile gardenChatReportRequestAboutMultipart = new MockMultipartFile(
            "gardenChatReportRequest",
            "gardenChatReportRequest",
            MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsString(gardenChatReportRequest).getBytes(StandardCharsets.UTF_8)
        );
        given(gardenChatRoomService.reportChatRoom(any())).willReturn(1L);

        mockMvc.perform(multipart("/garden-chats/{roomId}/report", 1L)
                .file(reportImages)
                .file(gardenChatReportRequestAboutMultipart)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .content(objectMapper.writeValueAsString(gardenChatReportRequest)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("report-garden-chat-room",
                requestParts(
                    partWithName("reportImages").description("신고 이미지 파일들"),
                    partWithName("gardenChatReportRequest").description("텃밭 채팅방 신고 요청값")
                ),
                requestPartFields("gardenChatReportRequest",
                    fieldWithPath("reportedMemberId").type(JsonFieldType.NUMBER).description("신고당한 사람의 아이디"),
                    fieldWithPath("reportContent").type(JsonFieldType.STRING).description("신고내용"),
                    fieldWithPath("reportType").type(JsonFieldType.STRING).description("신고항목:" + Arrays.stream(ChatReportType.values())
                        .map(Enum::name)
                        .collect(Collectors.joining(", ")))
                ),
                responseFields(
                    fieldWithPath("reportId").type(JsonFieldType.NUMBER).description("생성된 신고 ID")
                )));
    }

    @DisplayName("텃밭 분양 채팅방에 상대방이 퇴장했는지 여부를 확인한다.")
    @Test
    void isExitedPartner() throws Exception {
        Long roomId = 1L;
        given(gardenChatRoomService.isExitedPartner(any())).willReturn(true);

        mockMvc.perform(get("/garden-chats/{roomId}/members", roomId))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("check-partner-exited",
                pathParameters(
                    parameterWithName("roomId").description("채팅방 id")
                ),
                responseFields(
                    fieldWithPath("isExitedPartner").type(JsonFieldType.BOOLEAN).description("상대방 퇴장 여부")
                )));
    }


}
