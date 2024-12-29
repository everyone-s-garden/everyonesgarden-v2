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

    private static final String DEFAULT_DESCRIPTION = "";

    private String myManagedGardenName;
    private Long myManagedGardenId;
    private LocalDate createdAt;
    private Long memberId;
    private String imageUrl;
    private String description;

    protected MyManagedGarden(
        Long myManagedGardenId,
        String myManagedGardenName,
        LocalDate createdAt,
        Long memberId,
        String imageUrl,
        String description
    ) {
        Assert.isTrue(!myManagedGardenName.isEmpty(), "텃밭 이름은 공백일 수 없습니다.");
        Assert.isTrue(myManagedGardenId > 0, "myManagedGarden Id는 0보다 커야 합니다.");
        Assert.isTrue(memberId > 0, "member Id는 0보다 커야 합니다.");

        this.myManagedGardenName = myManagedGardenName;
        this.myManagedGardenId = myManagedGardenId;
        this.createdAt = createdAt;
        this.memberId = memberId;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public static MyManagedGarden of(
        Long myManagedGardenId,
        String myManagedGardenName,
        LocalDate createdAt,
        Long memberId,
        String imageUrl,
        String description
    ) {
        return new MyManagedGarden(
            myManagedGardenId,
            myManagedGardenName,
            createdAt,
            memberId,
            imageUrl,
            description
        );
    }

    protected MyManagedGarden(
        String myManagedGardenName,
        LocalDate createdAt,
        Long memberId,
        String imageUrl
    ) {
        Assert.isTrue(memberId > 0, "member Id는 0보다 커야 합니다.");

        this.myManagedGardenName = myManagedGardenName;
        this.createdAt = createdAt;
        this.memberId = memberId;
        this.imageUrl = imageUrl;
        this.description = DEFAULT_DESCRIPTION;
    }

    protected MyManagedGarden(
        String myManagedGardenName,
        LocalDate createdAt,
        Long memberId,
        String imageUrl,
        String description
    ) {
        Assert.isTrue(memberId > 0, "member Id는 0보다 커야 합니다.");

        this.myManagedGardenName = myManagedGardenName;
        this.createdAt = createdAt;
        this.memberId = memberId;
        this.imageUrl = imageUrl;
        this.description = description.isBlank() ? DEFAULT_DESCRIPTION : description;
    }

    public static MyManagedGarden of(
        String myManagedGardenName,
        LocalDate createdAt,
        Long memberId,
        String imageUrl
    ) {
        return new MyManagedGarden(
            myManagedGardenName,
            createdAt,
            memberId,
            imageUrl
        );
    }

    public static MyManagedGarden to(
        MyManagedGardenCreateDomainRequest request
    ) {
        return new MyManagedGarden(
            request.myManagedGardenName(),
            request.createdAt(),
            request.memberId(),
            request.myManagedGardenImageUrl(),
            request.description()
        );
    }

    public void update(MyManagedGardenUpdateDomainRequest request) {
        validWriterId(request.memberId());
        validGardenName(request.myManagedGardenName());
        updateImageIfPresent(request.myManagedGardenImageUrl());


        myManagedGardenName = request.myManagedGardenName();
        createdAt = request.createdAt();
        description = request.description();
    }

    private void validWriterId(Long requestMemberId) {
        if (!Objects.equals(memberId, requestMemberId)) {
            throw new IllegalArgumentException("텃밭 작성자가 아닙니다.");
        }
    }

    private void validGardenName(String myManagedGardenName) {
        if (!Objects.nonNull(myManagedGardenName)) {
            throw new IllegalArgumentException("텃밭 이름은 빈값일 수 없습니다.");
        }
    }

    private void updateImageIfPresent(String newImageUrl) {
        if (newImageUrl != null) {
            imageUrl = newImageUrl;
        }
    }

}
