package com.garden.back.crop.infra.wiki;

import com.garden.back.crop.infra.MonthlyRecommendedCropsFetcher;
import com.garden.back.crop.infra.MonthlyRecommendedCropsInfraResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WikiRecommendedCropsImpl implements MonthlyRecommendedCropsFetcher {

    private final MonthlyWikiRecommendedCropsRepository monthlyWikiRecommendedCropsRepository;

    public WikiRecommendedCropsImpl(MonthlyWikiRecommendedCropsRepository monthlyWikiRecommendedCropsRepository) {
        this.monthlyWikiRecommendedCropsRepository = monthlyWikiRecommendedCropsRepository;
    }

    @Override
    public MonthlyRecommendedCropsInfraResponse getMonthlyRecommendedCrops() {
        int january = 1;
        int december = 12;

        Map<Integer, List<MonthlyRecommendedCropsInfraResponse.CropsResponse.CropInfo>> monthlyCropInfos = new HashMap<>();

        for (int month = january; month <= december; month++) {
            List<MonthlyRecommendedCropsInfraResponse.CropsResponse.CropInfo> cropInfos = monthlyWikiRecommendedCropsRepository.findByMonth(month).stream()
                .map(crop -> new MonthlyRecommendedCropsInfraResponse.CropsResponse.CropInfo(crop.name(), crop.description(), crop.url()))
                .toList();
            monthlyCropInfos.put(month, cropInfos);
        }
        return MonthlyRecommendedCropsInfraResponse.from(monthlyCropInfos);
    }

}
