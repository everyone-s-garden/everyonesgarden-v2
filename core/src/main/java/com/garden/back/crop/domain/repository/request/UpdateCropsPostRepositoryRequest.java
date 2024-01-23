package com.garden.back.crop.domain.repository.request;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.CropPost;
import com.garden.back.crop.domain.TradeStatus;
import com.garden.back.crop.domain.TradeType;

import java.util.List;

public record UpdateCropsPostRepositoryRequest(
    String title,
    String content,
    CropCategory cropCategory,
    Integer price,
    Boolean priceProposal,
    TradeType tradeType,
    List<String> deletedImages,
    List<String> addedImages,
    Long loginUserId,
    CropPost crop,
    TradeStatus tradeStatus,
    Long memberAddressId
) {}
