package com.garden.back.domain.crop;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "chat_rooms")
@Entity
public class CropChatRoom {

    protected CropChatRoom() {
    }

    @Column(name = "chat_room_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @OneToMany(mappedBy = "chatRoom")
    private List<CropChatRoomInfo> chatRoomInfos = new ArrayList<>();

    public void addChatRoomInfos(CropChatRoomInfo chatRoomInfo) {
        chatRoomInfos.add(chatRoomInfo);
    }

    public static CropChatRoom of() {
        return new CropChatRoom();
    }
}
