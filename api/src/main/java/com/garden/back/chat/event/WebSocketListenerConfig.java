package com.garden.back.chat.event;

import com.garden.back.garden.repository.chatentry.SessionId;
import com.garden.back.garden.service.GardenChatRoomService;
import com.garden.back.garden.service.GardenChatService;
import com.garden.back.garden.service.dto.request.GardenSessionCreateParam;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Map;

@Component
public class WebSocketListenerConfig {

    private static final String SUBSCRIBE_URL="/queue/garden-chats/";
    private final GardenChatService gardenChatService;
    private final GardenChatRoomService gardenChatRoomService;

    public WebSocketListenerConfig(GardenChatService gardenChatService, GardenChatRoomService gardenChatRoomService) {
        this.gardenChatService = gardenChatService;
        this.gardenChatRoomService = gardenChatRoomService;
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        gardenChatService.leaveChatRoom(SessionId.of(event.getSessionId()));
    }

    @EventListener(SessionConnectEvent.class)
    public void onConnect(SessionConnectEvent event) {
        String sessionId = getSessionId(event);
        Long memberId = getMemberId(event);
        gardenChatService.saveSocketInfo(sessionId, memberId);
    }

    @EventListener(SessionSubscribeEvent.class)
    public void onSubscribe(SessionSubscribeEvent event) {
        String sessionId = getSessionId(event);
        Long roomId = getRoomId(event);
        Long memberId = gardenChatService.getWebSocketInfo(sessionId);

        gardenChatRoomService.createSessionInfo(
            new GardenSessionCreateParam(
                SessionId.of(sessionId),
                roomId,
                memberId
            )
        );
    }

    private String getSessionId(AbstractSubProtocolEvent event) {
        return event.getMessage().getHeaders().get("simpSessionId").toString();
    }

    private Long getMemberId(AbstractSubProtocolEvent event) {
        String memberId = event.getMessage().getHeaders().get("nativeHeaders", Map.class)
            .get("memberId")
            .toString()
            .replaceAll("[\\[\\]]", ""); // Remove square brackets
        return Long.parseLong(memberId);
    }

    private Long getRoomId(AbstractSubProtocolEvent event) {
        String destination = event.getMessage().getHeaders().get("simpDestination", String.class);
        return Long.parseLong(destination.replace(SUBSCRIBE_URL, ""));
    }

}