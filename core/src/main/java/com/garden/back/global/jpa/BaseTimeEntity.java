package com.garden.back.global.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createAt;

    //LocalDateTime의 os 별 작동 방식이 달라 모든 곳에서 작동 가능하도록 6자리수에서 올리기
    @PrePersist
    public void onPrePersist() {
        int nano = this.createAt.getNano();
        int newNano = (int) (Math.round(nano / 1000.0) * 1000);
        this.createAt = this.createAt.withNano(newNano);
    }
}