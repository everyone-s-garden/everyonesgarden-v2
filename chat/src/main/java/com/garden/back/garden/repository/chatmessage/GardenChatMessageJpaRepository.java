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
}
