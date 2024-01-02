package com.garden.back.domain.crop;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "crop_chat_room_infos")
@Entity
public class CropChatRoomInfo {

    protected CropChatRoomInfo() {
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
    private CropChatRoom chatRoom;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    public void create(CropChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.addChatRoomInfos(this);
    }

    private CropChatRoomInfo(
            boolean isWriter,
            Long postId,
            Long memberId
    ) {
        this.isWriter = isWriter;
        this.postId = postId;
        this.memberId = memberId;
    }

    public static CropChatRoomInfo of(
            boolean isWriter,
            Long postId,
            Long memberId
    ) {
        return new CropChatRoomInfo(
                isWriter,
                postId,
                memberId
        );
    }

}
