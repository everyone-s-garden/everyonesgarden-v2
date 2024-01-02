package com.garden.back.repository.chatroominfo.crop;

import com.garden.back.domain.crop.CropChatRoomInfo;
import com.garden.back.repository.chatroom.dto.ChatRoomCreateRepositoryParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CropChatRoomInfoJpaRepository extends JpaRepository<CropChatRoomInfo, Long> {
    @Query("""
           select case when exists(
                    select cri
                    from CropChatRoomInfo as cri
                    where cri.postId = :#{#param.postId}
                      and cri.memberId = :#{#param.writerId}
           ) then true else false end
           """)
    boolean existsByParams(@Param("param") ChatRoomCreateRepositoryParam param);

    @Query(
            """
                    select cri.memberId
                    from CropChatRoomInfo as cri
                    where cri.chatRoom.chatRoomId =:chatRoomId
                    and cri.memberId !=:myId
                    """
    )
    Long findPartnerId(@Param("chatRoomId") Long chatRoomId,
                       @Param("myId") Long memberId);
}
