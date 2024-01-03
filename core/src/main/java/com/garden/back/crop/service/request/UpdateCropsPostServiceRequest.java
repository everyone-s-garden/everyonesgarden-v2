package com.garden.back.crop.service.request;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.TradeType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record UpdateCropsPostServiceRequest(
    String title,
    String content,
    CropCategory cropCategory,
    Integer price,
    Boolean priceProposal,
    TradeType tradeType,
    boolean reservationStatus,
    List<String> deletedImages,
    List<MultipartFile> images
) {
}
