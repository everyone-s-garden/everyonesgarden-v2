package com.garden.back.crop.service.request;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.TradeType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreateCropsPostServiceRequest(
    String content,
    String title,
    CropCategory cropsCategory,
    Integer price,
    boolean priceProposal,
    TradeType tradeType,
    List<MultipartFile> images
) {
}
