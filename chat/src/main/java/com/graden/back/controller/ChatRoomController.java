package com.graden.back.controller;

import com.graden.back.controller.dto.ChatRoomCreateRequest;
import com.graden.back.global.LocationBuilder;
import com.graden.back.global.loginuser.CurrentUser;
import com.graden.back.global.loginuser.LoginUser;
import com.graden.back.service.ChatRoomService;
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
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> createChatRoom(
            @RequestBody @Valid ChatRoomCreateRequest request,
            @CurrentUser LoginUser loginUser
    ){
        URI location = LocationBuilder.buildLocation(
                chatRoomService.createChatRoom(request.to(loginUser)
                ));

        return ResponseEntity.created(location).build();
    }

}