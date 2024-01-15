package com.garden.back.repository.chatmessage.garden;

import com.garden.back.domain.garden.GardenChatMessage;

import java.util.List;

public interface GardenChatMessageRepository {

    void markMessagesAsRead(Long roomId, Long partnerId);

    GardenChatMessage save(GardenChatMessage chatMessage);

    List<GardenChatMessage> findAll();
}
