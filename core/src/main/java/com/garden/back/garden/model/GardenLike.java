package com.garden.back.garden.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "garden_likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "garden_id"})
})
public class GardenLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "garden_like_id")
    private Long gardenLikeId;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_id")
    private Garden garden;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    protected GardenLike(
            Long memberId,
            Garden garden
    ) {
        isNullOrNegativeMemberId(memberId);
        Assert.notNull(garden,"garden은 null일 수 없습니다.");

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
