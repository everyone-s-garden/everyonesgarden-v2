package com.garden.back.chat.gardenchat.controller;

import com.garden.back.chat.gardenchat.controller.dto.request.GardenChatReportRequest;
import com.garden.back.chat.gardenchat.controller.dto.request.GardenChatRoomCreateRequest;
import com.garden.back.chat.gardenchat.controller.dto.request.GardenSessionCreateRequest;
import com.garden.back.chat.gardenchat.controller.dto.response.GardenChatReportResponse;
import com.garden.back.chat.gardenchat.controller.dto.response.GardenChatRoomEnterResponse;
import com.garden.back.chat.gardenchat.facade.ChatRoomFacade;
import com.garden.back.chat.gardenchat.facade.GardenChatRoomEnterFacadeRequest;
import com.garden.back.garden.service.GardenChatRoomService;
import com.garden.back.garden.service.dto.request.GardenChatRoomDeleteParam;
import com.garden.back.global.LocationBuilder;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RequestMapping("/garden-chats")
@RestController
public class GardenChatRoomController {

    private final GardenChatRoomService gardenChatRoomService;
    private final ChatRoomFacade chatRoomFacade;

    public GardenChatRoomController(GardenChatRoomService gardenChatRoomService, ChatRoomFacade chatRoomFacade) {
        this.gardenChatRoomService = gardenChatRoomService;
        this.chatRoomFacade = chatRoomFacade;
    }

    @PostMapping(
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

    @PatchMapping(
            path = "/{roomId}",
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
            path = "/sessions",
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
            path = "/{roomId}"
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

    @PostMapping(
            path = "/{roomId}/report",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<GardenChatReportResponse> repostGardenChat(
            @PathVariable Long roomId,
            @RequestPart(value = "reportImages", required = false) List<MultipartFile> multipartFiles,
            @RequestPart @Valid GardenChatReportRequest gardenChatReportRequest,
            @CurrentUser LoginUser loginUser
    ) {

        Long reportId = gardenChatRoomService.reportChatRoom(gardenChatReportRequest.toGardenChatReportRequest(
                loginUser,
                roomId,
                multipartFiles
        ));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GardenChatReportResponse.of(reportId));
    }

}