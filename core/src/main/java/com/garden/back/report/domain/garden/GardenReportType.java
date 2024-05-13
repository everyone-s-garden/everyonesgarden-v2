package com.garden.back.report.domain.garden;

import lombok.Getter;

@Getter
public enum GardenReportType {

    ADVERTISING("광고성 게시글",1),
    OFF_TRACK("주제에 벗어남",1),
    INACCURATE_INFORMATION("부정확한 정보",1),
    FAKED_SALE("허위 게시글", 5),
    SPAMMING("도배 및 중복", 1),
    SWEAR_WORD("욕설", 3),
    SENSATIONAL("선정성", 5),
    PERSONAL_INFORMATION_EXPOSURE("개인정보노출", 2),
    COMMENTS("기타사항",1);

    private final String description;
    private final int score;

    GardenReportType(String description, int score) {
        this.description = description;
        this.score = score;
    }
}