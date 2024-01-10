package com.garden.back.garden.repository.mymanagedgarden.entity;

import com.garden.back.garden.domain.MyManagedGarden;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "my_managed_gardens")
public class MyManagedGardenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_managed_garden_id")
    private Long myManagedGardenId;

    @Column(name = "use_start_date")
    private LocalDate useStartDate;

    @Column(name = "use_end_date")
    private LocalDate useEndDate;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "garden_id")
    private Long gardenId;

    public MyManagedGarden toModel() {
        return MyManagedGarden.of(
                myManagedGardenId,
                useStartDate,
                useEndDate,
                memberId,
                imageUrl,
                gardenId
        );
    }

    public static MyManagedGardenEntity from(
            MyManagedGarden myManagedGarden
    ) {
        MyManagedGardenEntity myManagedGardenEntity = new MyManagedGardenEntity();
        myManagedGardenEntity.myManagedGardenId = myManagedGarden.getMyManagedGardenId();
        myManagedGardenEntity.useStartDate = myManagedGarden.getUseStartDate();
        myManagedGardenEntity.useEndDate = myManagedGarden.getUseEndDate();
        myManagedGardenEntity.memberId = myManagedGarden.getMemberId();
        myManagedGardenEntity.imageUrl = myManagedGarden.getImageUrl();
        myManagedGardenEntity.gardenId = myManagedGarden.getGardenId();

        return myManagedGardenEntity;
    }
}
