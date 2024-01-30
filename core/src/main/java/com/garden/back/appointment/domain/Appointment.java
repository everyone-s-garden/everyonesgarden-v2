package com.garden.back.appointment.domain;

import com.garden.back.global.jpa.BaseTimeEntity;
import com.mysema.commons.lang.Assert;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "appointments")
public class Appointment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;

    @Column(name = "assigner_id")
    private Long assignerId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "alert_time")
    private LocalDateTime alertTime;

    public Appointment(
        LocalDateTime appointmentDate,
        Long assignerId,
        Long receiverId,
        Integer reminderBeforeMinutes
    ) {
        Assert.notNull(appointmentDate, "약속 날짜를 입력해주세요.");
        Assert.notNull(assignerId, "약속 할당자 id를 입력해주세요.");
        Assert.notNull(receiverId, "약속 받는 이용자 id를 입력해 주세요.");
        Assert.notNull(reminderBeforeMinutes, "");
        validateAppointmentDate(appointmentDate);

        this.appointmentDate = appointmentDate;
        this.assignerId = assignerId;
        this.receiverId = receiverId;
        this.alertTime = appointmentDate.minusMinutes(reminderBeforeMinutes);
    }

    @PrePersist
    @PreUpdate
    private void prepare() {
        this.appointmentDate = this.appointmentDate.withSecond(0).withNano(0);
        this.alertTime = this.alertTime.withSecond(0).withNano(0);
    }

    public static Appointment create
        (
            LocalDateTime appointmentDate,
            Long assignerId,
            Long receiverId,
            Integer reminderBeforeMinutes
        ) {
        return new Appointment(appointmentDate, assignerId, receiverId, reminderBeforeMinutes);
    }

    private void validateAppointmentDate(LocalDateTime appointmentDate) {
        if (appointmentDate.isBefore(LocalDateTime.now(ZoneId.of("Asia/Seoul")))) {
            throw new IllegalArgumentException("약속시간은 현재 시간 이후로 등록해야 합니다.");
        }
    }
}
