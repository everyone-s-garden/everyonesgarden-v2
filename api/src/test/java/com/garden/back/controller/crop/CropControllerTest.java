package com.garden.back.controller.crop;

import com.garden.back.ControllerTestSupport;
import com.garden.back.crop.request.AssignBuyerRequest;
import com.garden.back.crop.request.CropsPostCreateRequest;
import com.garden.back.crop.request.CropsPostsUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CropControllerTest extends ControllerTestSupport {

    // 유효하지 않은 파라미터 조합을 제공하는 메소드
    private static Stream<Arguments> invalidParameters() {
        return Stream.of(
            Arguments.of("-1", "5", "COMMENT_COUNT"), // 잘못된 offset
            Arguments.of("0", "-5", "RECENT_DATE"),  // 잘못된 limit
            Arguments.of("0", "5", "INVALID_SORT")   // 잘못된 orderBy
        );
    }

    @DisplayName("게시글 전체 조회 유효하지 않은 파라미터 테스트")
    @ParameterizedTest
    @MethodSource("invalidParameters")
    void getAllCropsPostInvalidParameters(String offset, String limit, String orderBy) throws Exception {
        mockMvc.perform(get("/v1/crops/posts") // URL의 prefix 수정
                .param("offset", offset)
                .param("limit", limit)
                .param("orderBy", orderBy)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> invalidCropsPostCreateRequests() {
        return Stream.of(
            Arguments.of("", "Valid content", "GRAIN", 100, true, "DIRECT_TRADE", 1L), // 잘못된 title
            Arguments.of("Valid title", "", "GRAIN", 100, true, "DIRECT_TRADE", 1L), // 잘못된 content
            Arguments.of("Valid title", "Valid content", "INVALID_CATEGORY", 100, true, "DIRECT_TRADE", 1L), // 잘못된 cropsCategory
            Arguments.of("Valid title", "Valid content", "GRAIN", -1, true, "DIRECT_TRADE", 1L), // 잘못된 price (음수)
            Arguments.of("Valid title", "Valid content", "GRAIN", 100, null, "DIRECT_TRADE", 1L), // priceProposal이 null
            Arguments.of("Valid title", "Valid content", "GRAIN", 100, true, "INVALID_TRADE_TYPE", 1L), // 잘못된 tradeType
            Arguments.of("Valid title", "Valid content", "GRAIN", 100, true, "DIRECT_TRADE", -1L) // 잘못된 memberAddressId

        );
    }

    @DisplayName("게시글 생성 유효하지 않은 요청 테스트")
    @ParameterizedTest
    @MethodSource("invalidCropsPostCreateRequests")
    void createCropsPostInvalidRequest(String title, String content, String cropsCategory, Integer price, Boolean priceProposal, String tradeType, Long memberAddressId) throws Exception {
        CropsPostCreateRequest request = new CropsPostCreateRequest(title, content, cropsCategory, price, priceProposal, tradeType, memberAddressId);
        MockMultipartFile mockRequestPart = new MockMultipartFile(
            "texts",
            "",
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
                multipart("/v1/crops/posts")
                    .file(mockRequestPart)
                    .file(firstImage)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> invalidPostUpdateRequests() {
        return Stream.of(
            Arguments.of("", "Valid content", "GRAIN", 100, true, "DIRECT_TRADE", "TRADING", Collections.emptyList(), 1L), // 잘못된 title
            Arguments.of("Valid title", "", "GRAIN", 100, true, "DIRECT_TRADE", "TRADING", Collections.emptyList(), 1L), // 잘못된 content
            Arguments.of("Valid title", "Valid content", "INVALID_CATEGORY", 100, true, "DIRECT_TRADE", "TRADING", Collections.emptyList(), 1L), // 잘못된 cropsCategory
            Arguments.of("Valid title", "Valid content", "GRAIN", -1, true, "DIRECT_TRADE", "TRADING", Collections.emptyList(), 1L), // 잘못된 price (음수)
            Arguments.of("Valid title", "Valid content", "GRAIN", 100, null, "DIRECT_TRADE", "TRADING", Collections.emptyList(), 1L), // priceProposal이 null
            Arguments.of("Valid title", "Valid content", "GRAIN", 100, true, "INVALID_TRADE_TYPE", "TRADING", Collections.emptyList(), 1L), // 잘못된 tradeType
            Arguments.of("Valid title", "Valid content", "GRAIN", 100, true, "DIRECT_TRADE", "INVALID_STATUS", Collections.emptyList(), 1L), // 잘못된 tradeStatus
            Arguments.of("Valid title", "Valid content", "GRAIN", 100, true, "DIRECT_TRADE", "TRADING", Collections.emptyList(), -1L) // 잘못된 memberAddressId
        );
    }


    @DisplayName("게시글 수정 유효하지 않은 요청 테스트")
    @ParameterizedTest
    @MethodSource("invalidPostUpdateRequests")
    void updatePostInvalidRequest(String title, String content, String cropsCategory, Integer price, Boolean priceProposal, String tradeType, String tradeStatus, List<String> deleteImages, Long memberAddressId) throws Exception {
        CropsPostsUpdateRequest request = new CropsPostsUpdateRequest(title, content, cropsCategory, price, priceProposal, tradeType, tradeStatus, deleteImages, memberAddressId);
        MockMultipartFile mockRequestPart = new MockMultipartFile(
            "texts",
            "",
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
                multipart("/v1/crops/posts/{postId}", 1L)
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

    @Test
    @DisplayName("구매자 할당 유효하지 않은 요청 테스트")
    void assignBuyerInvalidRequest() throws Exception {
        AssignBuyerRequest invalidRequest = new AssignBuyerRequest(null); // 잘못된 요청: buyerId가 null

        mockMvc.perform(
                patch("/v1/crops/posts/{postId}/assign-buyer", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest))
            )
            .andExpect(status().isBadRequest());
    }


}