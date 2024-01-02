package com.graden.back.controller;

import com.garden.back.controller.dto.GardenChatRoomCreateRequest;
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
    void getGardensByName_invalidRequest(GardenChatRoomCreateRequest gardenChatRoomCreateRequest) throws Exception {
        mockMvc.perform(post("/chats")
                        .content(objectMapper.writeValueAsString(gardenChatRoomCreateRequest)))
                .andExpect(status().is4xxClientError());
    }

    private static Stream<GardenChatRoomCreateRequest> provideInvalidChatRoomCreateRequest() {
        return Stream.of(
                new GardenChatRoomCreateRequest(
                        -1L,
                        1L,
                        "GARDEN"
                ),
                new GardenChatRoomCreateRequest(
                        1L,
                        0L,
                        "GARDEN"
                ),
                new GardenChatRoomCreateRequest(
                        1L,
                        1L,
                        "community"
                )
        );
    }
}
