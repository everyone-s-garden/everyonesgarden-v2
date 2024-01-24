package com.garden.back.docs.member;

import com.garden.back.docs.RestDocsSupport;
import com.garden.back.member.MemberRegionController;
import com.garden.back.member.dto.FindMyCurrentRegionRequest;
import com.garden.back.member.dto.UpdateMyRegionRequest;
import com.garden.back.member.region.FindAllMyAddressResponse;
import com.garden.back.member.region.FindMyCurrentRegionResponse;
import com.garden.back.member.region.MemberRegionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberRegionRestDocs extends RestDocsSupport {
    MemberRegionService memberRegionService = mock(MemberRegionService.class);

    @Override
    protected Object initController() {
        return new MemberRegionController(memberRegionService);
    }

    @DisplayName("사용자가 등록한 주소 조회 api docs")
    @Test
    void findAllMyAddress() throws Exception {
        FindAllMyAddressResponse response = sut.giveMeBuilder(FindAllMyAddressResponse.class)
            .size("addressInfos", 1)
            .set("addressInfos[0].addressId", 1L)
            .set("addressInfos[0].sido", "서울특별시")
            .set("addressInfos[0].sigungu", "성동구")
            .set("addressInfos[0].upmyeondong", "금호동")
            .sample();

        given(memberRegionService.findAllMyAddresses(any())).willReturn(response);

        mockMvc.perform(get("/members/my/regions")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-my-regions",
                responseFields(
                    fieldWithPath("addressInfos").type(ARRAY).description("작물 게시글 정보 목록"),
                    fieldWithPath("addressInfos[].addressId").type(NUMBER).description("내 주소의 id"),
                    fieldWithPath("addressInfos[].sido").type(STRING).description("내 주소의 시/도"),
                    fieldWithPath("addressInfos[].sigungu").type(STRING).description("내 주소의 시/군/구"),
                    fieldWithPath("addressInfos[].upmyeondong").type(STRING).description("내 주소의 읍/면/동")
                )
            ));
    }

    @DisplayName("사용자 사는 지역 등록 api docs")
    @Test
    void updateMyRegion() throws Exception {
        UpdateMyRegionRequest request = new UpdateMyRegionRequest("37.567924", "127.07849");
        given(memberRegionService.registerMyAddress(any())).willReturn(1L);

        mockMvc.perform(post("/members/my/regions")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("register-my-regions",
                responseHeaders(
                    headerWithName("Location").description("생성된 사용자 지역의 id")
                )
            ));
    }

    @DisplayName("사용자 사는 지역 삭제 api docs")
    @Test
    void deleteMyRegion() throws Exception {
        UpdateMyRegionRequest request = new UpdateMyRegionRequest("37.567924", "127.07849");
        given(memberRegionService.registerMyAddress(any())).willReturn(1L);

        mockMvc.perform(delete("/members/my/regions/{addressId}", 1L)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("delete-my-regions",
                pathParameters(
                    parameterWithName("addressId").description("삭제할 주소의 id")
                )
            ));
    }

    @DisplayName("현재 위치의 주소 조회 api docs")
    @Test
    void findMyCurrentRegion() throws Exception {
        FindMyCurrentRegionRequest request = new FindMyCurrentRegionRequest("37.567924", "127.07849");
        FindMyCurrentRegionResponse response = new FindMyCurrentRegionResponse("서울시 성동구 금호동");
        given(memberRegionService.findMyCurrentRegions(any(), any())).willReturn(response);

        mockMvc.perform(get("/members/my/current/regions")
                .param("latitude", request.latitude())
                .param("longitude", request.longitude())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("find-my-current-region",
                queryParameters(
                    parameterWithName("latitude").description("위도"),
                    parameterWithName("longitude").description("경도")
                ),
                responseFields(
                    fieldWithPath("address").type(STRING).description("내 주소")
                )
            ));
    }
}
