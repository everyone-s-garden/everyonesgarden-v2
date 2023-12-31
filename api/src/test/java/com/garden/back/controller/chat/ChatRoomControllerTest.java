package com.garden.back.controller.chat;

import com.garden.back.ControllerTestSupport;
import com.garden.back.chat.controller.dto.request.CropChatRoomCreateRequest;
import com.garden.back.chat.controller.dto.request.GardenChatRoomCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatRoomControllerTest extends ControllerTestSupport {

    @DisplayName("텃밭 분양 채팅방 생성 요청값에 대해 검증한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidGardenChatRoomCreateRequest")
    void createGardenChatRoom_invalidRequest(GardenChatRoomCreateRequest gardenChatRoomCreateRequest) throws Exception {
        mockMvc.perform(post("/chats/garden")
                        .content(objectMapper.writeValueAsString(gardenChatRoomCreateRequest)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("작물 거래 채팅방 생성 요청값에 대해 검증한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidCropChatRoomCreateRequest")
    void createGardenChatRoom_invalidRequest(CropChatRoomCreateRequest cropChatRoomCreateRequest) throws Exception {
        mockMvc.perform(post("/chats/crop")
                        .content(objectMapper.writeValueAsString(cropChatRoomCreateRequest)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("텃밭 분양 채팅방에 들어갈 때 요청값 대해 검증한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void enterGardenChatRoom_invalidRequest(Long roomId) throws Exception {
        mockMvc.perform(patch("/chats/gardens/{roomId}",roomId))
                .andExpect(status().is4xxClientError());
    }

    private static Stream<CropChatRoomCreateRequest> provideInvalidCropChatRoomCreateRequest() {
        return Stream.of(
                new CropChatRoomCreateRequest(
                        -1L,
                        1L
                ),
                new CropChatRoomCreateRequest(
                        1L,
                        0L
                ),
                new CropChatRoomCreateRequest(
                        1L,
                        1L
                )
        );
    }

    private static Stream<GardenChatRoomCreateRequest> provideInvalidGardenChatRoomCreateRequest() {
        return Stream.of(
                new GardenChatRoomCreateRequest(
                        -1L,
                        1L
                ),
                new GardenChatRoomCreateRequest(
                        1L,
                        0L
                ),
                new GardenChatRoomCreateRequest(
                        1L,
                        1L
                )
        );
    }

}
