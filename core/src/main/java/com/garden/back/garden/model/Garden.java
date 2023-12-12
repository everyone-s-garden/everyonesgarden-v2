package com.garden.back.garden.model;

import com.garden.back.garden.model.vo.GardenStatus;
import com.garden.back.garden.model.vo.GardenType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "garden_name", nullable = false)
    private String gardenName;

    @Enumerated(EnumType.STRING)
    @Column(name = "garden_type", nullable = false)
    private GardenType gardenType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "garden_status", nullable = false)
    private GardenStatus gardenStatus;

    @Column(name = "link_for_request")
    private String linkFoRequest;

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

}
