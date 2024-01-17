package com.garden.back.repository.chatmessage.garden;

import com.garden.back.domain.garden.GardenChatMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GardenChatMessageRepositoryImpl implements GardenChatMessageRepository {

    private final GardenChatMessageJpaRepository gardenChatMessageJpaRepository;

    public GardenChatMessageRepositoryImpl(GardenChatMessageJpaRepository gardenChatMessageJpaRepository) {
        this.gardenChatMessageJpaRepository = gardenChatMessageJpaRepository;
    }

    @Override
    public void markMessagesAsRead(Long roomId,
                                   Long partnerId) {
        gardenChatMessageJpaRepository.markMessagesAsRead(roomId, partnerId);
    }

    @Override
    public GardenChatMessage save(GardenChatMessage chatMessage) {
        return gardenChatMessageJpaRepository.save(chatMessage);
    }

    @Override
    public List<GardenChatMessage> findAll() {
        return gardenChatMessageJpaRepository.findAll();
    }

    @Override
    public Optional<GardenChatMessage> findById(Long chatMessageId) {
        return gardenChatMessageJpaRepository.findById(chatMessageId);
    }

    @Override
    public GardenChatMessage getById(Long chatMessageId) {
        return findById(chatMessageId).orElseThrow(()-> new EntityNotFoundException("해당하는 아이디의 메세지는 존재하지 않습니다."));
    }

    @Override
    public Slice<GardenChatMessage> getGardenChatMessage(Long chatRoomId, Pageable pageable) {
        return gardenChatMessageJpaRepository.getGardenChatMessage(chatRoomId, pageable);
    }
}
