package com.garden.back.garden.domain;

import com.garden.back.garden.domain.dto.MyManagedGardenCreateDomainRequest;
import com.garden.back.garden.domain.dto.MyManagedGardenUpdateDomainRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.util.Assert;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyManagedGarden {

    private Long myManagedGardenId;
    private LocalDate useStartDate;
    private LocalDate useEndDate;
    private Long memberId;
    private String imageUrl;
    private Long gardenId;

    protected MyManagedGarden(
            Long myManagedGardenId,
            LocalDate useStartDate,
            LocalDate useEndDate,
            Long memberId,
            String imageUrl,
            Long gardenId
    ) {
        Assert.isTrue(myManagedGardenId > 0 , "myManagedGarden Id는 0보다 커야 합니다.");
        Assert.isTrue(memberId > 0 , "member Id는 0보다 커야 합니다.");
        Assert.isTrue(gardenId > 0 , "garden Id는 0보다 커야 합니다.");

        validateDate(useStartDate,useEndDate);

        this.myManagedGardenId = myManagedGardenId;
        this.useStartDate = useStartDate;
        this.useEndDate = useEndDate;
        this.memberId = memberId;
        this.imageUrl = imageUrl;
        this.gardenId = gardenId;
    }

    public static MyManagedGarden of(
            Long myManagedGardenId,
            LocalDate useStartDate,
            LocalDate useEndDate,
            Long memberId,
            String imageUrl,
            Long gardenId
    ) {
        return new MyManagedGarden(
                myManagedGardenId,
                useStartDate,
                useEndDate,
                memberId,
                imageUrl,
                gardenId
        );
    }

    protected MyManagedGarden(
            LocalDate useStartDate,
            LocalDate useEndDate,
            Long memberId,
            String imageUrl,
            Long gardenId
    ) {
        Assert.isTrue(memberId > 0 , "member Id는 0보다 커야 합니다.");
        Assert.isTrue(gardenId > 0 , "garden Id는 0보다 커야 합니다.");

        validateDate(useStartDate,useEndDate);

        this.useStartDate = useStartDate;
        this.useEndDate = useEndDate;
        this.memberId = memberId;
        this.imageUrl = imageUrl;
        this.gardenId = gardenId;
    }

    public static MyManagedGarden of(
            LocalDate useStartDate,
            LocalDate useEndDate,
            Long memberId,
            String imageUrl,
            Long gardenId
    ) {
        return new MyManagedGarden(
                useStartDate,
                useEndDate,
                memberId,
                imageUrl,
                gardenId
        );
    }

    public static MyManagedGarden to(
            MyManagedGardenCreateDomainRequest request
    ) {
        return new MyManagedGarden(
                request.useStartDate(),
                request.useEndDate(),
                request.memberId(),
                request.myManagedGardenImageUrl(),
                request.memberId()
        );
    }

    public void update(MyManagedGardenUpdateDomainRequest request) {
        Assert.isTrue(gardenId > 0 , "garden id는 0보다 커야 한다.");
        validWriterId(request.memberId());
        validateDate(request.useStartDate(), request.useEndDate());

        updateImageIfPresent(request.myManagedGardenImageUrl());

        useStartDate = request.useStartDate();
        useEndDate = request.useEndDate();
        gardenId = request.gardenId();
    }

    private void validWriterId(Long requestMemberId) {
        if (!Objects.equals(memberId, requestMemberId)) {
            throw new IllegalArgumentException("텃밭 작성자가 아닙니다.");
        }
    }

    private void validateDate(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("종료일은 시작일보다 이전일 수 없습니다.");
        }
    }

    private void updateImageIfPresent(String newImageUrl) {
        if (!newImageUrl.isEmpty()) {
            imageUrl = newImageUrl;
        }
    }

}
