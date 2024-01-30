package com.garden.back.global;

import lombok.Getter;

@Getter
public enum ChatReportType {
    NON_MANNER_USER("비매너사용자", -1),
    DISPUTE("분쟁", -3),
    FRAUD("사기", -10),
    SWEAR_WORD("욕설", -2),
    INAPPROPRIATE_BEHAVIOR("부적절한 행위", -3);

    private final String description;
    private final int score;

    ChatReportType(String description, int score) {
        this.description = description;
        this.score = score;
    }
}
