package com.garden.back.garden.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "garden_chat_report_images")
public class GardenChatReportImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_image_id")
    private Long reportId;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    GardenChatReport gardenChatReport;

    private GardenChatReportImage(
            String imageUrl,
            GardenChatReport gardenChatReport
    ) {
        this.imageUrl = imageUrl;
        this.gardenChatReport = gardenChatReport;
    }

    public static GardenChatReportImage of(
            String imageUrl,
            GardenChatReport gardenChatReport
    ) {
        return new GardenChatReportImage(
                imageUrl,
                gardenChatReport
        );
    }

}