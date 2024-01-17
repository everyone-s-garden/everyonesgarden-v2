package com.garden.back.domain.garden;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "garden_chat_rooms")
@Entity
public class GardenChatRoom {

    private static final int MAXIMUM_PEOPLE = 2;

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

    private GardenChatRoom(
            Long chatRoomId
    ) {
        Assert.isTrue(chatRoomId > 0 , "chatRoom id는 양수여야 합니다.");
        this.chatRoomId = chatRoomId;
    }

    public static GardenChatRoom of(
            Long chatRoomId
    ){
        return new GardenChatRoom(
                chatRoomId
        );
    }

    public boolean isRoomEmpty() {
        int size = chatRoomInfos.stream()
                .filter(GardenChatRoomInfo::isDeleted)
                .toList()
                .size();

        return size == MAXIMUM_PEOPLE;
    }


}
