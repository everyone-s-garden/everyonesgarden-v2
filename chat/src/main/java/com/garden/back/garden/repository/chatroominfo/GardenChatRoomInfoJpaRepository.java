package com.garden.back.garden.repository.chatroominfo;

import com.garden.back.garden.domain.GardenChatRoomInfo;
import com.garden.back.garden.repository.chatroom.dto.ChatRoomCreateRepositoryParam;
import com.garden.back.garden.repository.chatroominfo.dto.GardenChatRoomEnterRepositoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GardenChatRoomInfoJpaRepository extends JpaRepository<GardenChatRoomInfo, Long> {
    @Query("""
        select case when exists(
                 select cri
                 from GardenChatRoomInfo as cri
                 where cri.postId = :#{#param.postId}
                   and cri.isWriter = false
                   and cri.memberId = :#{#param.viewerId}
        ) then true else false end
        """)
    boolean existsByParams(@Param("param") ChatRoomCreateRepositoryParam param);

    @Query(
        """
            select
            cri.memberId as memberId,
            cri.postId as postId
            from GardenChatRoomInfo as cri
            where cri.chatRoom.chatRoomId =:chatRoomId
            and cri.memberId !=:myId
            """
    )
    GardenChatRoomEnterRepositoryResponse findPartnerId(@Param("chatRoomId") Long chatRoomId,
                                                        @Param("myId") Long memberId);

    @Query(
        """
            select gri
            from GardenChatRoomInfo as gri
            where gri.chatRoom.chatRoomId =:chatRoomId
            """
    )
    List<GardenChatRoomInfo> findByRoomId(@Param("chatRoomId") Long chatRoomId);

    @Modifying(clearAutomatically = true)
    @Query("""
        delete from GardenChatRoomInfo as gri
        where gri.chatRoom.chatRoomId =:chatRoomId
        """)
    void deleteAll(@Param("chatRoomId") Long chatRoomId);

    @Query(
        """
            select gri.chatRoom.chatRoomId
            from GardenChatRoomInfo as gri
            where gri.memberId =:memberId and gri.postId =:postId
            """
    )
    Optional<Long> findChatRoomId(@Param("memberId") Long memberId, @Param("postId") Long postId);

}
