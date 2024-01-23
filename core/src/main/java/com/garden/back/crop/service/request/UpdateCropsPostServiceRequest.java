package com.garden.back.crop.service.request;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.CropPost;
import com.garden.back.crop.domain.TradeStatus;
import com.garden.back.crop.domain.TradeType;
import com.garden.back.crop.domain.repository.request.UpdateCropsPostRepositoryRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record UpdateCropsPostServiceRequest(
    String title,
    String content,
    CropCategory cropCategory,
    Integer price,
    Boolean priceProposal,
    TradeType tradeType,
    List<String> deletedImages,
    List<MultipartFile> images,
    TradeStatus tradeStatus,
    Long memberAddressId
) {

    public Integer getAddedImageSize() {
        return images.size();
    }

    public Integer getDeletedImageSize() {
        return deletedImages.size();
    }

    public UpdateCropsPostRepositoryRequest toRepositoryDto(CropPost cropPost, List<String> images, Long loginUserId) {
        return new UpdateCropsPostRepositoryRequest(title, content, cropCategory, price, priceProposal, tradeType, deletedImages, images, loginUserId, cropPost, tradeStatus, memberAddressId);
    }
}
