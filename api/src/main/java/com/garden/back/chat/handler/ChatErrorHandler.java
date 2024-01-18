package com.garden.back.chat.handler;

import com.garden.back.chat.exception.UnauthorizedException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Configuration
public class ChatErrorHandler extends StompSubProtocolErrorHandler {
    private static final byte[] EMPTY_PAYLOAD = new byte[0];

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable throwable) {

        final Throwable exception = converterTrowException(throwable);

        if (exception instanceof UnauthorizedException) {
            return handleUnauthorizedException(clientMessage, exception);
        }

        return super.handleClientMessageProcessingError(clientMessage, throwable);
    }

    private Throwable converterTrowException(final Throwable exception) {
        if (exception instanceof MessageDeliveryException) {
            return exception.getCause();
        }
        return exception;
    }

    private Message<byte[]> handleUnauthorizedException(Message<byte[]> clientMessage,
                                                        Throwable ex) {
        return prepareErrorMessage(clientMessage, ex.getMessage(), HttpStatus.UNAUTHORIZED.name());
    }

    private Message<byte[]> prepareErrorMessage(final Message<byte[]> clientMessage,
                                                final String message, final String errorCode) {

        final StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setMessage(errorCode);
        accessor.setLeaveMutable(true);

        setReceiptIdForClient(clientMessage, accessor);

        return MessageBuilder.createMessage(
                message != null ? message.getBytes(StandardCharsets.UTF_8) : EMPTY_PAYLOAD,
                accessor.getMessageHeaders()
        );
    }

    private void setReceiptIdForClient(final Message<byte[]> clientMessage,
                                       final StompHeaderAccessor accessor) {

        if (Objects.isNull(clientMessage)) {
            return;
        }

        final StompHeaderAccessor clientHeaderAccessor = MessageHeaderAccessor.getAccessor(
                clientMessage, StompHeaderAccessor.class);

        final String receiptId =
                Objects.isNull(clientHeaderAccessor) ? null : clientHeaderAccessor.getReceipt();

        if (receiptId != null) {
            accessor.setReceiptId(receiptId);
        }
    }

    @Override
    protected Message<byte[]> handleInternal(StompHeaderAccessor errorHeaderAccessor,
                                             byte[] errorPayload, Throwable cause, StompHeaderAccessor clientHeaderAccessor) {
        return MessageBuilder.createMessage(errorPayload, errorHeaderAccessor.getMessageHeaders());
    }

}
