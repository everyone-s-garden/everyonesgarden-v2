package com.graden.back.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Table(name = "chat_messages")
@Entity
public class ChatMessage {

    protected ChatMessage() {
    }

    @Column(name = "chat_message_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    @Column(name = "chat_room_id", nullable = false)
    private Long chatRoomId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "chat_contents", columnDefinition = "TEXT")
    private String contents;

    @Column(name = "read_or_not", nullable = false)
    private boolean readOrNot;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "message_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MessageType messageType;

}
