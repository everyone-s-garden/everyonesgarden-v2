package com.garden.back.crop.request;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.TradeType;
import com.garden.back.crop.service.request.CreateCropsPostServiceRequest;
import com.garden.back.global.validation.EnumValue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

public record CropsPostCreateRequest(
    @NotBlank(message = "제목을 입력해주세요") @Length(min = 1, max = 255, message = "1~255 글자 사이의 제목을 입력해주세요.")
    String title,

    @NotBlank(message = "내용을 입력해주세요")
    String content,

    @EnumValue(enumClass = CropCategory.class, message = "GRAIN, VEGETABLE, FRUIT, BEAN, ETC 중에서 한개만 입력이 가능합니다.")
    String cropsCategory,

    @NotNull(message = "가격을 입력해주세요")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    Integer price,

    @NotNull(message = "가격 제안 여부를 선택해주세요")
    Boolean priceProposal,

    @EnumValue(enumClass = TradeType.class, message = "DIRECT_TRADE, DELIVERY_TRADE, ALL 중에서 한개만 입력이 가능합니다.")
    String tradeType,

    @Positive(message = "사용자의 주소는 양수만 입력 가능합니다.")
    Long memberAddressId
) {
    public CreateCropsPostServiceRequest toServiceRequest(List<MultipartFile> images) {
        if (images == null) {
            images = Collections.emptyList();
        }

        if (images.size() > 10) {
            throw new IllegalArgumentException("게시글 한개당 10장의 이미지만 등록할 수 있습니다.");
        }

        return new CreateCropsPostServiceRequest(
            content,
            title,
            CropCategory.valueOf(cropsCategory),
            price,
            priceProposal,
            TradeType.valueOf(tradeType),
            images,
            memberAddressId
        );
    }
}