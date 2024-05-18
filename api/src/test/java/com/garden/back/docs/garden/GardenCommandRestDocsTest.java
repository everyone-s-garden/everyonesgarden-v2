package com.garden.back.docs.garden;

import com.garden.back.docs.RestDocsSupport;
import com.garden.back.garden.controller.GardenCommandController;
import com.garden.back.garden.controller.dto.request.*;
import com.garden.back.garden.service.GardenCommandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GardenCommandRestDocsTest extends RestDocsSupport {
    GardenCommandService gardenCommandService = mock(GardenCommandService.class);

    @Override
    protected Object initController() {
        return new GardenCommandController(gardenCommandService);
    }

    @DisplayName("텃밭을 찜할 수 있다.")
    @Test
    void createLikeGarden() throws Exception {
        given(gardenCommandService.createGardenLike(any())).willReturn(1L);

        mockMvc.perform(post("/v2/gardens/{gardenId}/likes",1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-like-garden",
                pathParameters(
                    parameterWithName("gardenId").description("해당 텃밭 게시글의 ID")
                ),
                responseFields(
                    fieldWithPath("gardenLikeId").type(JsonFieldType.NUMBER).description("텃밭의 찜하기 ID")
                ),
                responseHeaders(
                    headerWithName("Location").description("생성된 찜한 텃밭의 id를 포함한 url")
                )));
    }

    @DisplayName("텃밭을 삭제할 수 있다.")
    @Test
    void deleteLikeGarden() throws Exception {
        mockMvc.perform(delete("/v2/gardens/likes/{gardenLikeId}",1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("delete-like",
                pathParameters(
                    parameterWithName("gardenLikeId").description("텃밭 찜하기 ID")
                )));
    }

    @DisplayName("분양하고자 하는 텃밭을 등록할 수 있다.")
    @Test
    void createGarden() throws Exception {
        GardenCreateRequest gardenCreateRequest = GardenFixture.gardenCreateRequest();
        MockMultipartFile gardenImage = new MockMultipartFile(
            "gardenImages",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );
        MockMultipartFile gardenCreateRequestAboutMultipart = new MockMultipartFile(
            "gardenCreateRequest",
            "gardenCreateRequest",
            MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsString(gardenCreateRequest).getBytes(StandardCharsets.UTF_8)
        );
        given(gardenCommandService.createGarden(any())).willReturn(1L);

        mockMvc.perform(multipart("/v2/gardens")
                .file(gardenImage)
                .file(gardenCreateRequestAboutMultipart)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .content(objectMapper.writeValueAsString(gardenCreateRequest)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andDo(document("create-garden",
                    requestParts(
                        partWithName("gardenImages").description("텃밭 이미지 파일"),
                        partWithName("gardenCreateRequest").description("텃밭 생성 요청 값")
                    ),
                    requestPartFields("gardenCreateRequest",
                        fieldWithPath("gardenName").type(JsonFieldType.STRING).description("등록하는 텃밭 이름"),
                        fieldWithPath("price").type(JsonFieldType.STRING).description("텃밭 분양 가격"),
                        fieldWithPath("size").type(JsonFieldType.STRING).description("텃밭 분양 크기(m²)"),
                        fieldWithPath("gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                        fieldWithPath("contact").type(JsonFieldType.STRING).description("연락처"),
                        fieldWithPath("address").type(JsonFieldType.STRING).description("텃밭 주소"),
                        fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("텃밭 위도"),
                        fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("텃밭 경도"),
                        fieldWithPath("isToilet").type(JsonFieldType.BOOLEAN).description("화장실 제공 여부"),
                        fieldWithPath("isWaterway").type(JsonFieldType.BOOLEAN).description("수로 제공 여부"),
                        fieldWithPath("isEquipment").type(JsonFieldType.BOOLEAN).description("농기구 제공 여부"),
                        fieldWithPath("gardenDescription").type(JsonFieldType.STRING).description("텃밭 설명, 최소 10글이상"),
                        fieldWithPath("recruitStartDate").type(JsonFieldType.STRING).description("모집 시작일 yyyy.MM.dd"),
                        fieldWithPath("recruitEndDate").type(JsonFieldType.STRING).description("모집 마감일 yyyy.MM.dd")
                    ),
                    responseHeaders(
                        headerWithName("Location").description("생성된 텃밭의 id를 포함한 url")
                    )
                )
            );
    }

    @DisplayName("게시한 텃밭을 수정할 수 있다.")
    @Test
    void updateGardens() throws Exception {
        Long gardenId = 1L;
        GardenUpdateRequest gardenUpdateRequest = GardenFixture.gardenUpdateRequest();
        MockMultipartFile gardenImage = new MockMultipartFile(
            "newGardenImages",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );
        MockMultipartFile gardenUpdateRequestAboutMultipart = new MockMultipartFile(
            "gardenUpdateRequest",
            "gardenUpdateRequest",
            MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsString(gardenUpdateRequest).getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders.multipart("/v2/gardens/{gardenId}", gardenId);

        requestBuilder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        given(gardenCommandService.updateGarden(any())).willReturn(gardenId);

        mockMvc.perform(requestBuilder
                .file(gardenImage)
                .file(gardenUpdateRequestAboutMultipart)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(gardenUpdateRequest)))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andExpect(header().exists("Location"))
            .andDo(document("update-garden",
                pathParameters(
                    parameterWithName("gardenId").description("수정하고자 하는 텃밭 아이디")
                ),
                requestParts(
                    partWithName("newGardenImages").description("추가로 등록하는 텃밭 이미지 파일"),
                    partWithName("gardenUpdateRequest").description("텃밭 수정 요청 값")
                ),
                requestPartFields("gardenUpdateRequest",
                    fieldWithPath("remainGardenImageUrls").type(JsonFieldType.ARRAY).description("남아있는 이미지 url들"),
                    fieldWithPath("gardenName").type(JsonFieldType.STRING).description("등록하는 텃밭 이름"),
                    fieldWithPath("price").type(JsonFieldType.STRING).description("텃밭 분양 가격"),
                    fieldWithPath("size").type(JsonFieldType.STRING).description("텃밭 분양 크기(m²)"),
                    fieldWithPath("gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                    fieldWithPath("gardenType").type(JsonFieldType.STRING).description("텃밭 타입 : PRIVATE(민간), PUBLIC(공공)"),
                    fieldWithPath("contact").type(JsonFieldType.STRING).description("연락처"),
                    fieldWithPath("address").type(JsonFieldType.STRING).description("텃밭 주소"),
                    fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("텃밭 위도"),
                    fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("텃밭 경도"),
                    fieldWithPath("isToilet").type(JsonFieldType.BOOLEAN).description("화장실 제공 여부"),
                    fieldWithPath("isWaterway").type(JsonFieldType.BOOLEAN).description("수로 제공 여부"),
                    fieldWithPath("isEquipment").type(JsonFieldType.BOOLEAN).description("농기구 제공 여부"),
                    fieldWithPath("gardenDescription").type(JsonFieldType.STRING).description("텃밭 설명, 최소 10글 이상"),
                    fieldWithPath("recruitStartDate").type(JsonFieldType.STRING).description("모집 시작일 yyyy.MM.dd"),
                    fieldWithPath("recruitEndDate").type(JsonFieldType.STRING).description("모집 마감일 yyyy.MM.dd")
                ),
                responseHeaders(
                    headerWithName("Location").description("수정된 텃밭의 id를 포함한 URL")
                )
            ));
    }

    @DisplayName("내가 가꾸는 텃밭을 삭제할 수 있다.")
    @Test
    void deleteMyManagedGarden() throws Exception {
        Long myManagedGardenId = 1L;

        mockMvc.perform(delete("/v2/gardens/my-managed/{myManagedGardenId}", myManagedGardenId))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document("delete-my-managed-garden",
                pathParameters(
                    parameterWithName("myManagedGardenId").description("삭제하고자 하는 가꾸는 텃밭 아이디")
                )));
    }

    @DisplayName("가꾸고자 하는 텃밭을 등록할 수 있다.")
    @Test
    void createMyManagedGarden() throws Exception {
        MyManagedGardenCreateRequest myManagedGardenCreateRequest = GardenFixture.myManagedGardenCreateRequest();
        MockMultipartFile gardenImage = new MockMultipartFile(
            "gardenImage",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );
        MockMultipartFile myMangedGardenCreateRequestAboutMultipart = new MockMultipartFile(
            "myManagedGardenCreateRequest",
            "myManagedGardenCreateRequest",
            MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsString(myManagedGardenCreateRequest).getBytes(StandardCharsets.UTF_8)
        );
        given(gardenCommandService.createMyManagedGarden(any())).willReturn(1L);

        mockMvc.perform(multipart("/v2/gardens/my-managed")
                .file(gardenImage)
                .file(myMangedGardenCreateRequestAboutMultipart)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .content(objectMapper.writeValueAsString(myManagedGardenCreateRequest)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andDo(document("create-my-managed-garden",
                    requestParts(
                        partWithName("gardenImage").description("텃밭 이미지 파일"),
                        partWithName("myManagedGardenCreateRequest").description("내가 가꾸는 텃밭 생성 요청 값")
                    ),
                    requestPartFields("myManagedGardenCreateRequest",
                        fieldWithPath("gardenId").type(JsonFieldType.NUMBER).description("분양받은 텃밭의 아이디"),
                        fieldWithPath("useStartDate").type(JsonFieldType.STRING).description("사용 시작일 yyyy.MM.dd"),
                        fieldWithPath("useEndDate").type(JsonFieldType.STRING).description("사용 종료일 yyyy.MM.dd"),
                        fieldWithPath("description").type(JsonFieldType.STRING).description("내가 가꾸는 텃밭 자랑할 만한 내용 또는 기록")
                    ),
                    responseHeaders(
                        headerWithName("Location").description("생성된 내가 가꾸는 텃밭의 id를 포함한 url")
                    )
                )
            );
    }

    @DisplayName("가꾸고자 하는 텃밭을 수정할 수 있다.")
    @Test
    void updateMyManagedGarden() throws Exception {
        Long myManagedGardenId = 1L;
        MyManagedGardenUpdateRequest myManagedGardenUpdateRequest = GardenFixture.myManagedGardenUpdateRequest();
        MockMultipartFile gardenImage = new MockMultipartFile(
            "gardenImage",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );
        MockMultipartFile gardenUpdateRequestAboutMultipart = new MockMultipartFile(
            "myManagedGardenUpdateRequest",
            "myManagedGardenUpdateRequest",
            MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsString(myManagedGardenUpdateRequest).getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartHttpServletRequestBuilder requestBuilder
            = RestDocumentationRequestBuilders.multipart("/v2/gardens/my-managed/{myManagedGardenId}", myManagedGardenId);

        requestBuilder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        given(gardenCommandService.updateGarden(any())).willReturn(myManagedGardenId);

        mockMvc.perform(requestBuilder
                .file(gardenImage)
                .file(gardenUpdateRequestAboutMultipart)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(myManagedGardenUpdateRequest)))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andExpect(header().exists("Location"))
            .andDo(document("update-my-managed-garden",
                pathParameters(
                    parameterWithName("myManagedGardenId").description("수정하는 내가 가꾸는 텃밭 아이디")
                ),
                requestParts(
                    partWithName("gardenImage").description("수정한 텃밭 사진"),
                    partWithName("myManagedGardenUpdateRequest").description("가꾸는 텃밭 수정 요청 값")
                ),
                requestPartFields("myManagedGardenUpdateRequest",
                    fieldWithPath("gardenId").type(JsonFieldType.NUMBER).description("분양받은 텃밭의 아이디"),
                    fieldWithPath("useStartDate").type(JsonFieldType.STRING).description("사용 시작일 yyyy.MM.dd"),
                    fieldWithPath("useEndDate").type(JsonFieldType.STRING).description("사용 종료일 yyyy.MM.dd"),
                    fieldWithPath("description").type(JsonFieldType.STRING).description("내가 가꾸는 텃밭 자랑할 만한 내용 또는 기록")
                ),
                responseHeaders(
                    headerWithName("Location").description("수정된 텃밭의 id를 포함한 URL")
                )
            ));
    }

    @DisplayName("텃밭을 삭제할 수 있다.")
    @Test
    void deleteGarden() throws Exception {
        Long gardenId = 1L;
        mockMvc.perform(delete("/v2/gardens/{gardenId}", gardenId))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document("delete-garden",
                pathParameters(
                    parameterWithName("gardenId").description("삭제하고자 하는 텃밭 아이디")
                )));
    }

}
