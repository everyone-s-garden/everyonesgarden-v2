package com.garden.back.appointment.service;

import com.garden.back.appointment.domain.Appointment;

import java.time.LocalDateTime;

public record AppointmentServiceRequest(
    LocalDateTime appointmentDate,
    Long assignerId,
    Long receiverId,
    Integer reminderBeforeMinutes
) {
    public Appointment toEntity() {
        return Appointment.create(appointmentDate, assignerId, receiverId, reminderBeforeMinutes);
    }
}
