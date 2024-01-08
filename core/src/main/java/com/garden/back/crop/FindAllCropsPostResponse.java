package com.garden.back.crop;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.TradeStatus;
import com.garden.back.crop.domain.TradeType;

import java.time.LocalDate;
import java.util.List;

public record FindAllCropsPostResponse(
    List<CropsInfo> cropsInfos
) {
    public record CropsInfo(
        Long cropsPostId,
        String title,
        Integer price,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate createdDate,
        TradeType tradeType,
        boolean priceProposal,
        TradeStatus tradeStatus,
        CropCategory cropCategory,
        Long bookmarkCount
    ) {}
}
