package com.garden.back.garden.repository.chatmessage;

import com.garden.back.garden.domain.GardenChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GardenChatMessageJpaRepository extends JpaRepository<GardenChatMessage, Long> {

    @Modifying(clearAutomatically = true)
    @Query(
            """
                    update GardenChatMessage as cm
                    set cm.readOrNot = true
                    where cm.chatRoom.chatRoomId = :roomId
                    and cm.memberId = :partnerId
                    """
    )
    void markMessagesAsRead(@Param("roomId") Long roomId,
                            @Param("partnerId") Long partnerId);


    @Query(
            """
            select gm
            from GardenChatMessage as gm
            where gm.chatRoom.chatRoomId =:chatRoomId
            order by gm.createdAt desc    
            """
    )
    Slice<GardenChatMessage> getGardenChatMessage(@Param("chatRoomId") Long chatRoomId, Pageable pageable);

    @Query(
            """
                    SELECT
                                MAX(gm.chatMessageId) as chatMessageId,
                                MAX(gm.createdAt) AS createdAt,
                                SUM(gm.readOrNot = false and gm.memberId !=:myId) AS notReadCount,
                                MAX(gcri.memberId) AS partnerId,
                                MAX(gcri.chatRoom.chatRoomId) AS chatRoomId,
                                MAX(gcri.postId) AS postId
                            FROM
                                        GardenChatRoomInfo AS gcrim
                            JOIN
                                        GardenChatRoomInfo AS gcri ON gcrim.chatRoom.chatRoomId = gcri.chatRoom.chatRoomId
                                    and gcrim.memberId =:myId and gcrim.memberId != gcri.memberId
                            JOIN
                                GardenChatMessage AS gm ON gm.chatRoom.chatRoomId = gcri.chatRoom.chatRoomId
                            group by gm.chatRoom.chatRoomId
            """
    )
    Slice<ChatRoomFindRepositoryResponse> findChatRooms(@Param("myId") Long memberId, Pageable pageable);

    @Query(
            """
            select gcm.contents
            from GardenChatMessage as gcm
            where gcm.chatMessageId =:chatMessageId
            """
    )
    String getContentsById(@Param("chatMessageId") Long chatMessageId);

}
