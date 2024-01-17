package com.garden.back.chat.handler;

import com.garden.back.auth.jwt.TokenProvider;
import com.garden.back.chat.exception.UnauthorizedException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;

@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Configuration
public class ChatPreHandler implements ChannelInterceptor {

    private static final String HEADER_PREFIX = "Bearer ";
    private static final String HEADER_NAME = "Authorization";
    private final TokenProvider tokenProvider;

    public ChatPreHandler(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        String authorizationHeader = headerAccessor.getFirstNativeHeader(HEADER_NAME);
        StompCommand stompCommand = headerAccessor.getCommand();

        if (isConnectOrSend(stompCommand)) {
            handleAuthorization(authorizationHeader, headerAccessor);
        }
        return message;
    }

    private boolean isConnectOrSend(StompCommand stompCommand) {
        return StompCommand.CONNECT == stompCommand || StompCommand.SEND == stompCommand;
    }

    private void handleAuthorization(String authorizationHeader, StompHeaderAccessor headerAccessor) {
        if (authorizationHeader != null && authorizationHeader.startsWith(HEADER_PREFIX)) {
            String accessToken = authorizationHeader.substring(HEADER_PREFIX.length());
            Authentication authentication = tokenProvider.getAuthentication(accessToken);
            headerAccessor.setUser(authentication);
        } else {
            throw new UnauthorizedException("유효한 Jwt 토큰이 아닙니다.");
        }
    }

}
