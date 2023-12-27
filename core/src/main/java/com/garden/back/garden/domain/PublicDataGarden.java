package com.garden.back.garden.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "public_data_gardens")
public class PublicDataGarden {

    protected PublicDataGarden() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_num")
    private int rowNum;

    @Column(name = "farm_id")
    private int farmId;

    @Column(name = "farm_type")
    private String farmType; // 개인, 공동, 지자체, 민간단체 등

    @Column(name = "farm_name")
    private String farmNm; // 텃밭 이름

    @Column(name = "area_large_category")
    private String areaLargeCategory;

    @Column(name = "area_large_name")
    private String areaLargeName;

    @Column(name = "area_medium_category")
    private String areaMediumCategory;

    @Column(name = "area_medium_name")
    private String areaMediumName;

    @Column(name = "full_address")
    private String fullAddress; // 풀 주소

    @Column(name = "farm_area_information")
    private String farmAreaInformation; // 면적

    @Column(name = "sell_area_information")
    private String sellAreaInformation; // 분양 면적

    @Column(name = "website")
    private String website; // 홈페이지

    @Column(name = "offsite_facilities")
    private String offsiteFacilities; // 편의시설

    @Column(name = "registration_date")
    private String registrationDate; // 등록 날짜

    @Column(name = "update_date")
    private String updateDate; // 업데이트 날짜

    @Column(name = "unique_hash")
    private int uniqueHash;

    public PublicDataGarden(
        int rowNum,
        int farmId,
        String farmType,
        String farmNm,
        String areaLargeCategory,
        String areaLargeName,
        String areaMediumCategory,
        String areaMediumName,
        String fullAddress,
        String farmAreaInformation,
        String sellAreaInformation,
        String website,
        String offsiteFacilities,
        String registrationDate,
        String updateDate
    ) {
        this.rowNum = rowNum;
        this.farmId = farmId;
        this.farmType = farmType;
        this.farmNm = farmNm;
        this.areaLargeCategory = areaLargeCategory;
        this.areaLargeName = areaLargeName;
        this.areaMediumCategory = areaMediumCategory;
        this.areaMediumName = areaMediumName;
        this.fullAddress = fullAddress;
        this.farmAreaInformation = farmAreaInformation;
        this.sellAreaInformation = sellAreaInformation;
        this.website = website;
        this.offsiteFacilities = offsiteFacilities;
        this.registrationDate = registrationDate;
        this.updateDate = updateDate;
        this.uniqueHash = Objects.hash(fullAddress, farmNm, registrationDate);
    }
}
