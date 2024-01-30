package com.garden.back.docs.crop;

import com.garden.back.crop.service.CropQueryService;
import com.garden.back.crop.MyCropController;
import com.garden.back.crop.domain.repository.response.FindAllMyBookmarkCropPostsResponse;
import com.garden.back.crop.domain.repository.response.FindAllMyBoughtCropPostsResponse;
import com.garden.back.crop.domain.repository.response.FindAllMyCropPostsResponse;
import com.garden.back.crop.request.FindAllMyBookmarkCropPostsRequest;
import com.garden.back.crop.request.FindAllMyBoughtCropRequest;
import com.garden.back.crop.request.FindAllMyCropPostsRequest;
import com.garden.back.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyCropRestDocsTest extends RestDocsSupport {

    CropQueryService cropQueryService = mock(CropQueryService.class);

    @Override
    protected Object initController() {
        return new MyCropController(cropQueryService);
    }

    @DisplayName("내가 북마크 한 작물 게시글 api docs")
    @Test
    void findAllByMyBookmark() throws Exception{
        FindAllMyBookmarkCropPostsResponse response = new FindAllMyBookmarkCropPostsResponse(List.of(new FindAllMyBookmarkCropPostsResponse.CropInfo(1L, "제목", "이미지 url")));
        FindAllMyBookmarkCropPostsRequest request = new FindAllMyBookmarkCropPostsRequest(0L, 10L);
        given(cropQueryService.findAllByMyBookmark(any(), any())).willReturn(response);

        mockMvc.perform(get("/v1/my/crops/bookmarks")
                .param("offset", String.valueOf(request.offset()))
                .param("limit", String.valueOf(request.limit()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-my-bookmark-crop-posts",
                queryParameters(
                    parameterWithName("offset").description("조회를 시작할 데이터의 위치"),
                    parameterWithName("limit").description("해당 페이지에서 조회할 데이터의 개수")
                ),
                responseFields(
                    fieldWithPath("cropInfos").type(ARRAY).description("작물 게시글 정보 목록"),
                    fieldWithPath("cropInfos[].cropPostId").type(NUMBER).description("작물 게시글 ID"),
                    fieldWithPath("cropInfos[].title").type(STRING).description("작물 게시글 제목"),
                    fieldWithPath("cropInfos[].imageUrl").type(STRING).description("이미지 url")
                )
            ));
    }

    @DisplayName("내가 작성한 작물 게시글 api docs")
    @Test
    void findAllByMyCropPosts() throws Exception{
        FindAllMyCropPostsResponse response = new FindAllMyCropPostsResponse(List.of(new FindAllMyCropPostsResponse.CropInfo(1L, "제목", "이미지 url")));
        FindAllMyCropPostsRequest request = new FindAllMyCropPostsRequest(0L, 10L);
        given(cropQueryService.findAllMyCropPosts(any(), any())).willReturn(response);

        mockMvc.perform(get("/v1/my/crops")
                .param("offset", String.valueOf(request.offset()))
                .param("limit", String.valueOf(request.limit()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-my-crop-posts",
                queryParameters(
                    parameterWithName("offset").description("조회를 시작할 데이터의 위치"),
                    parameterWithName("limit").description("해당 페이지에서 조회할 데이터의 개수")
                ),
                responseFields(
                    fieldWithPath("cropInfos").type(ARRAY).description("작물 게시글 정보 목록"),
                    fieldWithPath("cropInfos[].cropPostId").type(NUMBER).description("작물 게시글 ID"),
                    fieldWithPath("cropInfos[].title").type(STRING).description("작물 게시글 제목"),
                    fieldWithPath("cropInfos[].imageUrl").type(STRING).description("이미지 url")
                )
            ));
    }


    @DisplayName("내가 구매한 작물 조회 api docs")
    @Test
    void findAllMyBoughtCrops() throws Exception {
        FindAllMyBoughtCropPostsResponse response = new FindAllMyBoughtCropPostsResponse(List.of(new FindAllMyBoughtCropPostsResponse.CropInfo(1L, "제목", "이미지 url")));
        FindAllMyBoughtCropRequest request = new FindAllMyBoughtCropRequest(0L, 10L);
        given(cropQueryService.findAllMyBoughtCrops(any())).willReturn(response);

        mockMvc.perform(get("/v1/my/crops/buy")
                .param("offset", String.valueOf(request.offset()))
                .param("limit", String.valueOf(request.limit()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-my-buy-crop-posts",
                queryParameters(
                    parameterWithName("offset").description("조회를 시작할 데이터의 위치"),
                    parameterWithName("limit").description("해당 페이지에서 조회할 데이터의 개수")
                ),
                responseFields(
                    fieldWithPath("cropInfos").type(ARRAY).description("작물 게시글 정보 목록"),
                    fieldWithPath("cropInfos[].cropPostId").type(NUMBER).description("작물 게시글 ID"),
                    fieldWithPath("cropInfos[].title").type(STRING).description("작물 게시글 제목"),
                    fieldWithPath("cropInfos[].imageUrl").type(STRING).description("이미지 url")
                )
            ));
    }
}
