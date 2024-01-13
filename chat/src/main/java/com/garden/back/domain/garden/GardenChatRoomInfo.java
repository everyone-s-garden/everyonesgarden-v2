package com.garden.back.domain.garden;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
@Table(name = "garden_chat_room_infos")
@Entity
public class GardenChatRoomInfo {

    protected GardenChatRoomInfo() {
    }

    @Column(name = "chat_room_info_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomInfoId;

    @Column(name = "is_writer", nullable = false)
    private boolean isWriter;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private GardenChatRoom chatRoom;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    public void create(GardenChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.addChatRoomInfos(this);
    }

    private GardenChatRoomInfo(
            boolean isWriter,
            Long postId,
            Long memberId
    ) {
        Assert.isTrue(memberId >0, "유저 아이디는 0이거나 0보다 작을 수 없습니다.");
        Assert.isTrue(postId >0, "게시글 아이디는 0이거나 0보다 작을 수 없습니다.");

        this.isWriter = isWriter;
        this.postId = postId;
        this.memberId = memberId;
    }

    public static GardenChatRoomInfo of(
            boolean isWriter,
            Long postId,
            Long memberId
    ) {
        return new GardenChatRoomInfo(
                isWriter,
                postId,
                memberId
        );
    }

}
