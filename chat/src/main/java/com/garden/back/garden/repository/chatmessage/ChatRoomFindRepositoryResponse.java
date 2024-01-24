package com.garden.back.garden.repository.chatmessage;

import java.time.LocalDateTime;

public interface ChatRoomFindRepositoryResponse {
    Long getChatMessageId();
    LocalDateTime getCreatedAt();
    int getNotReadCount();
    Long getPartnerId();
    Long getChatRoomId();
    Long getPostId();
}
