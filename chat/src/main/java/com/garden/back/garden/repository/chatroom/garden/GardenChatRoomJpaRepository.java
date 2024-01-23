package com.garden.back.garden.repository.chatroom.garden;


import com.garden.back.garden.domain.GardenChatRoom;
import com.garden.back.global.exception.EntityNotFoundException;
import com.garden.back.global.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GardenChatRoomJpaRepository extends JpaRepository<GardenChatRoom, Long> {

    default GardenChatRoom getById(Long roomId) {
        return findById(roomId).orElseThrow(()->new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY));
    }

}
