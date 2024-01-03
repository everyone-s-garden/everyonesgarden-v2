package com.garden.back.crop.service.request;

public record FindAllCropsPostServiceRequest(//지역, 제목 + 내용 검색 추후 추가
    Integer offset,
    Integer limit,
    OrderBy orderBy
) {
    public enum OrderBy {
        RECENT_DATE, LIKE_COUNT, OLDER_DATE
    }
}
