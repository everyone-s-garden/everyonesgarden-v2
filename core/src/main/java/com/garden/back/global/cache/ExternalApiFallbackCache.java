package com.garden.back.global.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 날씨 api 응답이 느려서 폴백으로 돌 캐시
 */
@Component
public class ExternalApiFallbackCache {
    private static final Map<Object, Object> cache = new ConcurrentHashMap<>();

    public void put(Object key, Object value) {
        cache.put(key, value);
    }

    public Object get(Object key) {
        return cache.get(key);
    }
}
