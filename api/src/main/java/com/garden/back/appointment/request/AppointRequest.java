package com.garden.back.appointment.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.garden.back.appointment.service.AppointmentServiceRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record AppointRequest(
    @NotNull(message = "약속을 받는 이용자의 id를 입력해주세요.")
    @Positive(message = "약속을 받는 이용자의 id는 양수여야 합니다.")
    Long receiverId,

    @NotNull(message = "약속 날짜를 입력해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime appointmentDate,

    @NotNull(message = "리마인더의 시간을 입력해 주세요.")
    @Positive(message = "리마인더 시간은 양수여야 합니다.")
    Integer reminderBeforeMinutes
) {
    public AppointmentServiceRequest toServiceRequest(Long assignerId) {
        return new AppointmentServiceRequest(appointmentDate, assignerId, receiverId, reminderBeforeMinutes);
    }
}
