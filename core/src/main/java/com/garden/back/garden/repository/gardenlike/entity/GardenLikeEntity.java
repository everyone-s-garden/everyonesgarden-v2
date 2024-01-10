package com.garden.back.garden.repository.gardenlike.entity;

import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.domain.GardenLike;
import com.garden.back.garden.repository.garden.entity.GardenEntity;
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
@Table(name = "garden_likes")
public class GardenLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "garden_like_id")
    private Long gardenLikeId;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_id")
    private GardenEntity garden;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public GardenLike toModel() {
        return GardenLike.of(
                gardenLikeId,
                memberId,
                garden.toModel()
        );
    }

    public static GardenLikeEntity from( GardenLike gardenLike, GardenEntity garden) {
        GardenLikeEntity gardenLikeEntity = new GardenLikeEntity();
        gardenLikeEntity.gardenLikeId = gardenLike.getGardenLikeId();
        gardenLikeEntity.memberId = gardenLike.getMemberId();
        gardenLikeEntity.garden = garden;

        return gardenLikeEntity;
    }

    public static GardenLikeEntity from( GardenLike gardenLike) {
       GardenLikeEntity gardenLikeEntity = new GardenLikeEntity();
       gardenLikeEntity.gardenLikeId = gardenLike.getGardenLikeId();
       gardenLikeEntity.memberId = gardenLike.getMemberId();
       gardenLikeEntity.garden = GardenEntity.from(gardenLike.getGarden());

       return gardenLikeEntity;
    }

}
