package com.garden.back.report.domain.garden;

public enum GardenReportType {

    OFF_TRACK("주제에 벗어남",1),
    INACCURATE_INFORMATION("부정확한 정보",1),
    FAKED_SALE("광고성 게시글", 5),
    SPAMMING("도배글", 1),
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