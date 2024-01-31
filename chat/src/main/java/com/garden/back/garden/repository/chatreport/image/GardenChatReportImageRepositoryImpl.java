package com.garden.back.garden.repository.chatreport.image;

import com.garden.back.garden.domain.GardenChatReportImage;
import org.springframework.stereotype.Repository;

@Repository
public class GardenChatReportImageRepositoryImpl implements GardenChatReportImageRepository{

    private final GardenChatReportImageJpaRepository gardenChatReportImageJpaRepository;

    public GardenChatReportImageRepositoryImpl(GardenChatReportImageJpaRepository gardenChatReportImageJpaRepository) {
        this.gardenChatReportImageJpaRepository = gardenChatReportImageJpaRepository;
    }

    @Override
    public GardenChatReportImage save(GardenChatReportImage gardenChatReportImage) {
        return gardenChatReportImageJpaRepository.save(gardenChatReportImage);
    }
}
