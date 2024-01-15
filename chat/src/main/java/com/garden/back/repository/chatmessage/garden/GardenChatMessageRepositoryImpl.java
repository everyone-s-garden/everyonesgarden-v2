package com.garden.back.repository.chatmessage.garden;

import com.garden.back.domain.garden.GardenChatMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
