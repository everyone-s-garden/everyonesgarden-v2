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

    @Column(name = "my_managed_garden_name", nullable = false)
    private String myManagedGardenName;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public MyManagedGarden toModel() {
        return MyManagedGarden.of(
            myManagedGardenId,
            myManagedGardenName,
            createdAt,
            memberId,
            imageUrl,
            description
        );
    }

    public static MyManagedGardenEntity from(
        MyManagedGarden myManagedGarden
    ) {
        MyManagedGardenEntity myManagedGardenEntity = new MyManagedGardenEntity();
        myManagedGardenEntity.myManagedGardenId = myManagedGarden.getMyManagedGardenId();
        myManagedGardenEntity.myManagedGardenName = myManagedGarden.getMyManagedGardenName();
        myManagedGardenEntity.createdAt = myManagedGarden.getCreatedAt();
        myManagedGardenEntity.memberId = myManagedGarden.getMemberId();
        myManagedGardenEntity.imageUrl = myManagedGarden.getImageUrl();
        myManagedGardenEntity.description = myManagedGarden.getDescription();

        return myManagedGardenEntity;
    }
}
