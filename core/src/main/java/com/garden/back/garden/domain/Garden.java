package com.garden.back.garden.domain;

import com.garden.back.garden.domain.dto.GardenUpdateDomainRequest;
import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.domain.vo.GardenType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Garden {

    private static final int DEFAULT_REPORTED_SCORE = 0;
    private static final int DELETED_MAX_SCORE = 25;
    private static final int MAX_DESCRIPTION_LENGTH = 100;
    private static final String DEFAULT_FIELD = "";

    private Long gardenId;
    private String address;
    private Double latitude;
    private Double longitude;
    private Point point;
    private String gardenName;
    private GardenType gardenType;
    private GardenStatus gardenStatus;
    private String price;
    private String contact;
    private String size;
    private String gardenDescription;
    private String recruitStartDate;
    private String recruitEndDate;
    private LocalDate createdAt;
    private LocalDate lastModifiedDate;
    private String gardenFacilities;
    private Long writerId;
    private boolean isDeleted;
    private int reportedScore;
    private int resourceHashId;

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
        Long id,
        String address,
        Double latitude,
        Double longitude,
        Point point,
        String gardenName,
        GardenType gardenType,
        GardenStatus gardenStatus,
        String price,
        String contact,
        String size,
        String gardenDescription,
        String recruitStartDate,
        String recruitEndDate,
        String gardenFacilities,
        Long writerId,
        boolean isDeleted,
        int reportedScore) {

        Assert.hasLength(address, "address는 null이거나 빈 값일 수 없습니다.");
        Assert.notNull(latitude, "latitude는 null일 수 없습니다.");
        Assert.notNull(longitude, "longitude는 null일 수 없습니다.");
        Assert.notNull(point, "point는 null일 수 없습니다.");
        Assert.hasLength(gardenName, "gardenName는 null이거나 빈 값일 수 없습니다.");
        Assert.notNull(gardenFacilities, "gardenFacilities은 null일 수 없습니다.");

        isNegativeReportedScore(reportedScore);
        hasSize(size);
        isMaxDescriptionLength(gardenDescription);

        if(!gardenType.name().contains("EVERY")) {
            isNegativePrice(price);
            validateDate(recruitStartDate, recruitEndDate);
        }

        this.gardenId = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.point = point;
        this.gardenName = gardenName;
        this.gardenType = gardenType;
        this.gardenStatus = gardenStatus;
        this.price = price;
        this.contact = isNull(contact);
        this.size = size;
        this.gardenDescription = isNull(gardenDescription);
        this.recruitStartDate = recruitStartDate;
        this.recruitEndDate = recruitEndDate;
        this.gardenFacilities = gardenFacilities;
        this.writerId = writerId;
        this.isDeleted = isDeleted;
        this.reportedScore = reportedScore;
        this.resourceHashId = makeResourceHashId();

    }

    public static Garden of(
        Long id,
        String address,
        Double latitude,
        Double longitude,
        Point point,
        String gardenName,
        GardenType gardenType,
        GardenStatus gardenStatus,
        String price,
        String contact,
        String size,
        String gardenDescription,
        String recruitStartDate,
        String recruitEndDate,
        String gardenFacilities,
        Long writerId,
        boolean isDeleted,
        int reportedScore
    ) {
        return new Garden(
            id,
            address,
            latitude,
            longitude,
            point,
            gardenName,
            gardenType,
            gardenStatus,
            price,
            contact,
            size,
            gardenDescription,
            recruitStartDate,
            recruitEndDate,
            gardenFacilities,
            writerId,
            isDeleted,
            reportedScore
        );
    }

    protected Garden(
        String address,
        Double latitude,
        Double longitude,
        Point point,
        String gardenName,
        GardenType gardenType,
        GardenStatus gardenStatus,
        String price,
        String contact,
        String size,
        String gardenDescription,
        String recruitStartDate,
        String recruitEndDate,
        String gardenFacilities,
        Long writerId,
        boolean isDeleted,
        int reportedScore) {

        Assert.hasLength(address, "address는 null이거나 빈 값일 수 없습니다.");
        Assert.notNull(latitude, "latitude는 null일 수 없습니다.");
        Assert.notNull(longitude, "longitude는 null일 수 없습니다.");
        Assert.notNull(point, "point는 null일 수 없습니다.");
        Assert.hasLength(gardenName, "gardenName는 null이거나 빈 값일 수 없습니다.");
        Assert.notNull(gardenFacilities, "gardenFacilities은 null일 수 없습니다.");

        isNegativeReportedScore(reportedScore);
        hasSize(size);
        isMaxDescriptionLength(gardenDescription);

        if(!gardenType.name().contains("EVERY")) {
            isNegativePrice(price);
            validateDate(recruitStartDate, recruitEndDate);
        }

        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.point = point;
        this.gardenName = gardenName;
        this.gardenType = gardenType;
        this.gardenStatus = gardenStatus;
        this.price = price;
        this.contact = isNull(contact);
        this.size = size;
        this.gardenDescription = isNull(gardenDescription);
        this.recruitStartDate = recruitStartDate;
        this.recruitEndDate = recruitEndDate;
        this.gardenFacilities = gardenFacilities;
        this.writerId = writerId;
        this.isDeleted = isDeleted;
        this.reportedScore = reportedScore;
        this.resourceHashId = makeResourceHashId();
    }

    public static Garden of(
        String address,
        Double latitude,
        Double longitude,
        Point point,
        String gardenName,
        GardenType gardenType,
        GardenStatus gardenStatus,
        String price,
        String contact,
        String size,
        String gardenDescription,
        LocalDate recruitStartDate,
        LocalDate recruitEndDate,
        String gardenFacilities,
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
            price,
            contact,
            size,
            gardenDescription,
            recruitStartDate.toString(),
            recruitEndDate.toString(),
            gardenFacilities,
            writerId,
            isDeleted,
            reportedScore
        );
    }

    public static Garden createGarden(
        String address,
        Double latitude,
        Double longitude,
        Point point,
        GardenType gardenType,
        String gardenName,
        GardenStatus gardenStatus,
        String price,
        String contact,
        String size,
        String gardenDescription,
        String recruitStartDate,
        String recruitEndDate,
        String gardenFacilities,
        Long writerId

    ) {
        return new Garden(
            address,
            latitude,
            longitude,
            point,
            gardenName,
            gardenType,
            gardenStatus,
            price,
            contact,
            size,
            gardenDescription,
            recruitStartDate,
            recruitEndDate,
            gardenFacilities,
            writerId,
            false,
            DEFAULT_REPORTED_SCORE
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

    private void hasSize(String size) {
        Assert.hasLength(size, "size는 null이거나 빈 값일 수 없습니다.");
    }

    private void isMaxDescriptionLength(String gardenDescription) {
        if (gardenDescription.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(String.format("gardenDescription은 최대 %s글자 입니다.", MAX_DESCRIPTION_LENGTH));
        }
    }

    public void validWriterId(Long requestMemberId) {
        if (!Objects.equals(writerId, requestMemberId)) {
            throw new IllegalArgumentException("텃밭 작성자가 아닙니다.");
        }
    }

    private void validateDate(String startDate, String endDate) {
        if (LocalDate.parse(endDate).isBefore(LocalDate.parse(startDate))) {
            throw new IllegalArgumentException("종료일은 시작일보다 이전일 수 없습니다.");
        }
    }

    public void updateGarden(GardenUpdateDomainRequest request) {
        Assert.hasLength(request.address(), "address는 null이거나 빈 값일 수 없습니다.");
        Assert.notNull(request.latitude(), "latitude는 null일 수 없습니다.");
        Assert.notNull(request.longitude(), "longitude는 null일 수 없습니다.");
        Assert.hasLength(request.gardenName(), "gardenName는 null이거나 빈 값일 수 없습니다.");

        hasSize(request.size());
        isMaxDescriptionLength(request.gardenDescription());
        validWriterId(request.writerId());

        if(!request.gardenType().name().contains("EVERY")) {
            isNegativePrice(request.price());
            validateDate(request.recruitStartDate(), request.recruitEndDate());
        }

        gardenName = request.gardenName();
        price = request.price();
        size = request.size();
        gardenStatus = request.gardenStatus();
        gardenType = request.gardenType();
        contact = request.contact();
        address = request.address();
        latitude = request.latitude();
        longitude = request.longitude();
        gardenFacilities = request.gardenFacilities();
        gardenDescription = request.gardenDescription();
        recruitStartDate = request.recruitStartDate();
        recruitEndDate = request.recruitEndDate();
        resourceHashId = makeResourceHashId();
    }

    private String isNull(String field) {
        if (field == null) {
            return DEFAULT_FIELD;
        }
        return field;
    }

    public int makeResourceHashId() {
        return Objects.hash(latitude, longitude, gardenName);
    }

}
