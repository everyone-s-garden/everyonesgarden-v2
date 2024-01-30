package com.garden.back.garden.repository.chatreport;

import com.garden.back.garden.domain.GardenChatReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GardenChatReportJpaRepository extends JpaRepository<GardenChatReport, Long> {
    boolean existsByReporterIdAndRoomId(Long reporterId, Long roomId);

}
