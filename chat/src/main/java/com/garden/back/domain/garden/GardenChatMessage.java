package com.garden.back.domain.garden;

import com.garden.back.domain.MessageType;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@Table(name = "garden_chat_messages")
@Entity
public class GardenChatMessage {

    protected GardenChatMessage() {
    }

    @Column(name = "chat_message_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private GardenChatRoom chatRoom;

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

    private GardenChatMessage(
            GardenChatRoom chatRoom,
            Long memberId,
            String contents,
            boolean readOrNot,
            MessageType messageType
    ) {
        Assert.notNull(chatRoom, "Chat Room은 null일 수 없습니다.");
        Assert.isTrue(memberId >= 0, "유저 아이디는 0보다 작을 수 없습니다.");
        Assert.notNull(contents, "메세지 내용은 null일 수 없습니다.");

        this.chatRoom = chatRoom;
        this.memberId = memberId;
        this.contents = contents;
        this.readOrNot = readOrNot;
        this.messageType = messageType;
    }

    public static GardenChatMessage of(
            GardenChatRoom chatRoom,
            Long memberId,
            String contents,
            boolean readOrNot,
            MessageType messageType
    ) {
        return new GardenChatMessage(
                chatRoom,
                memberId,
                contents,
                readOrNot,
                messageType
        );

    }

}
