package com.garden.back.garden.domain;

import com.garden.back.garden.domain.dto.MyManagedGardenCreateDomainRequest;
import com.garden.back.garden.domain.dto.MyManagedGardenUpdateDomainRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="my_managed_gardens")
public class MyManagedGarden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_managed_garden_id")
    private Long myManagedGardenId;

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

    protected MyManagedGarden(
        LocalDate useStartDate,
        LocalDate useEndDate,
        Long memberId,
        String imageUrl,
        Long gardenId
    ){
        this.useStartDate = useStartDate;
        this.useEndDate = useEndDate;
        this.memberId = memberId;
        this.imageUrl = imageUrl;
        this.gardenId =gardenId;
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
        validWriterId(request.memberId());
        validateDate(request.useStartDate(), request.useEndDate());

        useStartDate = request.useStartDate();
        useEndDate = request.useEndDate();
        imageUrl = request.myManagedGardenImageUrl();
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

}
