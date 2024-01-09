package com.garden.back.crop.domain;

import com.garden.back.crop.domain.event.AssignBuyerEvent;
import com.garden.back.global.event.Events;
import com.garden.back.global.jpa.BaseTimeEntity;
import com.mysema.commons.lang.Assert;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "crop_posts")
public class CropPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "crop_category", nullable = false)
    private CropCategory cropCategory;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "price_proposal", nullable = false)
    private Boolean priceProposal;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_type", nullable = false)
    private TradeType tradeType;

    @Column(name = "crop_post_author_id", nullable = false)
    private Long cropPostAuthorId;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "crop")
    private Set<CropImage> cropImages;

    @Column(name = "trade_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TradeStatus tradeStatus;

    @Column(name = "bookmark_count", nullable = false)
    private Long bookMarkCount;

    @Column(name = "buyer_id", nullable = true)
    private Long buyerId;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Transient
    static final int APPROVE_IMAGE_COUNT = 10;

    private CropPost(
        String content,
        String title,
        CropCategory cropCategory,
        Integer price,
        Boolean priceProposal,
        TradeType tradeType,
        List<String> cropUrls,
        Long cropPostAuthorId
    ) {
        Assert.notNull(content, "content를 작성해주세요.");
        Assert.notNull(title, "제목을 작성해주세요.");
        Assert.notNull(cropCategory, "작물 종류를 작성해주세요.");
        Assert.notNull(price, "가격을 작성해주세요.");
        Assert.notNull(priceProposal, "가격 제안이 가능한지 여부를 작성해주세요.");
        Assert.notNull(tradeType, "거래 종류를 입력해 주세요.");
        Assert.notNull(cropPostAuthorId, "게시글을 작성한 이용자의 id를 입력해주세요.");

        this.createdDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        this.bookMarkCount = 0L;
        this.tradeStatus = TradeStatus.TRADING;
        this.version = 0L;
        this.content = content;
        this.title = title;
        this.cropCategory = cropCategory;
        this.price = price;
        this.priceProposal = priceProposal;
        this.tradeType = tradeType;
        this.cropImages = cropUrls.stream()
            .map(cropUrl -> CropImage.create(cropUrl, this))
            .collect(Collectors.toSet());
        this.cropPostAuthorId = cropPostAuthorId;
    }

    public static CropPost create(
        String content,
        String title,
        CropCategory cropCategory,
        Integer price,
        Boolean priceProposal,
        TradeType tradeType,
        List<String> cropUrls,
        Long loginUserId
    ) {
        return new CropPost(content, title, cropCategory, price, priceProposal, tradeType, cropUrls, loginUserId);
    }

    public void validateUpdatable(Integer addedImageCount, Integer deletedImageCount) {
        final int totalSize = addedImageCount + this.getCropImages().size() - deletedImageCount;

        if (totalSize > APPROVE_IMAGE_COUNT) {
            throw new IllegalArgumentException("게시글 1개에는 10개의 이미지만 등록할 수 있습니다.");
        }
    }

    public void update(
        String title,
        String content,
        CropCategory cropCategory,
        Integer price,
        boolean priceProposal,
        TradeType tradeType,
        List<String> addedImages,
        List<String> deletedImages,
        Long cropPostAuthorId,
        TradeStatus tradeStatus
    ) {
        Assert.notNull(content, "content를 작성해주세요.");
        Assert.notNull(title, "제목을 작성해주세요.");
        Assert.notNull(cropCategory, "작물 종류를 작성해주세요.");
        Assert.notNull(price, "가격을 작성해주세요.");
        Assert.notNull(priceProposal, "가격 제안이 가능한지 여부를 작성해주세요.");
        Assert.notNull(tradeType, "거래 종류를 입력해 주세요.");
        Assert.notNull(cropPostAuthorId, "게시글을 작성한 이용자의 id를 입력해주세요.");
        Assert.notNull(tradeStatus, "작물의 거래 상태를 표기해 주세요.");

        if (!this.cropPostAuthorId.equals(cropPostAuthorId)) {
            throw new IllegalArgumentException("자신이 작성한 작물 게시물만 수정이 가능합니다.");
        }

        validateUpdatable(addedImages.size(), deletedImages.size());

        addedImages.stream()
            .map(url -> CropImage.create(url, this))
            .forEach(this.cropImages::add);

        deletedImages.forEach(url ->
            cropImages.removeIf(cropImage -> cropImage.hasUrl(url))
        );

        this.title = title;
        this.content = content;
        this.cropCategory = cropCategory;
        this.price = price;
        this.priceProposal = priceProposal;
        this.tradeStatus = tradeStatus;
    }

    public void increaseBookmarkCount() {
        this.bookMarkCount++;
    }

    public void decreaseBookmarkCount() {
        if (bookMarkCount == 0L) {
            throw new IllegalStateException("북마크의 개수가 0보다 아래일 수 없습니다.");
        }
        this.bookMarkCount--;
    }

    public void assignBuyer(Long buyerId) {
        if (!this.tradeStatus.equals(TradeStatus.TRADED)) {
            throw new IllegalArgumentException("거래 완료가 되지 않은 작물 게시글입니다.");
        }
        this.buyerId = buyerId;
        Events.raise(new AssignBuyerEvent(this)); //TODO: AFTER COMMIT 옵션으로 이벤트 받아서 구매한 사람, 판매한 사람 양쪽에 리뷰 쓰라고 알람 보내기  NOSONAR
    }
}
