package com.garden.back.domain.garden;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "garden_chat_images")
@Entity
public class GardenChatImage {

    protected GardenChatImage() {
    }

    @Column(name = "chat_image_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private GardenChatRoom chatRoom;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "chat_image_")
    private List<String> imageUrls = new ArrayList<>();

    @Column(name = "read_or_not", nullable = false)
    private boolean readOrNot;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

}
