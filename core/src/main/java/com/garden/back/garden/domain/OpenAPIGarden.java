package com.garden.back.garden.domain;

import com.garden.back.global.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@Table(name = "openAPI_gardens")
public class OpenAPIGarden extends BaseTimeEntity {

    protected OpenAPIGarden() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_num")
    private String rowNum;

    @Column(name = "openAPI_garden_id")
    private String openAPIGardenId;

    @Column(name = "garden_type")
    private String gardenType; // 개인, 공동, 지자체, 민간단체 등

    @Column(name = "garden_name")
    private String gardenName;

    @Column(name = "sido_code")
    private String sidoCode;

    @Column(name = "sido_name")
    private String sidoName;

    @Column(name = "sigungu_code")
    private String sigunguCode;

    @Column(name = "sigungu_name")
    private String sigunguName;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "total_garden_area_size")
    private String totalGardenAreaSize;

    @Column(name = "garden_area_size_to_sell")
    private String gardenAreaSizeToSell;

    @Column(name = "homepage")
    private String homepage;

    @Column(name = "garden_facilities")
    private String gardenFacilities;

    @Column(name = "registration_date")
    private String registrationDate;

    @Column(name = "update_date")
    private String updateDate;

    @Column(name = "recruitment_period")
    private String recruitmentPeriod;

    @Column(name = "how_to_apply")
    private String howToApply;

    @Column(name = "price")
    private String price;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "unique_hash")
    private int uniqueHash;

    @LastModifiedDate
    @Column(name = "updated_batch_at")
    private LocalDateTime updatedBatchAt;

    @CreatedDate
    @Column(name = "created_batch_at")
    private LocalDateTime createdBatchAt;

    protected OpenAPIGarden(
        String rowNum,
        String openAPIGardenId,
        String gardenType,
        String gardenName,
        String sidoCode,
        String sidoName,
        String sigunguCode,
        String sigunguName,
        String fullAddress,
        String totalGardenAreaSize,
        String gardenAreaSizeToSell,
        String homepage,
        String gardenFacilities,
        String registrationDate,
        String updateDate,
        String recruitmentPeriod,
        String howToApply,
        String price,
        String latitude,
        String longitude
    ) {
        this.rowNum = rowNum;
        this.openAPIGardenId = openAPIGardenId;
        this.gardenType = gardenType;
        this.gardenName = gardenName;
        this.sidoCode = sidoCode;
        this.sidoName = sidoName;
        this.sigunguCode = sigunguCode;
        this.sigunguName = sigunguName;
        this.fullAddress = fullAddress;
        this.totalGardenAreaSize = totalGardenAreaSize;
        this.gardenAreaSizeToSell = gardenAreaSizeToSell;
        this.homepage = homepage;
        this.gardenFacilities = gardenFacilities;
        this.registrationDate = registrationDate;
        this.updateDate = updateDate;
        this.recruitmentPeriod = recruitmentPeriod;
        this.howToApply = howToApply;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
        this.uniqueHash = Objects.hash(Double.parseDouble(latitude), Double.parseDouble(longitude), gardenName);
    }

    public static OpenAPIGarden of(
        String rowNum,
        String openAPIGardenId,
        String gardenType,
        String gardenName,
        String sidoCode,
        String sidoName,
        String sigunguCode,
        String sigunguName,
        String fullAddress,
        String totalGardenAreaSize,
        String gardenAreaSizeToSell,
        String homePage,
        String gardenFacilities,
        String registrationDate,
        String updateDate,
        String recruitmentPeriod,
        String howToApply,
        String price,
        String latitude,
        String longitude
    ) {
        return new OpenAPIGarden(
            rowNum,
            openAPIGardenId,
            gardenType,
            gardenName,
            sidoCode,
            sidoName,
            sigunguCode,
            sigunguName,
            fullAddress,
            totalGardenAreaSize,
            gardenAreaSizeToSell,
            homePage,
            gardenFacilities,
            registrationDate,
            updateDate,
            recruitmentPeriod,
            howToApply,
            price,
            latitude,
            longitude
        );

    }

    public void updateExitedPublicGarden(OpenAPIGarden openAPIGardenToUpdate) {
        this.rowNum = openAPIGardenToUpdate.rowNum;
        this.openAPIGardenId = openAPIGardenToUpdate.openAPIGardenId;
        this.gardenType = openAPIGardenToUpdate.gardenType;
        this.gardenName = openAPIGardenToUpdate.gardenName;
        this.sidoCode = openAPIGardenToUpdate.sidoCode;
        this.sidoName = openAPIGardenToUpdate.sidoName;
        this.sigunguCode = openAPIGardenToUpdate.sigunguCode;
        this.sigunguName = openAPIGardenToUpdate.sigunguName;
        this.fullAddress = openAPIGardenToUpdate.fullAddress;
        this.totalGardenAreaSize = openAPIGardenToUpdate.totalGardenAreaSize;
        this.gardenAreaSizeToSell = openAPIGardenToUpdate.gardenAreaSizeToSell;
        this.homepage = openAPIGardenToUpdate.homepage;
        this.gardenFacilities = openAPIGardenToUpdate.gardenFacilities;
        this.registrationDate = openAPIGardenToUpdate.registrationDate;
        this.updateDate = openAPIGardenToUpdate.updateDate;
    }
}
