package com.garden.back.garden.domain;

import com.garden.back.garden.domain.dto.MyManagedGardenUpdateDomainRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.locationtech.jts.util.AssertionFailedException;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MyManagedGardenTest {

    @DisplayName("memberId가 0이거나 0보다 작으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    void throwException_zeroOrNegative_memberId(Long memberId) {
        assertThrows(RuntimeException.class,
            () -> MyManagedGarden.of(
                1L,
                "김별텃밭",
                LocalDate.of(2023, 12, 12),
                memberId,
                "https://kr.object.ncloudstorage.com/every-garden/images/garden/view.jpg",
                "최고"
            ));
    }

    @DisplayName("myManagedId가 0이거나 0보다 작으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    void throwException_zeroOrNegative_myManagedId(Long myManagedId) {
        assertThrows(RuntimeException.class,
            () -> MyManagedGarden.of(
                myManagedId,
                "김별텃밭",
                LocalDate.of(2023, 12, 12),
                1L,
                "https://kr.object.ncloudstorage.com/every-garden/images/garden/view.jpg",
                "이번에 토마토를 심었어요"
            ));
    }

    @DisplayName("myManagedGardenName이 빈값이면 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void throwException_zeroOrNegative_myManagedId(String myManagedGardenName) {
        assertThrows(RuntimeException.class,
            () -> MyManagedGarden.of(
                1L,
                myManagedGardenName,
                LocalDate.of(2023, 12, 12),
                1L,
                "https://kr.object.ncloudstorage.com/every-garden/images/garden/view.jpg",
                "이번에 토마토를 심었어요"
            ));
    }

    @DisplayName("업데이트 할 때 올바른 요청값인지 검증한다.")
    @ParameterizedTest
    @MethodSource("invalidMyManagedGardenUpdateDomainRequest")
    void throwException_update_invalidRequest(MyManagedGardenUpdateDomainRequest request) {
        MyManagedGarden myManagedGarden = myManagedGarden();

        assertThrows(RuntimeException.class,
            () -> myManagedGarden.update(request));
    }

    private static Stream<MyManagedGardenUpdateDomainRequest> invalidMyManagedGardenUpdateDomainRequest() {
        return Stream.of(
            new MyManagedGardenUpdateDomainRequest(
                "",
                "https://kr.object.ncloudstorage.com/every-garden/images/garden/view.jpg",
                LocalDate.of(2023, 12, 12),
                100L,
                "이번에 토마토를 심었어요"
            ),
            new MyManagedGardenUpdateDomainRequest(
                null,
                "https://kr.object.ncloudstorage.com/every-garden/images/garden/view.jpg",
                LocalDate.of(2023, 12, 16),
                1L,
                "이번에 토마토를 심었어요"
            )
        );
    }

    private MyManagedGarden myManagedGarden() {
        return MyManagedGarden.of(
            1L,
            "김별텃밭",
            LocalDate.of(2023, 12, 12),
            1L,
            "https://kr.object.ncloudstorage.com/every-garden/images/garden/view.jpg",
            "이번에 토마토를 심었어요"
        );
    }

}
