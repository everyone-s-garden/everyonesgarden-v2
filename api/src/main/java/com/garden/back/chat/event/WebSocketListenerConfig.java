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

import java.util.List;
import java.util.Map;

@Component
public class WebSocketListenerConfig {

    private static final String SUBSCRIBE_URL = "/queue/garden-chats/";
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
        Object sessionIdObj = event.getMessage().getHeaders().get("simpSessionId");
        if (sessionIdObj == null) {
            throw new IllegalArgumentException("Session Id null일 수 없습니다.");
        }
        return sessionIdObj.toString();
    }

    private Long getMemberId(AbstractSubProtocolEvent event) {
        Map<String, List<String>> nativeHeaders = event.getMessage().getHeaders().get("nativeHeaders", Map.class);
        if (nativeHeaders == null) {
            throw new IllegalArgumentException("Native header는 null일 수 없습니다.");
        }

        List<String> memberIdList = nativeHeaders.get("memberId");
        if (memberIdList == null || memberIdList.isEmpty()) {
            throw new IllegalArgumentException("Member Id는 null이거나 빈 값일 수 없습니다.");
        }

        String memberId = memberIdList.get(0);
        return Long.parseLong(memberId.replaceAll("[\\[\\]]", ""));
    }


    private Long getRoomId(AbstractSubProtocolEvent event) {
        String destination = event.getMessage().getHeaders().get("simpDestination", String.class);
        if (destination == null) {
            throw new IllegalArgumentException("Destination는 null일 수 없습니다.");
        }
        return Long.parseLong(destination.replace(SUBSCRIBE_URL, ""));
    }

}