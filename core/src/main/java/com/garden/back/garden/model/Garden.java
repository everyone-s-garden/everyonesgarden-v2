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
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "gardens")
public class Garden {

    private final int DELETED_MAX_SCORE = 25;
    private final int MIN_DESCRIPTION_LENGTH = 15;

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

    @Column(name = "writer_id")
    private Long writerId;

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
            Double latitude,
            Double longitude,
            Point point,
            String gardenName,
            GardenType gardenType,
            GardenStatus gardenStatus,
            String linkForRequest,
            String price,
            String contact,
            String size,
            String gardenDescription,
            LocalDateTime recruitStartDate,
            LocalDateTime recruitEndDate,
            LocalDateTime useStartDate,
            LocalDateTime useEndDate,
            Boolean isToilet,
            Boolean isWaterway,
            Boolean isEquipment,
            Long writerId,
            boolean isDeleted,
            int reportedScore) {

        Assert.hasLength(address, "address는 null이거나 빈 값일 수 없습니다.");
        Assert.notNull(latitude, "latitude는 null일 수 없습니다.");
        Assert.notNull(longitude, "longitude는 null일 수 없습니다.");
        Assert.notNull(point, "point는 null일 수 없습니다.");
        Assert.hasLength(gardenName, "gardenName는 null이거나 빈 값일 수 없습니다.");
        Assert.hasLength(linkForRequest, "linkForRequest는 null이거나 빈 값일 수 없습니다.");
        Assert.hasLength(contact, "contact는 null이거나 빈 값일 수 없습니다.");
        Assert.notNull(isToilet, "isToilet은 null일 수 없습니다.");
        Assert.notNull(isWaterway, "isWaterway는 null일 수 없습니다.");
        Assert.notNull(isEquipment, "isEquipment는 null일 수 없습니다.");

        isNegativeReportedScore(reportedScore);
        isNegativePrice(price);
        isNegativeSize(size);
        hasDefaultDescriptionLength(gardenDescription);

        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.point = point;
        this.gardenName = gardenName;
        this.gardenType = gardenType;
        this.gardenStatus = gardenStatus;
        this.linkForRequest = linkForRequest;
        this.price = price;
        this.contact = contact;
        this.size = size;
        this.gardenDescription = gardenDescription;
        this.recruitStartDate = recruitStartDate;
        this.recruitEndDate = recruitEndDate;
        this.useStartDate = useStartDate;
        this.useEndDate = useEndDate;
        this.isToilet = isToilet;
        this.isWaterway = isWaterway;
        this.isEquipment = isEquipment;
        this.writerId = writerId;
        this.isDeleted = isDeleted;
        this.reportedScore = reportedScore;
    }

    public static Garden of(
            String address,
            Double latitude,
            Double longitude,
            Point point,
            String gardenName,
            GardenType gardenType,
            GardenStatus gardenStatus,
            String linkForRequest,
            String price,
            String contact,
            String size,
            String gardenDescription,
            LocalDateTime recruitStartDate,
            LocalDateTime recruitEndDate,
            LocalDateTime useStartDate,
            LocalDateTime useEndDate,
            Boolean isToilet,
            Boolean isWaterway,
            Boolean isEquipment,
            Long writerId,
            boolean isDeleted,
            int reportedScore
    ) {
        return new Garden(
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

    private void isNegativeReportedScore(int reportedScore) {
        if (reportedScore < 0) {
            throw new IllegalArgumentException("reportedScore는 음수일 수 없습니다.");
        }
    }

    private void isNegativePrice(String price) {
        Assert.hasLength(price, "price는 null이거나 빈 값일 수 없습니다.");
        if (Integer.parseInt(price) < 0) {
            throw new IllegalArgumentException("price는 음수일 수 없습니다.");
        }
    }

    private void isNegativeSize(String size) {
        Assert.hasLength(size, "size는 null이거나 빈 값일 수 없습니다.");
        if (Double.parseDouble(size) < 0) {
            throw new IllegalArgumentException("size는 음수일 수 없습니다.");
        }
    }

    private void hasDefaultDescriptionLength(String gardenDescription) {
        Assert.hasLength(gardenDescription, "gardenDescription는 null이거나 빈 값일 수 없습니다.");
        if (gardenDescription.length() < MIN_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(String.format("gardenDescription은 최소 %s여야 합니다", MIN_DESCRIPTION_LENGTH));
        }
    }

    public void validWriterId(Long requestMemberId) {
        if (!Objects.equals(writerId, requestMemberId)) {
            throw new IllegalArgumentException("텃밭 게시글 작성자만 텃밭을 삭제할 수 있습니다.");
        }
    }

}
