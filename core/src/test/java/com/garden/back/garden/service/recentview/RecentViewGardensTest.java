package com.garden.back.garden.service.recentview;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RecentViewGardensTest {

    @DisplayName("최근 본 텃밭의 개수는 항상 "+RecentViewGardens.MAX_RECENT_VIEW_COUNT+"보다 작거나 같다.")
    @Test
    void addRecentViewGarden_ensureMaxSize() {
        // Given
        RecentViewGardens recentViews = new RecentViewGardens(new ArrayDeque<>());
        for (long i = 0L; i < RecentViewGardens.MAX_RECENT_VIEW_COUNT + 5; i++) {
            RecentViewGarden garden = new RecentViewGarden(
                    i, "Size", "Name", "Price", "Image", "Status", "Type");
            // When
            recentViews.addRecentViewGarden(garden);
        }

        // Then
        List<RecentViewGarden> recentViewGardenList = recentViews.getRecentViewGardens().recentViewGardens().stream().toList();
        assertThat(recentViewGardenList.size()).isEqualTo(RecentViewGardens.MAX_RECENT_VIEW_COUNT);
    }
}
