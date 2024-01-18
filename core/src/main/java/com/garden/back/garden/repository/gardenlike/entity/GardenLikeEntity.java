package com.garden.back.garden.repository.gardenlike.entity;

import com.garden.back.garden.domain.GardenLike;
import com.garden.back.garden.repository.garden.entity.GardenEntity;
import com.garden.back.global.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "garden_likes")
public class GardenLikeEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "garden_like_id")
    private Long gardenLikeId;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_id")
    private GardenEntity garden;

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
