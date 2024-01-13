package com.garden.back.crop.domain.repository.request;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.TradeType;

public record FindAllCropsPostRepositoryRequest(
    Integer offset,
    Integer limit,
    String searchContent,
    TradeType tradeType,
    CropCategory cropCategory,
    OrderBy orderBy
) {
    public enum OrderBy {
        RECENT_DATE, BOOKMARK_COUNT, OLDER_DATE, LOWER_PRICE, HIGHER_PRICE
    }
}
