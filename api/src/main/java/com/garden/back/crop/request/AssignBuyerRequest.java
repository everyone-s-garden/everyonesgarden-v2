package com.garden.back.crop.request;

import com.garden.back.crop.service.request.AssignBuyerServiceRequest;
import jakarta.validation.constraints.NotNull;

public record AssignBuyerRequest(
    @NotNull(message = "구매자의 id를 입력해 주세요.")
    Long buyerId
) {
    public AssignBuyerServiceRequest toServiceDto() {
        return new AssignBuyerServiceRequest(buyerId);
    }
}
