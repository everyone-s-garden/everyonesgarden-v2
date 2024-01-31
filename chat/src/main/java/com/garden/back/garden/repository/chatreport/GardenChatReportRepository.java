package com.garden.back.garden.repository.chatreport;

import com.garden.back.garden.domain.GardenChatReport;

public interface GardenChatReportRepository  {
    boolean existsByReporterIdAndRoomId(Long reporterId, Long roomId);

    GardenChatReport save(GardenChatReport gardenChatReport);

}
