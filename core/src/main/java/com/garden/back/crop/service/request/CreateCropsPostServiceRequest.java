package com.garden.back.crop.service.request;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.CropPost;
import com.garden.back.crop.domain.TradeType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreateCropsPostServiceRequest(
    String content,
    String title,
    CropCategory cropCategory,
    Integer price,
    boolean priceProposal,
    TradeType tradeType,
    List<MultipartFile> images,
    Long memberAddressId
) {
    public CropPost toEntity(List<String> images, Long loginUserId) {
        return CropPost.create(content, title, cropCategory, price, priceProposal, tradeType, images, loginUserId, memberAddressId);
    }
}