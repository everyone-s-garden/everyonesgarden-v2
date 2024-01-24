package com.garden.back.global;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum ChatType {
    GARDEN(1L), CROP(2L);

    private final Long chatTypeId;

    ChatType(Long chatTypeId) {
        this.chatTypeId = chatTypeId;
    }

    public static boolean containsId(Long chatTypeId) {
        return Arrays.stream(ChatType.values())
                .anyMatch(chatType -> Objects.equals(chatType.getChatTypeId(), chatTypeId));
    }

}
