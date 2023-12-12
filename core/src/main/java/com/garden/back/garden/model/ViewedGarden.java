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
@Table(name = "viewed_gardens")
public class ViewedGarden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gardenViewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_id")
    Garden garden;

    @Column(name = "member_id")
    private Long memberId;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
