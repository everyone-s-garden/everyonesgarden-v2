package com.graden.back.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "chat_rooms")
@Entity
public class ChatRoom {

    protected ChatRoom() {
    }

    @Column(name = "chat_room_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomInfo> chatRoomInfos = new ArrayList<>();

    public void addChatRoomInfos(ChatRoomInfo chatRoomInfo) {
        chatRoomInfos.add(chatRoomInfo);
    }
}
