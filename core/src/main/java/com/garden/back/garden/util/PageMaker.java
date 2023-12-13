package com.garden.back.garden.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageMaker {

    private PageMaker() {
        throw new RuntimeException("유틸 클래스는 객체를 생성할 수 없습니다.");
    }

    private static final int GARDEN_BY_NAME_PAGE_SIZE = 10;

    public static Pageable makePage(Integer pageNumber) {
        return PageRequest.of(pageNumber, GARDEN_BY_NAME_PAGE_SIZE);
    }

}
