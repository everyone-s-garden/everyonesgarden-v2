package com.garden.back.crop.repository.chatroomInfo;

import com.garden.back.crop.domain.CropChatRoomInfo;
import com.garden.back.garden.repository.chatroom.dto.ChatRoomCreateRepositoryParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CropChatRoomInfoJpaRepository extends JpaRepository<CropChatRoomInfo, Long> {
    @Query("""
           select case when exists(
                    select cri
                    from CropChatRoomInfo as cri
                    where cri.postId = :#{#param.postId}
                      and isWriter = false
                      and cri.memberId = :#{#param.viewerId}
           ) then true else false end
           """)
    boolean existsByParams(@Param("param") ChatRoomCreateRepositoryParam param);

}
