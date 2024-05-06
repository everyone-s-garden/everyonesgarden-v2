package com.garden.back.report.domain.post;

public enum PostReportType {

    OFF_TRACK("주제에 벗어남",1),
    INACCURATE_INFORMATION("부정확한 정보",1),
    FAKED_SALE("광고성 게시글", 5),
    SPAMMING("도배글", 1),
    SWEAR_WORD("욕설", 3),
    SENSATIONAL("선정성", 5),
    PERSONAL_INFORMATION_EXPOSURE("개인정보노출", 2),
    OFFENSIVE_EXPRESSION("불쾌한 표현", 3),
    COMMENTS("기타",1);

    private final String description;
    private final int score;

    PostReportType(String description, int score) {
        this.description = description;
        this.score = score;
    }
}
