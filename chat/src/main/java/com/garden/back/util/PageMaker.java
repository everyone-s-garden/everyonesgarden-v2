package com.garden.back.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageMaker {

    private PageMaker() {
        throw new AssertionError("유틸 클래스는 객체를 생성할 수 없습니다.");
    }

    private static final int GARDEN_BY_NAME_PAGE_SIZE = 10;

    public static Pageable makePage(Integer pageNumber) {
        if(pageNumber < 0) {
            throw new IllegalArgumentException("페이지 넘버는 음수일 수 없습니다.");
        }

        return PageRequest.of(pageNumber, GARDEN_BY_NAME_PAGE_SIZE);
    }

}
