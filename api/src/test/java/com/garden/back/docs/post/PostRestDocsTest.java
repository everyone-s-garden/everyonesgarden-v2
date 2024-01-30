package com.garden.back.docs.post;

import com.garden.back.docs.RestDocsSupport;
import com.garden.back.post.PostController;
import com.garden.back.post.domain.repository.response.FindAllPostsResponse;
import com.garden.back.post.domain.repository.response.FindPostDetailsResponse;
import com.garden.back.post.domain.repository.response.FindPostsAllCommentResponse;
import com.garden.back.post.request.*;
import com.garden.back.post.service.PostCommandService;
import com.garden.back.post.service.PostQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostRestDocsTest extends RestDocsSupport {
    PostQueryService postQueryService = mock(PostQueryService.class);
    PostCommandService postCommandService = mock(PostCommandService.class);

    @Override
    protected Object initController() {
        return new PostController(postQueryService, postCommandService);
    }

    @DisplayName("게시글 전체 조회 api docs")
    @Test
    void findAllPosts() throws Exception {
        FindAllPostsResponse response = new FindAllPostsResponse(List.of(new FindAllPostsResponse.PostInfo(1L, "제목", 2L, 3L, LocalDate.now())));
        FindAllPostParamRequest request = new FindAllPostParamRequest(0, 10, "title", "RECENT_DATE");
        given(postQueryService.findAllPosts(request.toRepositoryDto())).willReturn(response);

        mockMvc.perform(get("/v1/posts")
                .param("searchContent", request.searchContent())
                .param("offset", String.valueOf(request.offset()))
                .param("limit", String.valueOf(request.limit()))
                .param("orderBy", request.orderBy())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-all-posts",
                queryParameters(
                    parameterWithName("searchContent").description("검색할 제목 + 내용(해당 검색어로 검색할 경우 제목 또는 내용에 검색어가 포함되어 있을 경우 해당 게시글들을 반환, null일 경우 게시글 전체 조회)").optional(),
                    parameterWithName("offset").description("조회를 시작할 데이터의 위치"),
                    parameterWithName("limit").description("해당 페이지에서 조회할 데이터의 개수"),
                    parameterWithName("orderBy").description("정렬 조건(COMMENT_COUNT, RECENT_DATE, LIKE_COUNT, OLDER_DATE 중 한개를 입력해주세요)")
                ),
                responseFields(
                    fieldWithPath("postInfos").type(ARRAY).description("게시글 정보 목록"),
                    fieldWithPath("postInfos[].postId").type(NUMBER).description("게시글 ID"),
                    fieldWithPath("postInfos[].title").type(STRING).description("게시글 제목"),
                    fieldWithPath("postInfos[].likeCount").type(NUMBER).description("좋아요 수"),
                    fieldWithPath("postInfos[].commentCount").type(NUMBER).description("댓글 수"),
                    fieldWithPath("postInfos[].createdDate").type(STRING).description("생성 일")
                )
            ));
    }

    @DisplayName("게시글 상세 조회 api docs")
    @Test
    void findPostsDetails() throws Exception {
        FindPostDetailsResponse response = new FindPostDetailsResponse(10L, 1L, "작성자", "내용", "제목", LocalDate.now(), List.of("이미지 url"));
        given(postQueryService.findPostById(any())).willReturn(response);

        mockMvc.perform(get("/v1/posts/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("get-details-posts",
                pathParameters(
                    parameterWithName("postId").description("게시글 id")
                ),
                responseFields(
                    fieldWithPath("commentCount").type(NUMBER).description("댓글 수"),
                    fieldWithPath("likeCount").type(NUMBER).description("좋아요 수"),
                    fieldWithPath("author").type(STRING).description("작성자"),
                    fieldWithPath("content").type(STRING).description("내용"),
                    fieldWithPath("title").type(STRING).description("제목"),
                    fieldWithPath("createdDate").type(STRING).description("생성 일"),
                    fieldWithPath("images").type(ARRAY).description("이미지 목록")
                )
            ));
    }

    @DisplayName("특정 게시글의 댓글 조회 api docs")
    @Test
    void findPostsComments() throws Exception {
        FindPostsAllCommentResponse response = new FindPostsAllCommentResponse(List.of(new FindPostsAllCommentResponse.CommentInfo(2L, 1L, 0L, "대댓글", "작성자2"), new FindPostsAllCommentResponse.CommentInfo(1L, null, 0L, "댓글", "작성자1")));
        FindAllCommentsParamRequest request = new FindAllCommentsParamRequest(0, 10, "RECENT_DATE");
        given(postQueryService.findAllCommentsByPostId(any(), any())).willReturn(response);

        mockMvc.perform(get("/v1/posts/{postId}/comments", 1L)
                .param("offset", String.valueOf(request.offset()))
                .param("limit", String.valueOf(request.limit()))
                .param("orderBy", request.orderBy())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("get-posts-comments",
                queryParameters(
                    parameterWithName("offset").description("조회를 시작할 데이터의 위치"),
                    parameterWithName("limit").description("해당 페이지에서 조회할 데이터의 개수"),
                    parameterWithName("orderBy").description("정렬 조건(RECENT_DATE, LIKE_COUNT, OLDER_DATE 중 한개를 입력해주세요)")
                ),
                pathParameters(
                    parameterWithName("postId").description("게시글 id")
                ),
                responseFields(
                    fieldWithPath("commentInfos").description("댓글 정보 목록"),
                    fieldWithPath("commentInfos[].commentId").description("댓글 ID"),
                    fieldWithPath("commentInfos[].parentId").description("부모 댓글 ID (대댓글인 경우)").optional(),
                    fieldWithPath("commentInfos[].likeCount").description("좋아요 수"),
                    fieldWithPath("commentInfos[].content").description("댓글 내용"),
                    fieldWithPath("commentInfos[].author").description("댓글 작성자")
                )
            ));
    }

    @DisplayName("게시글 생성 api docs")
    @Test
    void createPost() throws Exception {

        MockMultipartFile firstImage = new MockMultipartFile(
            "images",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );

        PostCreateRequest request = sut.giveMeBuilder(PostCreateRequest.class)
            .set("title", "제목")
            .set("content", "내용")
            .sample();

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "texts",
            "content",
            MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(
                multipart("/v1/posts")
                    .file(firstImage)
                    .file(mockMultipartFile)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-posts",
                requestPartFields("texts",
                    fieldWithPath("content").type(STRING).description("게시글 내용"),
                    fieldWithPath("title").type(STRING).description("게시글 제목")
                ),
                requestParts(
                    partWithName("texts").description("게시글 내용 texts에는 json 형식으로 위 part 필드들에 대해 요청해주시면 됩니다."),
                    partWithName("images").description("게시글에 넣을 이미지(10개까지 허옹)").optional()
                ),
                responseHeaders(
                    headerWithName("Location").description("생성된 게시글의 id")
                )
            ));
    }

    @DisplayName("게시글 좋아요 api docs")
    @Test
    void createPostLikes() throws Exception {

        mockMvc.perform(post("/v1/posts/{postId}/likes", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("create-post-like",
                pathParameters(
                    parameterWithName("postId").description("게시글 id")
                ),
                responseHeaders(
                    headerWithName("Location").description("생성된 좋아요의 id")
                )
            ));
    }

    @DisplayName("게시글 수정 api docs")
    @Test
    void updatePost() throws Exception {
        MockMultipartFile firstImage = new MockMultipartFile(
            "images",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );

        PostUpdateRequest request = sut.giveMeBuilder(PostUpdateRequest.class)
            .set("title", "제목")
            .set("content", "내용")
            .size("deleteImages", 1)
            .set("deleteImages[0]", "해당 이미지의 url")
            .sample();

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "texts",
            "content",
            MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(
                multipart("/v1/posts/{postId}", 1L)
                    .file(firstImage)
                    .file(mockMultipartFile)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .with(req -> {
                        req.setMethod("PATCH");
                        return req;
                    })
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("update-post",
                pathParameters(
                    parameterWithName("postId").description("게시글 id")
                ),
                requestPartFields("texts",
                    fieldWithPath("content").type(STRING).description("게시글 내용"),
                    fieldWithPath("title").type(STRING).description("게시글 제목"),
                    fieldWithPath("deleteImages").type(ARRAY).description("삭제할 이미지의 url 목록")
                ),
                requestParts(
                    partWithName("texts").description("게시글 내용, 제목, 수정할 때 삭제되는 이미지 url들 texts에는 json 형식으로 위 part 필드들에 대해 요청해주시면 됩니다."),
                    partWithName("images").description("게시글에 넣을 이미지(10개까지 허옹)").optional()
                )
            ));
    }

    @DisplayName("게시글 삭제 api docs")
    @Test
    void deletePost() throws Exception {

        mockMvc.perform(delete("/v1/posts/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(document("delete-post",
                pathParameters(
                    parameterWithName("postId").description("삭제할 게시글 id")
                )
            ));
    }

    @DisplayName("댓글 생성 api docs")
    @Test
    void createComment() throws Exception {
        CommentCreateRequest request = sut.giveMeBuilder(CommentCreateRequest.class)
            .set("content", "댓글 내용")
            .set("parentCommentId", 1L)
            .sample();
        mockMvc.perform(post("/v1/posts/{postId}/comments", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(document("create-comment",
                pathParameters(
                    parameterWithName("postId").description("댓글을 달 게시글의 id")
                ),
                requestFields(
                    fieldWithPath("content").description("댓글 내용"),
                    fieldWithPath("parentCommentId").description("대댓글인 경우(상위 댓글 id)").optional()
                ),
                responseHeaders(
                    headerWithName("Location").description("생성된 댓글의 id")
                )
            ));
    }

    @DisplayName("댓글 좋아요 api docs")
    @Test
    void createCommentsLikes() throws Exception {
        mockMvc.perform(post("/v1/posts/comments/{commentId}/likes", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(document("create-comment-like",
                pathParameters(
                    parameterWithName("commentId").description("댓글 id")
                ),
                responseHeaders(
                    headerWithName("Location").description("생성된 좋아요의 id")
                )
            ));
    }

    @DisplayName("댓글 수정 api docs")
    @Test
    void updateComment() throws Exception {
        CommentUpdateRequest request = sut.giveMeBuilder(CommentUpdateRequest.class)
            .set("content", "댓글 내용")
            .sample();

        mockMvc.perform(patch("/v1/posts/comments/{commentId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(document("update-comment",
                pathParameters(
                    parameterWithName("commentId").description("댓글 id")
                ),
                requestFields(
                    fieldWithPath("content").type(STRING).description("댓글 내용")
                )
            ));
    }

    @DisplayName("댓글 삭제 api docs")
    @Test
    void deleteComment() throws Exception {
        mockMvc.perform(delete("/v1/posts/comments/{commentId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(document("delete-comment",
                pathParameters(
                    parameterWithName("commentId").description("댓글 id")
                )
            ));
    }

    @DisplayName("댓글 좋아요 취소 api docs")
    @Test
    void deleteCommentLike() throws Exception {
        mockMvc.perform(delete("/v1/posts/comments/{commentId}/likes", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(document("delete-comment-likes",
                pathParameters(
                    parameterWithName("commentId").description("댓글 id")
                )
            ));
    }

    @DisplayName("게시글 좋아요 취소 api docs")
    @Test
    void deletePostLike() throws Exception {
        mockMvc.perform(delete("/v1/posts/{postId}/likes", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(document("delete-post-likes",
                pathParameters(
                    parameterWithName("postId").description("게시글 id")
                )
            ));
    }
}
