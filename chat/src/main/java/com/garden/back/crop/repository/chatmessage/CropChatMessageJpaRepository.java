package com.garden.back.crop.repository.chatmessage;

import com.garden.back.crop.domain.CropChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CropChatMessageJpaRepository extends JpaRepository<CropChatMessage, Long> {

    @Modifying(clearAutomatically = true)
    @Query(
            """
                    update CropChatMessage as cm
                    set cm.readOrNot = true
                    where cm.chatRoom.chatRoomId = :roomId
                    and cm.memberId = :partnerId
                    """
    )
    void markMessagesAsRead(@Param("roomId") Long roomId,
                            @Param("partnerId") Long partnerId);
}
