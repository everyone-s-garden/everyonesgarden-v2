package com.garden.back.controller;

import com.garden.back.controller.dto.CropChatRoomCreateRequest;
import com.garden.back.controller.dto.GardenChatRoomCreateRequest;
import com.garden.back.global.LocationBuilder;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.service.crop.CropChatRoomService;
import com.garden.back.service.garden.GardenChatRoomService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/chats")
@RestController
public class ChatRoomController {

    private final GardenChatRoomService gardenChatRoomService;
    private final CropChatRoomService chatRoomService;

    public ChatRoomController(GardenChatRoomService gardenChatRoomService, CropChatRoomService chatRoomService) {
        this.gardenChatRoomService = gardenChatRoomService;
        this.chatRoomService = chatRoomService;
    }

    @PostMapping(
            path = "/gardens",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> createGardenChatRoom(
            @RequestBody @Valid GardenChatRoomCreateRequest request,
            @CurrentUser LoginUser loginUser
    ){
        URI location = LocationBuilder.buildLocation(
                gardenChatRoomService.createGardenChatRoom(request.to(loginUser)
                ));

        return ResponseEntity.created(location).build();
    }

    @PostMapping(
            path = "/crops",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> createCropChatRoom(
            @RequestBody @Valid CropChatRoomCreateRequest request,
            @CurrentUser LoginUser loginUser
    ){
        URI location = LocationBuilder.buildLocation(
                chatRoomService.createCropChatRoom(request.to(loginUser)
                ));

        return ResponseEntity.created(location).build();
    }

}