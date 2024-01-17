package com.garden.back.chat.controller;

import com.garden.back.chat.controller.dto.request.GardenChatMessagesGetRequest;
import com.garden.back.chat.controller.dto.request.GardenMessageSendRequest;
import com.garden.back.chat.controller.dto.response.GardenChatMessageGetResponses;
import com.garden.back.chat.controller.dto.response.GardenMessageSendResponse;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.repository.chatentry.SessionId;
import com.garden.back.service.garden.GardenChatService;
import com.garden.back.service.garden.dto.response.GardenChatMessagesGetResults;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@RestController
public class ChatController {

    private final GardenChatService gardenChatService;

    public ChatController(GardenChatService gardenChatService) {
        this.gardenChatService = gardenChatService;
    }

    @MessageMapping("/garden-chats/{roomId}/")
    @SendToUser("/queue/garden-chats/{roomId}")
    public GardenMessageSendResponse sendMessage(
            @DestinationVariable("roomId") Long roomId,
            @CurrentUser LoginUser loginUser,
            @Payload @Valid GardenMessageSendRequest request) {
        return GardenMessageSendResponse.to(gardenChatService.saveMessage(request.to(loginUser, roomId)));
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        gardenChatService.leaveChatRoom(SessionId.of(event.getSessionId()));
    }

    @GetMapping(
            path = "/garden-chats/{roomId}/messages",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GardenChatMessageGetResponses> getGardenChatMessages(
            @PathVariable @Positive Long roomId,
            @CurrentUser LoginUser loginUser,
            @RequestParam @NotNull Integer pageNumber
    ) {
        GardenChatMessagesGetResults chatRoomMessages = gardenChatService.getChatRoomMessages(
                GardenChatMessagesGetRequest.to(loginUser, roomId, pageNumber)
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(GardenChatMessageGetResponses.to(chatRoomMessages));
    }

}
