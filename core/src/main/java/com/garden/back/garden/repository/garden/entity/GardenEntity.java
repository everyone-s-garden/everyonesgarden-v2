package com.garden.back.garden.repository.garden.entity;

import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.domain.vo.GardenType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "gardens")
public class GardenEntity {

    private static final int DEFAULT_REPORTED_SCORE = 0;
    private static final int DELETED_MAX_SCORE = 25;
    private static final int MIN_DESCRIPTION_LENGTH = 15;

    @Id
    @Column(name = "garden_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gardenId;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "point", nullable = false)
    private Point point;

    @Column(name = "garden_name", nullable = false)
    private String gardenName;

    @Enumerated(EnumType.STRING)
    @Column(name = "garden_type", nullable = false)
    private GardenType gardenType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "garden_status", nullable = false)
    private GardenStatus gardenStatus;

    @Column(name = "link_for_request")
    private String linkForRequest;

    @Column(name = "price")
    private String price;

    @Column(name = "contact")
    private String contact;

    @Column(name = "size")
    private String size;

    @Column(name = "garden_description", columnDefinition = "TEXT")
    private String gardenDescription;

    @Column(name = "recruit_start_date")
    private LocalDate recruitStartDate;

    @Column(name = "recruit_end_date")
    private LocalDate recruitEndDate;

    @Column(name = "use_start_date")
    private LocalDate useStartDate;

    @Column(name = "use_end_date")
    private LocalDate useEndDate;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDate lastModifiedDate;

    @Column(name = "is_toilet")
    private Boolean isToilet;

    @Column(name = "is_waterway")
    private Boolean isWaterway;

    @Column(name = "is_equipment")
    private Boolean isEquipment;

    @Column(name = "writer_id")
    private Long writerId;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "reported_score")
    private int reportedScore;

    public Garden toModel() {
        return Garden.of(
                gardenId,
                address,
                latitude,
                longitude,
                point,
                gardenName,
                gardenType,
                gardenStatus,
                linkForRequest,
                price,
                contact,
                size,
                gardenDescription,
                recruitStartDate,
                recruitEndDate,
                useStartDate,
                useEndDate,
                isToilet,
                isWaterway,
                isEquipment,
                writerId,
                isDeleted,
                reportedScore

        );
    }

    public static GardenEntity from(Garden garden) {
        GardenEntity gardenEntity = new GardenEntity();
        gardenEntity.gardenId = garden.getGardenId();
        gardenEntity.address = garden.getAddress();
        gardenEntity.latitude = garden.getLatitude();
        gardenEntity.longitude = garden.getLongitude();
        gardenEntity.point = garden.getPoint();
        gardenEntity.gardenName = garden.getGardenName();
        gardenEntity.gardenType = garden.getGardenType();
        gardenEntity.gardenStatus = garden.getGardenStatus();
        gardenEntity.linkForRequest = garden.getLinkForRequest();
        gardenEntity.price = garden.getPrice();
        gardenEntity.contact = garden.getContact();
        gardenEntity.size = garden.getSize();
        gardenEntity.gardenDescription = garden.getGardenDescription();
        gardenEntity.recruitStartDate = garden.getRecruitStartDate();
        gardenEntity.recruitEndDate = garden.getRecruitEndDate();
        gardenEntity.useStartDate = garden.getUseStartDate();
        gardenEntity.useEndDate = garden.getUseEndDate();
        gardenEntity.isToilet = garden.getIsToilet();
        gardenEntity.isWaterway = garden.getIsWaterway();
        gardenEntity.isEquipment = garden.getIsEquipment();
        gardenEntity.writerId = garden.getWriterId();
        gardenEntity.isDeleted = garden.isDeleted();
        gardenEntity.reportedScore = garden.getReportedScore();

        return gardenEntity;
    }

}

