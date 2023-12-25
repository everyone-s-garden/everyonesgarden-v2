package com.garden.back.docs.garden;

import com.garden.back.docs.RestDocsSupport;
import com.garden.back.garden.GardenController;
import com.garden.back.garden.dto.request.GardenCreateRequest;
import com.garden.back.garden.dto.request.GardenLikeCreateRequest;
import com.garden.back.garden.dto.request.GardenLikeDeleteRequest;
import com.garden.back.garden.dto.request.GardenUpdateRequest;
import com.garden.back.garden.service.GardenCommandService;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.garden.service.dto.response.*;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GardenRestDocs extends RestDocsSupport {

    GardenReadService gardenReadService = mock(GardenReadService.class);
    GardenCommandService gardenCommandService = mock(GardenCommandService.class);

    @Override
    protected Object initController() {
        return new GardenController(gardenReadService, gardenCommandService);
    }

    @DisplayName("텃밭 이름으로 검색이 가능하다.")
    @Test
    void getGardenByName() throws Exception {
        GardenByNameResults gardenByNameResults = GardenFixture.gardenByNameResults();
        given(gardenReadService.getGardensByName(any())).willReturn(gardenByNameResults);

        mockMvc.perform(get("/v2/gardens")
                        .param("gardenName", "도연")
                        .param("pageNumber", "0")
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-gardens-by-name",
                        queryParameters(
                                parameterWithName("gardenName").description("검색하고자 하는 텃밭 이름"),
                                parameterWithName("pageNumber").description("요구하는 페이지 수")
                        ),
                        responseFields(
                                fieldWithPath("gardenSearchResponses").type(JsonFieldType.ARRAY).description("텃밭 이름 검색 결과"),
                                fieldWithPath("gardenSearchResponses[].gardenId").type(JsonFieldType.NUMBER).description("텃밭 ID"),
                                fieldWithPath("gardenSearchResponses[].gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                                fieldWithPath("gardenSearchResponses[].address").type(JsonFieldType.STRING).description("텃밭 주소"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부")
                        )));

    }

    @DisplayName("모든 텃밭을 조회한다.")
    @Test
    void getAllGardens() throws Exception {
        GardenAllResults gardenAllResults = GardenFixture.gardenAllResults();
        given(gardenReadService.getAllGarden(any())).willReturn(gardenAllResults);

        mockMvc.perform(get("/v2/gardens/all")
                        .param("pageNumber", "0")
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-all-gardens",
                        queryParameters(
                                parameterWithName("pageNumber").description("요구하는 페이짓 수")
                        ),
                        responseFields(
                                fieldWithPath("gardenGetAllResponses").type(JsonFieldType.ARRAY).description("텃밭 전체 검색 결과"),
                                fieldWithPath("gardenGetAllResponses[].gardenId").type(JsonFieldType.NUMBER).description("텃밭 아이디"),
                                fieldWithPath("gardenGetAllResponses[].latitude").type(JsonFieldType.NUMBER).description("텃밭 위도"),
                                fieldWithPath("gardenGetAllResponses[].longitude").type(JsonFieldType.NUMBER).description("텃밭 경도"),
                                fieldWithPath("gardenGetAllResponses[].gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                                fieldWithPath("gardenGetAllResponses[].gardenType").type(JsonFieldType.STRING).description("텃밭 타입 : PRIVATE(민간), PUBLIC(공공)"),
                                fieldWithPath("gardenGetAllResponses[].price").type(JsonFieldType.STRING).description("텃밭 가격"),
                                fieldWithPath("gardenGetAllResponses[].size").type(JsonFieldType.STRING).description("텃밭 크기"),
                                fieldWithPath("gardenGetAllResponses[].gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                                fieldWithPath("gardenGetAllResponses[].images").type(JsonFieldType.ARRAY).description("텃밭 이미지"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부")
                        )));
    }

    @DisplayName("해당 화면에 위치한 텃밭 정보를 반환한다.")
    @Test
    void getGardensByComplexes() throws Exception {
        GardenByComplexesResults gardenByComplexesResults = GardenFixture.gardenByComplexesResults();
        given(gardenReadService.getGardensByComplexes(any())).willReturn(gardenByComplexesResults);

        mockMvc.perform(get("/v2/gardens/by-complexes")
                        .param("gardenType", "PUBLIC")
                        .param("pageNumber", "0")
                        .param("startLat", "37.2449168")
                        .param("startLong", "127.1288684")
                        .param("endLat", "37.4449168")
                        .param("endLong", "127.1388684")
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-gardens-by-complexes",
                        queryParameters(
                                parameterWithName("gardenType").description("텃밭 타입 : ALL(모두),PRIVATE(민간), PUBLIC(공공) "),
                                parameterWithName("pageNumber").description("요청하는 페이지 수"),
                                parameterWithName("startLat").description("북서쪽 위도"),
                                parameterWithName("startLong").description("북서쪽 경도"),
                                parameterWithName("endLat").description("남동쪽 위도"),
                                parameterWithName("endLong").description("남동쪽 경도")
                        ),
                        responseFields(
                                fieldWithPath("gardenByComplexesResponses").type(JsonFieldType.ARRAY).description("위치 및 타입에 따른 텃밭 검색"),
                                fieldWithPath("gardenByComplexesResponses[].gardenId").type(JsonFieldType.NUMBER).description("텃밭 아이디"),
                                fieldWithPath("gardenByComplexesResponses[].size").type(JsonFieldType.STRING).description("텃밭 크기"),
                                fieldWithPath("gardenByComplexesResponses[].gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                                fieldWithPath("gardenByComplexesResponses[].price").type(JsonFieldType.STRING).description("텃밭 가격"),
                                fieldWithPath("gardenByComplexesResponses[].images").type(JsonFieldType.ARRAY).description("텃밭 이미지"),
                                fieldWithPath("gardenByComplexesResponses[].gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                                fieldWithPath("gardenByComplexesResponses[].gardenType").type(JsonFieldType.STRING).description("텃밭 타입 : PRIVATE(민간), PUBLIC(공공)"),
                                fieldWithPath("gardenByComplexesResponses[].latitude").type(JsonFieldType.NUMBER).description("텃밭 위도"),
                                fieldWithPath("gardenByComplexesResponses[].longitude").type(JsonFieldType.NUMBER).description("텃밭 경도"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부")
                        )));
    }

    @DisplayName("텃밭을 상세 조회할 수 있다.")
    @Test
    void getGardenDetail() throws Exception {
        Long gardenId = 1L;
        GardenDetailResult gardenDetailResult = GardenFixture.gardenDetailResult();
        given(gardenReadService.getGardenDetail(any())).willReturn(gardenDetailResult);

        mockMvc.perform(get("/v2/gardens/{gardenId}", gardenId))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("detail-garden",
                        pathParameters(
                                parameterWithName("gardenId").description("텃밭의 id")
                        ),
                        responseFields(
                                fieldWithPath("gardenId").type(JsonFieldType.NUMBER).description("텃밭 아이디"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("텃밭 주소"),
                                fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("텃밭 위도"),
                                fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("텃밭 경도"),
                                fieldWithPath("gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                                fieldWithPath("gardenType").type(JsonFieldType.STRING).description("텃밭 타입 : PRIVATE(민간), PUBLIC(공공)"),
                                fieldWithPath("linkForRequest").type(JsonFieldType.STRING).description("텃밭 신청 홈페이지"),
                                fieldWithPath("price").type(JsonFieldType.STRING).description("텃밭 가격"),
                                fieldWithPath("contact").type(JsonFieldType.STRING).description("연락처"),
                                fieldWithPath("size").type(JsonFieldType.STRING).description("텃밭 크기"),
                                fieldWithPath("gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                                fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                                fieldWithPath("recruitStartDate").type(JsonFieldType.STRING).description("모집 시작일"),
                                fieldWithPath("recruitEndDate").type(JsonFieldType.STRING).description("모집 마감일"),
                                fieldWithPath("useStartDate").type(JsonFieldType.STRING).description("사용 시작일"),
                                fieldWithPath("useEndDate").type(JsonFieldType.STRING).description("사용 종료일"),
                                fieldWithPath("gardenDescription").type(JsonFieldType.STRING).description("텃밭 설명"),
                                fieldWithPath("images").type(JsonFieldType.ARRAY).description("텃밭 사진"),
                                fieldWithPath("gardenFacility").type(JsonFieldType.OBJECT).description("텃밭 설비 정보"),
                                fieldWithPath("gardenFacility.isToilet").type(JsonFieldType.BOOLEAN).description("텃밭 화장실 제공 여부"),
                                fieldWithPath("gardenFacility.isWaterway").type(JsonFieldType.BOOLEAN).description("텃밭 수로 제공 여부"),
                                fieldWithPath("gardenFacility.isEquipment").type(JsonFieldType.BOOLEAN).description("농기구 제공 여부"),
                                fieldWithPath("isLiked").type(JsonFieldType.BOOLEAN).description("좋아요 여부")
                        )));
    }

    @DisplayName("최근 본 텃밭 정보를 조회할 수 있다.")
    @Test
    void getRecentGardens() throws Exception {
        RecentGardenResults recentGardenResults = GardenFixture.recentGardenResults();
        given(gardenReadService.getRecentGardens(any())).willReturn(recentGardenResults);

        mockMvc.perform(get("/v2/gardens/recent"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("recent-gardens",
                        responseFields(
                                fieldWithPath("recentGardenResponses").type(JsonFieldType.ARRAY).description("최근 본 텃밭 최대 10개"),
                                fieldWithPath("recentGardenResponses[].gardenId").type(JsonFieldType.NUMBER).description("텃밭 아이디"),
                                fieldWithPath("recentGardenResponses[].size").type(JsonFieldType.STRING).description("텃밭 크기"),
                                fieldWithPath("recentGardenResponses[].gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                                fieldWithPath("recentGardenResponses[].price").type(JsonFieldType.STRING).description("텃밭 가격"),
                                fieldWithPath("recentGardenResponses[].images").type(JsonFieldType.STRING).description("텃밭 이미지"),
                                fieldWithPath("recentGardenResponses[].gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                                fieldWithPath("recentGardenResponses[].gardenType").type(JsonFieldType.STRING).description("텃밭 타입 : PRIVATE(민간), PUBLIC(공공)")
                        )));
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

    @DisplayName("내가 등록한 텃밭들을 모두 조회할 수 있다.")
    @Test
    void getMyGardens() throws Exception {
        GardenMineResults gardenMineResults = GardenFixture.gardenMineResults();
        given(gardenReadService.getMyGarden(any())).willReturn(gardenMineResults);

        mockMvc.perform(get("/v2/gardens/mine"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-my-gardens",
                        responseFields(
                                fieldWithPath("gardenMineResponses").type(JsonFieldType.ARRAY).description("내가 등록한 텃밭 목록"),
                                fieldWithPath("gardenMineResponses[].gardenId").type(JsonFieldType.NUMBER).description("내가 등록한 텃밭 아이디"),
                                fieldWithPath("gardenMineResponses[].size").type(JsonFieldType.STRING).description("텃밭 크기"),
                                fieldWithPath("gardenMineResponses[].gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                                fieldWithPath("gardenMineResponses[].price").type(JsonFieldType.STRING).description("텃밭 가격"),
                                fieldWithPath("gardenMineResponses[].gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                                fieldWithPath("gardenMineResponses[].imageUrls").type(JsonFieldType.ARRAY).description("텃밭 사진")
                        )));
    }

    @DisplayName("내가 찜한 텃밭을 확인할 수 있다.")
    @Test
    void getLikeGardens() throws Exception {
        GardenLikeByMemberResults gardenLikeByMemberResults = GardenFixture.gardenLikeByMemberResults();
        given(gardenReadService.getLikeGardensByMember(any())).willReturn(gardenLikeByMemberResults);

        mockMvc.perform(get("/v2/gardens/likes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-like-gardens",
                        responseFields(
                                fieldWithPath("gardenLikeByMemberResponses").type(JsonFieldType.ARRAY).description("내가 찜한 텃밭 목록"),
                                fieldWithPath("gardenLikeByMemberResponses[].gardenId").type(JsonFieldType.NUMBER).description("내가 찜한 텃밭 아이디"),
                                fieldWithPath("gardenLikeByMemberResponses[].size").type(JsonFieldType.STRING).description("텃밭 크기"),
                                fieldWithPath("gardenLikeByMemberResponses[].gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                                fieldWithPath("gardenLikeByMemberResponses[].price").type(JsonFieldType.STRING).description("텃밭 가격"),
                                fieldWithPath("gardenLikeByMemberResponses[].gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                                fieldWithPath("gardenLikeByMemberResponses[].imageUrls").type(JsonFieldType.ARRAY).description("텃밭 사진")
                        )));
    }

    @DisplayName("텃밭을 찜할 수 있다.")
    @Test
    void createLikeGarden() throws Exception {
        GardenLikeCreateRequest gardenLikeCreateRequest = GardenFixture.gardenLikeCreateRequest();
        given(gardenCommandService.createGardenLike(any())).willReturn(1L);

        mockMvc.perform(post("/v2/gardens/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gardenLikeCreateRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-like-garden",
                        requestFields(
                                fieldWithPath("gardenId").type(JsonFieldType.NUMBER).description("찜하고자 하는 텃밭 아이디")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("생성된 찜한 텃밭의 id를 포함한 url")
                        )));
    }

    @DisplayName("텃밭을 삭제할 수 있다.")
    @Test
    void deleteLikeGarden() throws Exception {
        GardenLikeDeleteRequest gardenLikeDeleteRequest = GardenFixture.gardenLikeDeleteRequest();

        mockMvc.perform(delete("/v2/gardens/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gardenLikeDeleteRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("delete-like",
                        requestFields(
                                fieldWithPath("gardenId").type(JsonFieldType.NUMBER).description("찜하기를 취소하고자 하는 텃밭 아이디")
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
                                        fieldWithPath("size").type(JsonFieldType.STRING).description("텃밭 분양 크기"),
                                        fieldWithPath("gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                                        fieldWithPath("linkForRequest").type(JsonFieldType.STRING).description("텃밭 신청 사이트"),
                                        fieldWithPath("contact").type(JsonFieldType.STRING).description("연락처"),
                                        fieldWithPath("address").type(JsonFieldType.STRING).description("텃밭 주소"),
                                        fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("텃밭 위도"),
                                        fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("텃밭 경도"),
                                        fieldWithPath("isToilet").type(JsonFieldType.BOOLEAN).description("화장실 제공 여부"),
                                        fieldWithPath("isWaterway").type(JsonFieldType.BOOLEAN).description("수로 제공 여부"),
                                        fieldWithPath("isEquipment").type(JsonFieldType.BOOLEAN).description("농기구 제공 여부"),
                                        fieldWithPath("gardenDescription").type(JsonFieldType.STRING).description("텃밭 설명, 최소 10글이상"),
                                        fieldWithPath("recruitStartDate").type(JsonFieldType.STRING).description("모집 시작일 yyyy.MM.dd"),
                                        fieldWithPath("recruitEndDate").type(JsonFieldType.STRING).description("모집 마감일 yyyy.MM.dd"),
                                        fieldWithPath("useStartDate").type(JsonFieldType.STRING).description("사용 시작일 yyyy.MM.dd"),
                                        fieldWithPath("useEndDate").type(JsonFieldType.STRING).description("사용 종료일 yyyy.MM.dd")
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
                                fieldWithPath("size").type(JsonFieldType.STRING).description("텃밭 분양 크기"),
                                fieldWithPath("gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                                fieldWithPath("gardenType").type(JsonFieldType.STRING).description("텃밭 타입 : PRIVATE(민간), PUBLIC(공공)"),
                                fieldWithPath("linkForRequest").type(JsonFieldType.STRING).description("텃밭 신청 사이트"),
                                fieldWithPath("contact").type(JsonFieldType.STRING).description("연락처"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("텃밭 주소"),
                                fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("텃밭 위도"),
                                fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("텃밭 경도"),
                                fieldWithPath("isToilet").type(JsonFieldType.BOOLEAN).description("화장실 제공 여부"),
                                fieldWithPath("isWaterway").type(JsonFieldType.BOOLEAN).description("수로 제공 여부"),
                                fieldWithPath("isEquipment").type(JsonFieldType.BOOLEAN).description("농기구 제공 여부"),
                                fieldWithPath("gardenDescription").type(JsonFieldType.STRING).description("텃밭 설명, 최소 10글 이상"),
                                fieldWithPath("recruitStartDate").type(JsonFieldType.STRING).description("모집 시작일 yyyy.MM.dd"),
                                fieldWithPath("recruitEndDate").type(JsonFieldType.STRING).description("모집 마감일 yyyy.MM.dd"),
                                fieldWithPath("useStartDate").type(JsonFieldType.STRING).description("사용 시작일 yyyy.MM.dd"),
                                fieldWithPath("useEndDate").type(JsonFieldType.STRING).description("사용 종료일 yyyy.MM.dd")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("수정된 텃밭의 id를 포함한 URL")
                        )
                ));
    }


}
