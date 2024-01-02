package com.garden.back.domain.garden;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "garden_chat_rooms")
@Entity
public class GardenChatRoom {

    protected GardenChatRoom() {
    }

    @Column(name = "chat_room_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @OneToMany(mappedBy = "chatRoom")
    private List<GardenChatRoomInfo> chatRoomInfos = new ArrayList<>();

    public void addChatRoomInfos(GardenChatRoomInfo chatRoomInfo) {
        chatRoomInfos.add(chatRoomInfo);
    }

    public static GardenChatRoom of() {
        return new GardenChatRoom();
    }
}
