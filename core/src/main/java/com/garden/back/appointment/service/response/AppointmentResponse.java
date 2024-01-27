package com.garden.back.appointment.service.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.garden.back.appointment.domain.Appointment;

import java.time.LocalDateTime;

public record AppointmentResponse(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime appointmentDate,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime alertTime
) {
    public static AppointmentResponse from(Appointment appointment) {
        return new AppointmentResponse(appointment.getAppointmentDate(), appointment.getAlertTime());
    }
}
