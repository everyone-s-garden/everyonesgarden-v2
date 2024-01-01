package com.graden.back.controller;

import com.graden.back.controller.dto.ChatRoomCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatRoomControllerTest extends ControllerTestSupport{

    @DisplayName("학원 이름 검색에 요청값에 대해 검증한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidChatRoomCreateRequest")
    void getGardensByName_invalidRequest(ChatRoomCreateRequest chatRoomCreateRequest) throws Exception {
        mockMvc.perform(post("/chats")
                        .content(objectMapper.writeValueAsString(chatRoomCreateRequest)))
                .andExpect(status().is4xxClientError());
    }

    private static Stream<ChatRoomCreateRequest> provideInvalidChatRoomCreateRequest() {
        return Stream.of(
                new ChatRoomCreateRequest(
                        -1L,
                        1L,
                        "GARDEN"
                ),
                new ChatRoomCreateRequest(
                        1L,
                        0L,
                        "GARDEN"
                ),
                new ChatRoomCreateRequest(
                        1L,
                        1L,
                        "community"
                )
        );
    }
}
