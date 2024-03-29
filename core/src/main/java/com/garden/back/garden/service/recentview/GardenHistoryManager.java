package com.garden.back.garden.service.recentview;

import java.util.Optional;

public interface GardenHistoryManager {
    void addRecentViewGarden(Long memberId, RecentViewGarden recentViewGarden);

    Optional<RecentViewGardens> findRecentViewGarden(Long memberId);

}
