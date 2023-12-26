package com.garden.back.garden.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

}
