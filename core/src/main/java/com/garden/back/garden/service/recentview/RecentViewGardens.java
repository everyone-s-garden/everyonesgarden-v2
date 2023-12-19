package com.garden.back.garden.service.recentview;

import java.util.*;

public record RecentViewGardens(
        Deque<RecentViewGarden> recentViewGardens
) {
    public static final int MAX_RECENT_VIEW_COUNT = 10;

    public void addRecentViewGarden(RecentViewGarden recentViewGarden) {
        recentViewGardens.remove(recentViewGarden);
        recentViewGardens.addFirst(recentViewGarden);
        ensureMaxSize();
    }

    public RecentViewGardens getRecentViewGardens() {
        return new RecentViewGardens(new ArrayDeque<>(recentViewGardens));
    }

    private void ensureMaxSize() {
        if (recentViewGardens.size() > MAX_RECENT_VIEW_COUNT) {
            recentViewGardens.removeLast();
        }
    }

}
