package com.garden.back.garden.domain;

import com.garden.back.garden.domain.dto.GardenChatReportDomainParam;
import com.garden.back.global.ChatReportType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "garden_chat_reports")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GardenChatReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_report_id")
    private Long chatReportId;

    @Column(name = "reporter_id")
    private Long reporterId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "report_type")
    @Enumerated(EnumType.STRING)
    private ChatReportType reportType;

    @Column(name = "report_contents")
    private String reportContents;

    private GardenChatReport(
            Long reporterId,
            Long roomId,
            ChatReportType commentReportType,
            String reportContents) {
        this.reporterId = reporterId;
        this.roomId = roomId;
        this.reportType = commentReportType;
        this.reportContents = reportContents;
    }

    public static GardenChatReport create(GardenChatReportDomainParam param) {
        return new GardenChatReport(
                param.reporterId(),
                param.roomId(),
                param.commentReportType(),
                param.reportContents());
    }
}
