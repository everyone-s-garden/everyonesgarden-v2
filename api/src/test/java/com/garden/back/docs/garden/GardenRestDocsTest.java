package com.garden.back.docs.garden;

import com.garden.back.docs.RestDocsSupport;
import com.garden.back.garden.controller.GardenController;
import com.garden.back.garden.facade.dto.GardenDetailFacadeResponse;
import com.garden.back.garden.facade.GardenFacade;
import com.garden.back.garden.facade.dto.OtherGardenGetFacadeResponses;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.garden.service.dto.response.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.nio.charset.StandardCharsets;

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

class GardenRestDocsTest extends RestDocsSupport {
    GardenReadService gardenReadService = mock(GardenReadService.class);
    GardenFacade gardenFacade = mock(GardenFacade.class);

    @Override
    protected Object initController() {
        return new GardenController(gardenReadService, gardenFacade);
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
                    parameterWithName("pageNumber").description("요구하는 페이지 수")
                ),
                responseFields(
                    fieldWithPath("gardenGetAllResponses").type(JsonFieldType.ARRAY).description("텃밭 전체 검색 결과"),
                    fieldWithPath("gardenGetAllResponses[].gardenId").type(JsonFieldType.NUMBER).description("텃밭 아이디"),
                    fieldWithPath("gardenGetAllResponses[].latitude").type(JsonFieldType.NUMBER).description("텃밭 위도"),
                    fieldWithPath("gardenGetAllResponses[].longitude").type(JsonFieldType.NUMBER).description("텃밭 경도"),
                    fieldWithPath("gardenGetAllResponses[].gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                    fieldWithPath("gardenGetAllResponses[].gardenType").type(JsonFieldType.STRING).description("텃밭 타입 : PRIVATE(민간), PUBLIC(공공)"),
                    fieldWithPath("gardenGetAllResponses[].price").type(JsonFieldType.STRING).description("텃밭 가격"),
                    fieldWithPath("gardenGetAllResponses[].size").type(JsonFieldType.STRING).description("텃밭 크기(m²)"),
                    fieldWithPath("gardenGetAllResponses[].gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                    fieldWithPath("gardenGetAllResponses[].images").type(JsonFieldType.ARRAY).description("텃밭 이미지"),
                    fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부")
                )));
    }

    @DisplayName("해당 목록에 위치한 텃밭 정보를 반환한다. - 무한스크롤")
    @Test
    void getGardensByComplexesWithScroll() throws Exception {
        GardenByComplexesWithScrollResults gardenByComplexesWithScrollResults = GardenFixture.gardenByComplexesWithScrollResults();
        given(gardenReadService.getGardensByComplexesWithScroll(any())).willReturn(gardenByComplexesWithScrollResults);

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
            .andDo(document("get-gardens-by-complexes-with-scroll",
                queryParameters(
                    parameterWithName("gardenType").description("텃밭 타입 : ALL(모두),PRIVATE(민간), PUBLIC(공공) "),
                    parameterWithName("pageNumber").description("요청하는 페이지 수"),
                    parameterWithName("startLat").description("북서쪽 위도"),
                    parameterWithName("startLong").description("북서쪽 경도"),
                    parameterWithName("endLat").description("남동쪽 위도"),
                    parameterWithName("endLong").description("남동쪽 경도")
                ),
                responseFields(
                    fieldWithPath("gardenByComplexesWithScrollResponses").type(JsonFieldType.ARRAY).description("위치 및 타입에 따른 텃밭 검색"),
                    fieldWithPath("gardenByComplexesWithScrollResponses[].gardenId").type(JsonFieldType.NUMBER).description("텃밭 아이디"),
                    fieldWithPath("gardenByComplexesWithScrollResponses[].size").type(JsonFieldType.STRING).description("텃밭 크기(m²)"),
                    fieldWithPath("gardenByComplexesWithScrollResponses[].gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                    fieldWithPath("gardenByComplexesWithScrollResponses[].price").type(JsonFieldType.STRING).description("텃밭 가격"),
                    fieldWithPath("gardenByComplexesWithScrollResponses[].images").type(JsonFieldType.ARRAY).description("텃밭 이미지"),
                    fieldWithPath("gardenByComplexesWithScrollResponses[].gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                    fieldWithPath("gardenByComplexesWithScrollResponses[].gardenType").type(JsonFieldType.STRING).description("텃밭 타입 : PRIVATE(민간), PUBLIC(공공)"),
                    fieldWithPath("gardenByComplexesWithScrollResponses[].latitude").type(JsonFieldType.NUMBER).description("텃밭 위도"),
                    fieldWithPath("gardenByComplexesWithScrollResponses[].longitude").type(JsonFieldType.NUMBER).description("텃밭 경도"),
                    fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부")
                )));
    }

    @DisplayName("해당 화면에 위치한 텃밭 정보를 반환한다.")
    @Test
    void getGardensByComplexes() throws Exception {
        GardenByComplexesResults gardenByComplexesResults = GardenFixture.gardenByComplexesResults();
        given(gardenReadService.getGardensByComplexes(any())).willReturn(gardenByComplexesResults);

        mockMvc.perform(get("/v2/gardens/by-complexes/all")
                .param("gardenType", "PUBLIC")
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
                    parameterWithName("startLat").description("북서쪽 위도"),
                    parameterWithName("startLong").description("북서쪽 경도"),
                    parameterWithName("endLat").description("남동쪽 위도"),
                    parameterWithName("endLong").description("남동쪽 경도")
                ),
                responseFields(
                    fieldWithPath("gardenByComplexesResponses").type(JsonFieldType.ARRAY).description("위치 및 타입에 따른 텃밭 검색"),
                    fieldWithPath("gardenByComplexesResponses[].gardenId").type(JsonFieldType.NUMBER).description("텃밭 아이디"),
                    fieldWithPath("gardenByComplexesResponses[].size").type(JsonFieldType.STRING).description("텃밭 크기(m²)"),
                    fieldWithPath("gardenByComplexesResponses[].gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                    fieldWithPath("gardenByComplexesResponses[].price").type(JsonFieldType.STRING).description("텃밭 가격"),
                    fieldWithPath("gardenByComplexesResponses[].images").type(JsonFieldType.ARRAY).description("텃밭 이미지"),
                    fieldWithPath("gardenByComplexesResponses[].gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                    fieldWithPath("gardenByComplexesResponses[].gardenType").type(JsonFieldType.STRING).description("텃밭 타입 : PRIVATE(민간), PUBLIC(공공)"),
                    fieldWithPath("gardenByComplexesResponses[].latitude").type(JsonFieldType.NUMBER).description("텃밭 위도"),
                    fieldWithPath("gardenByComplexesResponses[].longitude").type(JsonFieldType.NUMBER).description("텃밭 경도")
                )));
    }

    @DisplayName("텃밭을 상세 조회할 수 있다.")
    @Test
    void getGardenDetail() throws Exception {
        Long gardenId = 1L;
        GardenDetailFacadeResponse gardenDetailResult = GardenFixture.gardenDetailResult();
        given(gardenFacade.getGardenDetail(any())).willReturn(gardenDetailResult);

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
                    fieldWithPath("gardenType").type(JsonFieldType.STRING).description("텃밭 타입 : PRIVATE(민간), PUBLIC(공공), EVERY_FARM_PRIVATE(모두가 농부 - 민간), EVERY_FARM_PUBLIC(모두가 농부 - 공공)"),
                    fieldWithPath("price").type(JsonFieldType.STRING).description("텃밭 가격"),
                    fieldWithPath("contact").type(JsonFieldType.STRING).description("연락처이며 빈값 가능"),
                    fieldWithPath("size").type(JsonFieldType.STRING).description("텃밭 크기(m²)"),
                    fieldWithPath("gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                    fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("작성자 아이디 만약에 공공데이터의 경우에는 작성자가 없으므로 -1를 리턴한다."),
                    fieldWithPath("recruitStartDate").type(JsonFieldType.STRING).description("모집 시작일"),
                    fieldWithPath("recruitEndDate").type(JsonFieldType.STRING).description("모집 마감일"),
                    fieldWithPath("gardenDescription").type(JsonFieldType.STRING).description("텃밭 설명이며 최대 100글자까지 등록 가능하다. 빈값 가능"),
                    fieldWithPath("images").type(JsonFieldType.ARRAY).description("텃밭 사진"),
                    fieldWithPath("gardenFacilities").type(JsonFieldType.STRING).description("텃밭 부대시설 정보"),
                    fieldWithPath("openAPIResourceId").type(JsonFieldType.STRING).description(
                        " gardenType이 EVERY_FARM_PRIVATE(모두가 농부 - 민간), EVERY_FARM_PUBLIC(모두가 농부 - 공공)인 경우 신청하기 페이지 이동을 위해서 제공되는 식별자 제공"),
                    fieldWithPath("gardenLikeId").type(JsonFieldType.NUMBER).description("해당 텃밭 찜하기 ID, 텃밭을 찜하지 않았다면 0을 반환하고 텃밭을 찜했다면 0보다 큰 수를 응답한다."),
                    fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("해당 게시글에 대한 채팅방 아이디, 만약 채팅방이 없는 경우에는 -1L를 반환합니다.")
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
                    fieldWithPath("recentGardenResponses[].latitude").type(JsonFieldType.NUMBER).description("텃밭 위도"),
                    fieldWithPath("recentGardenResponses[].longitude").type(JsonFieldType.NUMBER).description("텃밭 경도"),
                    fieldWithPath("recentGardenResponses[].size").type(JsonFieldType.STRING).description("텃밭 크기(m²)"),
                    fieldWithPath("recentGardenResponses[].gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                    fieldWithPath("recentGardenResponses[].price").type(JsonFieldType.STRING).description("텃밭 가격"),
                    fieldWithPath("recentGardenResponses[].images").type(JsonFieldType.ARRAY).description("텃밭 이미지"),
                    fieldWithPath("recentGardenResponses[].gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                    fieldWithPath("recentGardenResponses[].gardenType").type(JsonFieldType.STRING).description("텃밭 타입 : PRIVATE(민간), PUBLIC(공공)")
                )));
    }

    @DisplayName("내가 등록한 텃밭들을 모두 조회할 수 있다.")
    @Test
    void getMyGardens() throws Exception {
        GardenMineResults gardenMineResults = GardenFixture.gardenMineResults();
        given(gardenReadService.getMyGarden(any())).willReturn(gardenMineResults);

        mockMvc.perform(get("/v2/gardens/mine")
                .param("nextGardenId", "0"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-my-gardens",
                queryParameters(
                    parameterWithName("nextGardenId").description("텃밭 넥스트 키 , 처음에는 다음 텃밭 키를 모르기 때문에 0을 보내주세요")
                ),
                responseFields(
                    fieldWithPath("gardenMineResponses").type(JsonFieldType.ARRAY).description("내가 등록한 텃밭 목록"),
                    fieldWithPath("gardenMineResponses[].gardenId").type(JsonFieldType.NUMBER).description("내가 등록한 텃밭 아이디"),
                    fieldWithPath("gardenMineResponses[].size").type(JsonFieldType.STRING).description("텃밭 크기(m²)"),
                    fieldWithPath("gardenMineResponses[].gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                    fieldWithPath("gardenMineResponses[].price").type(JsonFieldType.STRING).description("텃밭 가격"),
                    fieldWithPath("gardenMineResponses[].gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                    fieldWithPath("gardenMineResponses[].images").type(JsonFieldType.ARRAY).description("텃밭 사진"),
                    fieldWithPath("nextGardenId").type(JsonFieldType.NUMBER).description("텃밭 넥스트 키"),
                    fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부")
                )));
    }

    @DisplayName("내가 찜한 텃밭을 확인할 수 있다.")
    @Test
    void getLikeGardens() throws Exception {
        GardenLikeByMemberResults gardenLikeByMemberResults = GardenFixture.gardenLikeByMemberResults();
        given(gardenReadService.getLikeGardensByMember(any(), any())).willReturn(gardenLikeByMemberResults);

        mockMvc.perform(get("/v2/gardens/likes")
                .param("nextGardenId", "0"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-like-gardens",
                queryParameters(
                    parameterWithName("nextGardenId").description("텃밭 넥스트 키 , 처음에는 다음 텃밭 키를 모르기 때문에 0을 보내주세요")
                ),
                responseFields(
                    fieldWithPath("gardenLikeByMemberResponses").type(JsonFieldType.ARRAY).description("내가 찜한 텃밭 목록"),
                    fieldWithPath("gardenLikeByMemberResponses[].gardenId").type(JsonFieldType.NUMBER).description("내가 찜한 텃밭 아이디"),
                    fieldWithPath("gardenLikeByMemberResponses[].size").type(JsonFieldType.STRING).description("텃밭 크기(m²)"),
                    fieldWithPath("gardenLikeByMemberResponses[].gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                    fieldWithPath("gardenLikeByMemberResponses[].price").type(JsonFieldType.STRING).description("텃밭 가격"),
                    fieldWithPath("gardenLikeByMemberResponses[].gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태 : ACTIVE(모집중), INACTIVE(마감)"),
                    fieldWithPath("gardenLikeByMemberResponses[].images").type(JsonFieldType.ARRAY).description("텃밭 사진"),
                    fieldWithPath("nextGardenId").type(JsonFieldType.NUMBER).description("텃밭 넥스트 키"),
                    fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부")
                )));
    }

    @DisplayName("내가 가꾸는 텃밭 목록을 조회할 수 있다.")
    @Test
    void getMyManagedGardens() throws Exception {
        MyManagedGardenGetResults myManagedGardenGetResults = GardenFixture.myManagedGardenGetResults();
        given(gardenReadService.getMyManagedGardens(any())).willReturn(myManagedGardenGetResults);

        mockMvc.perform(get("/v2/gardens/my-managed")
                .param("nextMyManagedGardenId", "0"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-my-managed-gardens",
                queryParameters(
                    parameterWithName("nextMyManagedGardenId").description("내가 가꾸는 텃밭 넥스트 키 , 처음에는 다음 텃밭 키를 모르기 때문에 0을 보내주세요")
                ),
                responseFields(
                    fieldWithPath("myManagedGardenGetResponses").type(JsonFieldType.ARRAY).description("내가 가꾸는 텃밭 목록"),
                    fieldWithPath("myManagedGardenGetResponses[].myManagedGardenId").type(JsonFieldType.NUMBER).description("내가 가꾸는 텃밭 아이디"),
                    fieldWithPath("myManagedGardenGetResponses[].myManagedGardenName").type(JsonFieldType.STRING).description("가꾸는 텃밭의 농장 이름"),
                    fieldWithPath("myManagedGardenGetResponses[].createdAt").type(JsonFieldType.STRING).description("텃밭 일기 등록일"),
                    fieldWithPath("myManagedGardenGetResponses[].images").type(JsonFieldType.ARRAY).description("가꾸는 텃밭 대표 이미지 url"),
                    fieldWithPath("myManagedGardenGetResponses[].description").type(JsonFieldType.STRING).description("내가 가꾸는 텃밭 자랑할 만한 내용 또는 기록"),
                    fieldWithPath("nextMyManagedGardenId").type(JsonFieldType.NUMBER).description("내가 가꾸는 텃밭 넥스트 키"),
                    fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부")
                )));
    }

    @DisplayName("내가 가꾸는 텃밭 상세 보기할 수 있다.")
    @Test
    void getDetailMyManagedGarden() throws Exception {
        Long myManagedGardenId = 1L;
        MyManagedGardenDetailResult myManagedGardenDetailResult = GardenFixture.myManagedGardenDetailResult();
        given(gardenReadService.getDetailMyManagedGarden(any())).willReturn(myManagedGardenDetailResult);

        mockMvc.perform(get("/v2/gardens/my-managed/{myManagedGardenId}", myManagedGardenId))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-detail-my-managed-garden",
                pathParameters(
                    parameterWithName("myManagedGardenId").description("상세보기를 할 내가 가꾸는 텃밭 아이디")
                ),
                responseFields(
                    fieldWithPath("myManagedGardenId").type(JsonFieldType.NUMBER).description("내가 가꾸는 텃밭 아이디"),
                    fieldWithPath("myManagedGardenName").type(JsonFieldType.STRING).description("분양받은 텃밭의 이름"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("텃밭 사용 시작일"),
                    fieldWithPath("images").type(JsonFieldType.ARRAY).description("가꾸는 텃밭 대표 이미지 url"),
                    fieldWithPath("description").type(JsonFieldType.STRING).description("내가 가꾸는 텃밭 자랑할 만한 내용 또는 기록")
                )));
    }

    @DisplayName("최근 등록된 텃밭들을 조회한다.")
    @Test
    void getRecentCreatedGardens() throws Exception {
        RecentCreatedGardenResults results = GardenFixture.recentCreatedGardenResults();
        given(gardenReadService.getRecentCreatedGardens(any())).willReturn(results);

        mockMvc.perform(get("/v2/gardens/recent-created")
                .param("memberId", "0"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-recent-created-gardens",
                queryParameters(
                    parameterWithName("memberId").description("로그인한 사용자의 Id 없는 경우 0을 보내주세요")
                ),
                responseFields(
                    fieldWithPath("recentCreatedGardenResponses").type(JsonFieldType.ARRAY).description("텃밭 전체 검색 결과"),
                    fieldWithPath("recentCreatedGardenResponses[].gardenId").type(JsonFieldType.NUMBER).description("텃밭 아이디"),
                    fieldWithPath("recentCreatedGardenResponses[].gardenName").type(JsonFieldType.STRING).description("텃밭 이름"),
                    fieldWithPath("recentCreatedGardenResponses[].address").type(JsonFieldType.STRING).description("텃밭 주소"),
                    fieldWithPath("recentCreatedGardenResponses[].latitude").type(JsonFieldType.NUMBER).description("텃밭 위도"),
                    fieldWithPath("recentCreatedGardenResponses[].longitude").type(JsonFieldType.NUMBER).description("텃밭 경도"),
                    fieldWithPath("recentCreatedGardenResponses[].recruitStartDate").type(JsonFieldType.STRING).description("텃밭 모집 시작일"),
                    fieldWithPath("recentCreatedGardenResponses[].recruitEndDate").type(JsonFieldType.STRING).description("텃밭 모집 마감일"),
                    fieldWithPath("recentCreatedGardenResponses[].price").type(JsonFieldType.STRING).description("텃밭 가격"),
                    fieldWithPath("recentCreatedGardenResponses[].isLiked").type(JsonFieldType.BOOLEAN).description("텃밭 좋아요 여부"),
                    fieldWithPath("recentCreatedGardenResponses[].imageUrl").type(JsonFieldType.STRING).description("텃밭 이미지 썸네일")
                )));
    }

    @DisplayName("텃밭의 위도 경도를 알 수 있다.")
    @Test
    void getGardenLocation() throws Exception {
        Long gardenId = 1L;
        given(gardenReadService.getGardenLocation(any())).willReturn(GardenFixture.gardenLocationResult());

        mockMvc.perform(get("/v2/gardens/{gardenId}/locations", gardenId))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-garden-location",
                pathParameters(
                    parameterWithName("gardenId").description("텃밭 아이디")
                ),
                responseFields(
                    fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("텃밭 위도"),
                    fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("텃밭 경도")
                )
            ));
    }

    @DisplayName("상대방이 가꾸는 텃밭 목록을 조회할 수 있다.")
    @Test
    void visitOtherManagedGardens() throws Exception {
        OtherManagedGardenGetResults result = GardenFixture.otherManagedGardenGetResults();
        given(gardenReadService.visitOtherManagedGarden(any())).willReturn(result);

        mockMvc.perform(get("/v2/gardens/other-managed")
                .param("otherMemberIdToVisit", "1")
                .param("nextManagedGardenId", "0"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("visit-other-managed-gardens",
                queryParameters(
                    parameterWithName("otherMemberIdToVisit").description("방문하고자 하는 상대의 Member Id, 0이거나 음수일 수 없습니다."),
                    parameterWithName("nextManagedGardenId").description("상대방이 가꾸는 텃밭 넥스트 키 , 처음에는 다음 텃밭 키를 모르기 때문에 0을 보내주세요")
                ),
                responseFields(
                    fieldWithPath("otherManagedGardenGetResponses").type(JsonFieldType.ARRAY).description("상대방이 가꾸는 텃밭 목록"),
                    fieldWithPath("otherManagedGardenGetResponses[].myManagedGardenId").type(JsonFieldType.NUMBER).description("상대방의 가꾸는 텃밭 아이디"),
                    fieldWithPath("otherManagedGardenGetResponses[].myManagedGardenName").type(JsonFieldType.STRING).description("가꾸는 텃밭의 농장 이름"),
                    fieldWithPath("otherManagedGardenGetResponses[].createdAt").type(JsonFieldType.STRING).description("텃밭 일기 작성일"),
                    fieldWithPath("otherManagedGardenGetResponses[].images").type(JsonFieldType.ARRAY).description("가꾸는 텃밭 대표 이미지 url"),
                    fieldWithPath("otherManagedGardenGetResponses[].description").type(JsonFieldType.STRING).description("상대방이 가꾸는 텃밭 자랑할 만한 내용 또는 기록"),
                    fieldWithPath("nextManagedGardenId").type(JsonFieldType.NUMBER).description("상대방이 가꾸는 텃밭 넥스트 키"),
                    fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부")
                )));
    }

    @DisplayName("상대방이 분양하는 텃밭 목록을 조회할 수 있다.")
    @Test
    void visitOtherGardens() throws Exception {
        OtherGardenGetFacadeResponses result = GardenFixture.otherGardenGetFacadeResponses();
        given(gardenFacade.visitOtherGarden(any())).willReturn(result);

        mockMvc.perform(get("/v2/gardens/other")
                .param("otherMemberIdToVisit", "1")
                .param("nextManagedGardenId", "0"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("visit-other-gardens",
                queryParameters(
                    parameterWithName("otherMemberIdToVisit").description("방문하고자 하는 상대의 Member Id, 0이거나 음수일 수 없습니다."),
                    parameterWithName("nextManagedGardenId").description("상대방이 가꾸는 텃밭 넥스트 키 , 처음에는 다음 텃밭 키를 모르기 때문에 0을 보내주세요")
                ),
                responseFields(
                    fieldWithPath("otherGardenGetResponse").type(JsonFieldType.ARRAY).description("상대방이 분양하는 텃밭 목록"),
                    fieldWithPath("otherGardenGetResponse[].gardenId").type(JsonFieldType.NUMBER).description("상대방이 분양하는 텃밭 아이디"),
                    fieldWithPath("otherGardenGetResponse[].gardenName").type(JsonFieldType.STRING).description("분양하는 텃밭의 농장 이름"),
                    fieldWithPath("otherGardenGetResponse[].price").type(JsonFieldType.STRING).description("가격"),
                    fieldWithPath("otherGardenGetResponse[].contact").type(JsonFieldType.STRING).description("연락처"),
                    fieldWithPath("otherGardenGetResponse[].gardenStatus").type(JsonFieldType.STRING).description("텃밭 상태"),
                    fieldWithPath("otherGardenGetResponse[].images").type(JsonFieldType.ARRAY).description("가꾸는 텃밭 대표 이미지 url"),
                    fieldWithPath("otherGardenGetResponse[].chatRoomId").type(JsonFieldType.NUMBER).description("해당 게시글에 대한 채팅방 아이디, 만약에 존재하지 않으면 -1L을 반환"),
                    fieldWithPath("otherGardenGetResponse[].isLiked").type(JsonFieldType.BOOLEAN).description("내가 좋아요 했는지 여부"),
                    fieldWithPath("nextGardenId").type(JsonFieldType.NUMBER).description("상대방이 분양하는 텃밭 넥스트 키"),
                    fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부")
                )));
    }

}
