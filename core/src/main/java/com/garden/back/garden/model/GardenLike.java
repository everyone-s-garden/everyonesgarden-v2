package com.garden.back.garden.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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
    @Column(name = "garden_id")
    private Garden garden;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
