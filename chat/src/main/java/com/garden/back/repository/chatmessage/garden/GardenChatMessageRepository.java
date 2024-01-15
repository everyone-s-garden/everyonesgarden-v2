package com.garden.back.repository.chatmessage.garden;

import com.garden.back.domain.garden.GardenChatMessage;

import java.util.List;
import java.util.Optional;

public interface GardenChatMessageRepository {

    void markMessagesAsRead(Long roomId, Long partnerId);

    GardenChatMessage save(GardenChatMessage chatMessage);

    List<GardenChatMessage> findAll();

    Optional<GardenChatMessage> findById(Long chatMessageId);

    GardenChatMessage getById(Long chatMessageId);
}
