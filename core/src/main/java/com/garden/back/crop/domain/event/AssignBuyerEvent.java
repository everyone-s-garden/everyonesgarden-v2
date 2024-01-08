package com.garden.back.crop.domain.event;

import com.garden.back.crop.domain.CropPost;

public record AssignBuyerEvent(
    CropPost cropPost
) {
}
