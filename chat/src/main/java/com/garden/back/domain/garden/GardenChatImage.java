package com.graden.back.domain.garden;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "garden_chat_images")
@Entity
public class GardenChatImage {

    protected GardenChatImage() {
    }

    @Column(name = "chat_image_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatImageId;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id")
    private GardenChatMessage chatMessage;

}
