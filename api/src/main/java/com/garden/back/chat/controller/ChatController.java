package com.garden.back.chat.controller;

import com.garden.back.chat.controller.dto.request.GardenMessageSendRequest;
import com.garden.back.chat.controller.dto.response.GardenMessageSendResponse;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.repository.chatentry.SessionId;
import com.garden.back.service.garden.GardenChatService;
import jakarta.validation.Valid;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@RestController
public class ChatController {

    private final GardenChatService gardenChatService;

    public ChatController(GardenChatService gardenChatService) {
        this.gardenChatService = gardenChatService;
    }

    @MessageMapping("/chats/{roomId}/messages")
    @SendToUser("/queue/chats/{roomId}")
    public GardenMessageSendResponse sendMessage(@DestinationVariable("roomId") Long roomId,
                                                 @CurrentUser LoginUser loginUser,
                                                 @Payload @Valid GardenMessageSendRequest request) {
        return GardenMessageSendResponse.to(gardenChatService.saveMessage(request.to(loginUser, roomId)));
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        gardenChatService.leaveChatRoom(SessionId.of(event.getSessionId()));
    }

}
