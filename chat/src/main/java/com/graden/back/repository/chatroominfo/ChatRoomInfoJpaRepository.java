package com.graden.back.repository.chatroominfo;

import com.graden.back.domain.ChatRoomInfo;
import com.graden.back.repository.chatroom.dto.ChatRoomCreateRepositoryParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomInfoJpaRepository extends JpaRepository<ChatRoomInfo, Long> {
    @Query("""
           select case when exists(
                    select cri
                    from ChatRoomInfo as cri
                    where cri.postId = :#{#param.postId}
                      and cri.memberId = :#{#param.writerId}
                      and cri.chatType = :#{#param.chatType}
           ) then true else false end
           """)
    boolean existsByParams(@Param("param") ChatRoomCreateRepositoryParam param);
}
