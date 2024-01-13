package com.garden.back.garden.service.recentview;

import com.garden.back.testutil.garden.GardenFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RecentViewGardensTest {

    @DisplayName("최근 본 텃밭의 개수는 항상 "+RecentViewGardens.MAX_RECENT_VIEW_COUNT+"보다 작거나 같다.")
    @Test
    void addRecentViewGarden_ensureMaxSize() {
        // Given
        RecentViewGardens recentViews = new RecentViewGardens(new ArrayDeque<>());
        for (long i = 0L; i < RecentViewGardens.MAX_RECENT_VIEW_COUNT + 5; i++) {
            RecentViewGarden garden = new RecentViewGarden(
                    i, "Size", "Name", "Price", List.of("Image"), "Status", "Type");
            // When
            recentViews.addRecentViewGarden(garden);
        }

        // Then
        List<RecentViewGarden> recentViewGardenList = recentViews.getRecentViewGardens().recentViewGardens().stream().toList();
        assertThat(recentViewGardenList.size()).isEqualTo(RecentViewGardens.MAX_RECENT_VIEW_COUNT);
    }

    @DisplayName("중복된 텃밭 정보가 저장되어도 최근의 하나만 저장됨을 확인할 수 있다.")
    @Test
    void addRecentViewGarden_distinctGarden() {
        // Given
        RecentViewGardens recentViews = new RecentViewGardens(new ArrayDeque<>());

        // When
        recentViews.addRecentViewGarden(GardenFixture.publicRecentViewGarden());
        recentViews.addRecentViewGarden(GardenFixture.privateRecentViewGarden());
        recentViews.addRecentViewGarden(GardenFixture.publicRecentViewGarden());

        // Then
        List<RecentViewGarden> recentViewGardenList = recentViews.getRecentViewGardens().recentViewGardens().stream().toList();
        assertThat(recentViewGardenList).
                containsExactly(GardenFixture.publicRecentViewGarden(),GardenFixture.privateRecentViewGarden());
    }
}
