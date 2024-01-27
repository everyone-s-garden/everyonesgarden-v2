package com.garden.back.appointment.service;

import com.garden.back.appointment.domain.AppointmentRepository;
import com.garden.back.appointment.service.response.AppointmentResponse;
import com.garden.back.global.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AppointmentServiceTest extends IntegrationTestSupport {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    AppointmentRepository appointmentRepository;

    @DisplayName("약속을 잡는다.")
    @Test
    void appoint() {
        //given
        AppointmentServiceRequest request = new AppointmentServiceRequest(LocalDateTime.now().plusDays(1), 1L, 2L, 10);

        //when
        AppointmentResponse response = appointmentService.appoint(request);

        //then
        assertThat(request.appointmentDate().withSecond(0).withNano(0)).isEqualTo(response.appointmentDate());
    }
}