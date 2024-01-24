package com.garden.back.crop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "crop_chat_rooms")
@Entity
public class CropChatRoom {

    @Column(name = "chat_room_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @OneToMany(mappedBy = "chatRoom")
    private List<CropChatRoomInfo> chatRoomInfos = new ArrayList<>();


    protected CropChatRoom() {
    }

    private CropChatRoom(
            Long chatRoomId
    ){
        Assert.isTrue(chatRoomId > 0, "채팅방 아이디는 양수여야 합니다.");
        this.chatRoomId = chatRoomId;
    }

    public static CropChatRoom of(
            Long chatRoomId
    ) {
        return new CropChatRoom(chatRoomId);
    }
    public void addChatRoomInfos(CropChatRoomInfo chatRoomInfo) {
        chatRoomInfos.add(chatRoomInfo);
    }

}
