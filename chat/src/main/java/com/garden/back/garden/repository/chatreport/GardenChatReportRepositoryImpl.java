package com.garden.back.garden.repository.chatreport;

import com.garden.back.garden.domain.GardenChatReport;
import org.springframework.stereotype.Repository;

@Repository
public class GardenChatReportRepositoryImpl implements GardenChatReportRepository{

    private final GardenChatReportJpaRepository gardenChatReportJpaRepository;

    public GardenChatReportRepositoryImpl(GardenChatReportJpaRepository gardenChatReportJpaRepository) {
        this.gardenChatReportJpaRepository = gardenChatReportJpaRepository;
    }

    @Override
    public boolean existsByReporterIdAndRoomId(Long reporterId, Long roomId) {
        return gardenChatReportJpaRepository.existsByReporterIdAndRoomId(
                reporterId, roomId
        );
    }

    @Override
    public GardenChatReport save(GardenChatReport gardenChatReport) {
        return gardenChatReportJpaRepository.save(gardenChatReport);
    }
}
