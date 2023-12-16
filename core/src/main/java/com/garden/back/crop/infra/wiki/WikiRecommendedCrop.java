package com.garden.back.crop.infra.wiki;

import java.util.List;

public record WikiRecommendedCrop(
    String name,
    String description,
    List<Integer> recommendedMonth,
    String url
) {
}
