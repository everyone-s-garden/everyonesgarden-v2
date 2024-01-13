package com.garden.back.garden.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GardenLike {

    private Long gardenLikeId;
    private Long memberId;
    private Garden garden;
    private LocalDateTime createdDate;

    protected GardenLike(
            Long gardenLikeId,
            Long memberId,
            Garden garden
    ) {
        isNullOrNegativeMemberId(memberId);
        Assert.notNull(garden, "garden은 null일 수 없습니다.");

        this.gardenLikeId = gardenLikeId;
        this.memberId = memberId;
        this.garden = garden;
    }

    public static GardenLike of(
            Long gardenLikeId,
            Long memberId,
            Garden garden
    ) {
        return new GardenLike(
                gardenLikeId,
                memberId,
                garden
        );
    }

    protected GardenLike(
            Long memberId,
            Garden garden
    ) {
        isNullOrNegativeMemberId(memberId);
        Assert.notNull(garden, "garden은 null일 수 없습니다.");

        this.memberId = memberId;
        this.garden = garden;
    }

    public static GardenLike of(
            Long memberId,
            Garden garden
    ) {
        return new GardenLike(
                memberId,
                garden
        );
    }

    private void isNullOrNegativeMemberId(Long memberId) {
        Assert.notNull(memberId, "memberId는 null일 수 없습니다.");
        if (memberId < 0L) {
            throw new IllegalArgumentException("memberId는 음수일 수 없습니다.");
        }
    }

}
