package com.garden.back.chat.controller;

import com.garden.back.chat.controller.dto.request.CropChatRoomCreateRequest;
import com.garden.back.chat.controller.dto.request.GardenChatRoomCreateRequest;
import com.garden.back.chat.controller.dto.request.GardenSessionCreateRequest;
import com.garden.back.chat.controller.dto.response.GardenChatRoomEnterResponse;
import com.garden.back.chat.facade.ChatRoomFacade;
import com.garden.back.chat.facade.GardenChatRoomEnterFacadeRequest;
import com.garden.back.global.LocationBuilder;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.service.crop.CropChatRoomService;
import com.garden.back.service.garden.GardenChatRoomService;
import com.garden.back.service.garden.dto.request.GardenChatRoomDeleteParam;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/chats")
@RestController
public class ChatRoomController {

    private final GardenChatRoomService gardenChatRoomService;
    private final CropChatRoomService chatRoomService;
    private final ChatRoomFacade chatRoomFacade;

    public ChatRoomController(GardenChatRoomService gardenChatRoomService, CropChatRoomService chatRoomService, ChatRoomFacade chatRoomFacade) {
        this.gardenChatRoomService = gardenChatRoomService;
        this.chatRoomService = chatRoomService;
        this.chatRoomFacade = chatRoomFacade;
    }

    @PostMapping(
            path = "/gardens",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> createGardenChatRoom(
            @RequestBody @Valid GardenChatRoomCreateRequest request,
            @CurrentUser LoginUser loginUser
    ) {
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
    ) {
        URI location = LocationBuilder.buildLocation(
                chatRoomService.createCropChatRoom(request.to(loginUser)
                ));

        return ResponseEntity.created(location).build();
    }

    @PatchMapping(
            path = "/gardens/{roomId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GardenChatRoomEnterResponse> enterGardenChatRoom(
            @PathVariable @Positive Long roomId,
            @CurrentUser LoginUser loginUser
    ) {
        GardenChatRoomEnterFacadeRequest request = GardenChatRoomEnterFacadeRequest.to(roomId, loginUser);
        return ResponseEntity.status(HttpStatus.OK)
                .body(GardenChatRoomEnterResponse.to(
                        chatRoomFacade.enterGardenChatRoom(request)));
    }

    @PostMapping(
            path = "/gardens/sessions",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> createSession(
            @RequestBody @Valid GardenSessionCreateRequest request,
            @CurrentUser LoginUser loginUser
    ) {
        gardenChatRoomService.createSessionInfo(request.to(loginUser));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(
            path = "/gardens/{roomId}"
    )
    public ResponseEntity<Void> deleteChatRoom(
            @PathVariable @Positive Long roomId,
            @CurrentUser LoginUser loginUser
    ) {
        gardenChatRoomService.deleteChatRoom(
                GardenChatRoomDeleteParam.of(
                        roomId,
                        loginUser.memberId()
                )
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}