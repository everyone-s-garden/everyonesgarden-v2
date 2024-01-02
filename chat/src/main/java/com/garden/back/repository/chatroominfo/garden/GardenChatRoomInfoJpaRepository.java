package com.graden.back.repository.chatroominfo.garden;

import com.graden.back.domain.garden.GardenChatRoomInfo;
import com.graden.back.repository.chatroom.dto.ChatRoomCreateRepositoryParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GardenChatRoomInfoJpaRepository extends JpaRepository<GardenChatRoomInfo, Long> {
    @Query("""
           select case when exists(
                    select cri
                    from GardenChatRoomInfo as cri
                    where cri.postId = :#{#param.postId}
                      and cri.memberId = :#{#param.writerId}
           ) then true else false end
           """)
    boolean existsByParams(@Param("param") ChatRoomCreateRepositoryParam param);

    @Query(
            """
                    select cri.memberId
                    from GardenChatRoomInfo as cri
                    where cri.chatRoom.chatRoomId =:chatRoomId
                    and cri.memberId !=:myId
                    """
    )
    Long findPartnerId(@Param("chatRoomId") Long chatRoomId,
                       @Param("myId") Long memberId);

}
