package com.garden.back.garden.domain;

import com.garden.back.garden.domain.dto.GardenChatReportDomainParam;
import com.garden.back.garden.domain.dto.GardenChatReportEvent;
import com.garden.back.global.ChatReportType;
import com.garden.back.global.event.Events;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Table(name = "garden_chat_reports")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GardenChatReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_report_id")
    private Long chatReportId;

    @Column(name = "reported_member_id")
    private Long reportedMemberId;

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
        Long reportedMemberId,
        Long reporterId,
        Long roomId,
        ChatReportType commentReportType,
        String reportContents) {
        Assert.notNull(reportedMemberId,"reportedMemberId는 null이어서는 안됩니다.");
        Assert.notNull(reporterId,"reporterId는 null이어서는 안됩니다.");
        Assert.notNull(roomId,"roomId는 null이어서는 안됩니다.");

        Assert.isTrue(reportedMemberId > 0, "reportedMemberId는 양수여야 합니다.");
        Assert.isTrue(reporterId > 0, "reporterId는 양수여야 합니다.");
        Assert.isTrue(roomId > 0, "roomId는 양수여야 합니다.");

        this.reportedMemberId = reportedMemberId;
        this.reporterId = reporterId;
        this.roomId = roomId;
        this.reportType = commentReportType;
        this.reportContents = reportContents;
        Events.raise(
            GardenChatReportEvent.toChatReportEvent(this)
        );
    }

    public static GardenChatReport create(GardenChatReportDomainParam param) {
        return new GardenChatReport(
            param.reportedMemberId(),
            param.reporterId(),
            param.roomId(),
            param.commentReportType(),
            param.reportContents());
    }

    public int getReportScore() {
        return reportType.getScore();
    }
}
