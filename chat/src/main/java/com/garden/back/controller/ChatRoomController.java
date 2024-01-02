package com.garden.back.controller;

import com.garden.back.controller.dto.GardenChatRoomCreateRequest;
import com.garden.back.global.LocationBuilder;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.service.ChatRoomService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/chats")
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @PostMapping(
            path = "/gardens",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> createChatRoom(
            @RequestBody @Valid GardenChatRoomCreateRequest request,
            @CurrentUser LoginUser loginUser
    ){
        URI location = LocationBuilder.buildLocation(
                chatRoomService.createGardenChatRoom(request.to(loginUser)
                ));

        return ResponseEntity.created(location).build();
    }

}