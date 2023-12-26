package com.garden.back.garden.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="tended_gardens")
public class TendedGarden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "garden_name")
    private String gardenName;

    @Column(name="address")
    private String address;

    @Column(name="latitude")
    private Double latitude;

    @Column(name="longitude")
    private Double longitude;

    @Column(name="use_start_date")
    private LocalDate useStartDate;

    @Column(name="use_end_date")
    private LocalDate useEndDate;

    @Column(name="member_id")
    private Long memberId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "garden_id")
    private Long gardenId;

}
