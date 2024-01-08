package com.garden.back.repository.chatmessage.garden;

import com.garden.back.domain.garden.GardenChatMessage;
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
}
