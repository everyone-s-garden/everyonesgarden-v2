package com.garden.back.chat.cropchat.controller;

import com.garden.back.chat.cropchat.controller.dto.request.CropChatRoomCreateRequest;
import com.garden.back.global.LocationBuilder;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.crop.service.CropChatRoomService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/crop-chats")
@RestController
public class CropChatRoomController {

    private final CropChatRoomService cropChatRoomService;

    public CropChatRoomController(CropChatRoomService cropChatRoomService) {
        this.cropChatRoomService = cropChatRoomService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> createCropChatRoom(
            @RequestBody @Valid CropChatRoomCreateRequest request,
            @CurrentUser LoginUser loginUser
    ) {
        URI location = LocationBuilder.buildLocation(
                cropChatRoomService.createCropChatRoom(request.to(loginUser)
                ));

        return ResponseEntity.created(location).build();
    }
}
