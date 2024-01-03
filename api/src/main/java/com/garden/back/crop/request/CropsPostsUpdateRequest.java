package com.garden.back.crop.request;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.TradeType;
import com.garden.back.crop.service.request.UpdateCropsPostServiceRequest;
import com.garden.back.global.validation.EnumValue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CropsPostsUpdateRequest(
    @NotBlank(message = "제목을 입력해주세요") @Length(min = 1, max = 255, message = "1~255 글자 사이의 제목을 입력해주세요.")
    String title,

    @NotBlank(message = "내용을 입력해주세요")
    String content,

    @EnumValue(enumClass = CropCategory.class, message = "GRAIN, VEGETABLE, FRUIT, BEAN 중에서 한개만 입력이 가능합니다.")
    String cropsCategory,

    @NotNull(message = "가격을 입력해주세요")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    Integer price,

    @NotNull(message = "가격 제안 여부를 선택해주세요")
    Boolean priceProposal,

    @EnumValue(enumClass = TradeType.class, message = "DIRECT_TRADE, DELIVERY_TRADE 중에서 한개만 입력이 가능합니다.")
    String tradeType,

    List<String> deleteImages,

    boolean reservationStatus
) {
    public UpdateCropsPostServiceRequest toServiceRequest(List<MultipartFile> images) {
        return new UpdateCropsPostServiceRequest(
            title,
            content,
            CropCategory.valueOf(cropsCategory),
            price,
            priceProposal,
            TradeType.valueOf(tradeType),
            reservationStatus,
            deleteImages,
            images
        );
    }
}
