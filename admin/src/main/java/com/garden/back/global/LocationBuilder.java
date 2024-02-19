package com.garden.back.global;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

public class LocationBuilder {

    private LocationBuilder() {
        throw new AssertionError("유틸클래스 생성자를 통해서 객체를 생성할 수 없습니다.");
    }

    public static URI buildLocation(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}