package com.garden.back.garden.service.recentview;

import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RecentViewGardenStorage implements GardenHistoryManager {

    private static final Map<Long, RecentViewGardens> recentViewGardens = new ConcurrentHashMap<>();

    @Override
    public void addRecentViewGarden(Long memberId, RecentViewGarden recentViewGarden) {
        RecentViewGardenStorage.recentViewGardens.computeIfAbsent(memberId, k -> new RecentViewGardens(new ArrayDeque<>()))
                .addRecentViewGarden(recentViewGarden);
    }

    @Override
    public Optional<RecentViewGardens> findRecentViewGarden(Long memberId) {
        return Optional.of(recentViewGardens.get(memberId).getRecentViewGardens());
    }

}
