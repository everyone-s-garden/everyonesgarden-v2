package com.garden.back.docs.appointment;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.garden.back.appointment.AppointmentController;
import com.garden.back.appointment.request.AppointRequest;
import com.garden.back.appointment.service.AppointmentService;
import com.garden.back.appointment.service.response.AppointmentResponse;
import com.garden.back.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AppointmentRestDocsTest extends RestDocsSupport {
    AppointmentService appointmentService = mock(AppointmentService.class);

    @Override
    protected Object initController() {
        return new AppointmentController(appointmentService);
    }

    @DisplayName("약속잡기 api docs")
    @Test
    void appoint() throws Exception {
        given(appointmentService.appoint(any())).willReturn(new AppointmentResponse(LocalDateTime.now().withSecond(0).withNano(0), LocalDateTime.now().minusMinutes(10).withSecond(0).withNano(0)));
        AppointRequest request = new AppointRequest(1L, LocalDateTime.now(), 10);

        mockMvc.perform(post("/v1/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-appointment",
                requestFields(
                    fieldWithPath("receiverId").type(NUMBER).description("약속잡기를 받은 사용자의 id"),
                    fieldWithPath("appointmentDate").type(STRING).description("약속 시간 yyyy-MM-dd HH:mm:ss 형식으로 입력"),
                    fieldWithPath("reminderBeforeMinutes").type(NUMBER).description("약속 리마인더 시간(분) ex: 10으로 입력 시 약속 시간 10분 전에 알림")
                ),
                responseFields(
                    fieldWithPath("appointmentDate").type(STRING).description("약속 날짜"),
                    fieldWithPath("alertTime").type(STRING).description("알람 시간")
                )
            ));
    }
}
