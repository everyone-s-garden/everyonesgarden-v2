package com.garden.back.controller.post;

import com.garden.back.controller.ControllerTestSupport;
import com.garden.back.post.request.CommentCreateRequest;
import com.garden.back.post.request.CommentUpdateRequest;
import com.garden.back.post.request.PostCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest extends ControllerTestSupport {

    @DisplayName("게시글 전체 조회 유효하지 않은 파라미터 테스트")
    @ParameterizedTest
    @MethodSource("invalidParameters")
    void findAllPostsInvalidParameters(String offset, String limit, String orderBy) throws Exception {
        mockMvc.perform(get("/v1/posts")
                .param("offset", offset)
                .param("limit", limit)
                .param("orderBy", orderBy)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    private static Stream<String[]> invalidParameters() {
        return Stream.of(
            new String[]{"-1", "5", "COMMENT_COUNT"}, // 잘못된 offset
            new String[]{"0", "-5", "RECENT_DATE"},  // 잘못된 limit
            new String[]{"0", "5", "INVALID_SORT"}   // 잘못된 orderBy
            // 추가적인 유효하지 않은 파라미터 조합을 여기에 포함시킬 수 있습니다.
        );
    }

    @DisplayName("게시글 생성 유효하지 않은 요청 테스트")
    @ParameterizedTest
    @MethodSource("invalidPostCreateRequests")
    void createPostInvalidRequest(String title, String content) throws Exception {
        PostCreateRequest request = new PostCreateRequest(title, content);
        MockMultipartFile mockRequestPart = new MockMultipartFile(
            "texts",
            "content",
            MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile firstImage = new MockMultipartFile(
            "images",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );

        mockMvc.perform(
                multipart("/v1/posts")
                    .file(mockRequestPart)
                    .file(firstImage)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andExpect(status().isBadRequest());
    }

    private static Stream<String[]> invalidPostCreateRequests() {
        return Stream.of(
            new String[]{"", "Valid content"}, // 잘못된 title
            new String[]{"Valid title", ""}  // 잘못된 content
        );
    }

    @DisplayName("게시글 수정 유효하지 않은 요청 테스트")
    @ParameterizedTest
    @MethodSource("invalidPostUpdateRequests")
    void updatePostInvalidRequest(String title, String content) throws Exception {
        PostCreateRequest request = new PostCreateRequest(title, content);
        MockMultipartFile mockRequestPart = new MockMultipartFile(
            "texts",
            "content",
            MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile firstImage = new MockMultipartFile(
            "images",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );

        mockMvc.perform(
                multipart("/v1/posts/{postId}", 1L)
                    .file(firstImage)
                    .file(mockRequestPart)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .with(req -> {
                        req.setMethod("PATCH");
                        return req;
                    })
            )
            .andExpect(status().isBadRequest());
    }

    private static Stream<String[]> invalidPostUpdateRequests() {
        return Stream.of(
            new String[]{"", "Valid content"}, // 잘못된 title
            new String[]{"Valid title", ""}  // 잘못된 content
        );
    }

    @DisplayName("댓글 생성 유효하지 않은 내용 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    ", "a"}) // 잘못된 content 값 (빈 문자열, 공백 등)
    void createCommentInvalidContent(String content) throws Exception {
        content.repeat(256);
        CommentCreateRequest request = new CommentCreateRequest(content);

        mockMvc.perform(post("/v1/posts/{postId}/comments", 1L)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("댓글 수정 유효하지 않은 내용 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    ", "a"}) // 잘못된 content 값 (빈 문자열, 공백, 256자 초과 문자열)
    void updateCommentInvalidContent(String content) throws Exception {
        content.repeat(256);
        CommentUpdateRequest request = new CommentUpdateRequest(content);

        mockMvc.perform(patch("/v1/posts/comments/{commentId}", 1L)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }
}