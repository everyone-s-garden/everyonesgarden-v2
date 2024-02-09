package com.garden.back.chat.gardenchat.controller;

import com.garden.back.chat.gardenchat.controller.dto.request.GardenChatMessagesGetRequest;
import com.garden.back.chat.gardenchat.controller.dto.request.GardenMessageSendRequest;
import com.garden.back.chat.gardenchat.controller.dto.response.GardenChatMessageGetResponses;
import com.garden.back.chat.gardenchat.controller.dto.response.GardenChatRoomsFindResponses;
import com.garden.back.chat.gardenchat.controller.dto.response.GardenMessageSendResponse;
import com.garden.back.chat.gardenchat.facade.ChatRoomFacade;
import com.garden.back.chat.gardenchat.facade.GardenChatRoomsFindFacadeRequest;
import com.garden.back.chat.gardenchat.facade.GardenChatRoomsFindFacadeResponses;
import com.garden.back.garden.service.GardenChatService;
import com.garden.back.garden.service.dto.response.GardenChatMessagesGetResults;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GardenChatController {

    private final GardenChatService gardenChatService;
    private final ChatRoomFacade chatRoomFacade;

    public GardenChatController(GardenChatService gardenChatService, ChatRoomFacade chatRoomFacade) {
        this.gardenChatService = gardenChatService;
        this.chatRoomFacade = chatRoomFacade;
    }

    @MessageMapping("/garden-chats/{roomId}")
    @SendTo("/queue/garden-chats/{roomId}")
    public GardenMessageSendResponse sendMessage(
        @DestinationVariable("roomId") Long roomId,
        Authentication  authentication,
        @Payload GardenMessageSendRequest request,
        @Header("simpSessionId") String sessionId) {

        return GardenMessageSendResponse.to(gardenChatService.saveMessage(
            request.to(
                authentication.getName(),
                roomId,
                sessionId)));
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

    @GetMapping(
        path = "/garden-chats",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GardenChatRoomsFindResponses> findChatRoomsInMember(
        @RequestParam @NotNull Integer pageNumber,
        @CurrentUser LoginUser loginUser
    ) {
        GardenChatRoomsFindFacadeResponses chatRoomsInMember = chatRoomFacade.findChatRoomsInMember(GardenChatRoomsFindFacadeRequest.of(
            loginUser,
            pageNumber
        ));

        return ResponseEntity.status(HttpStatus.OK)
            .body(GardenChatRoomsFindResponses.to(chatRoomsInMember));
    }

}
