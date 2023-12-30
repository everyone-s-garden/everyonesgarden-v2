package com.graden.back.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "chat_images")
@Entity
public class ChatImage {

    protected ChatImage() {
    }

    @Column(name = "chat_image_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatImageId;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id")
    private ChatMessage chatMessage;

}
