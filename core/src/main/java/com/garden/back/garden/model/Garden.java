package com.garden.back.garden.model;

import com.garden.back.garden.model.vo.GardenStatus;
import com.garden.back.garden.model.vo.GardenType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "gardens")
public class Garden {

    private final int DELETED_MAX_SCORE = 25;

    @Id
    @Column(name = "garden_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gardenId;

    @Column(name = "address", nullable = false)
    private String address;

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
    private LocalDateTime recruitStartDate;

    @Column(name = "recruit_end_date")
    private LocalDateTime recruitEndDate;

    @Column(name = "use_start_date")
    private LocalDateTime useStartDate;

    @Column(name = "use_end_date")
    private LocalDateTime useEndDate;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "is_toilet")
    private Boolean isToilet;

    @Column(name = "is_waterway")
    private Boolean isWaterway;

    @Column(name = "is_equipment")
    private Boolean isEquipment;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "reported_score")
    private int reportedScore;

    public void changeDelete() {
        isDeleted = true;
    }

    public void registerReport(int score) {
        reportedScore += score;
        if (reportedScore > DELETED_MAX_SCORE) {
            changeDelete();
        }
    }

    protected Garden(
            String address,
            Point point,
            String gardenName,
            GardenType gardenType,
            GardenStatus gardenStatus
    ) {
        this.address =address;
        this.point = point;
        this.gardenName = gardenName;
        this.gardenType = gardenType;
        this.gardenStatus = gardenStatus;
    }


    public static Garden of(
            String address,
            Point point,
            String gardenName,
            GardenType gardenType,
            GardenStatus gardenStatus
    ){
        return new Garden(
                address,
                point,
                gardenName,
                gardenType,
                gardenStatus
        );
    }

}
