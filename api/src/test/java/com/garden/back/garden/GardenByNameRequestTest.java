package com.garden.back.garden.dto;

import com.garden.back.garden.dto.request.GardenByNameRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GardenByNameRequestTest {

    @DisplayName("GardenByNameRequest의 검증 테스트")
    @ParameterizedTest
    @MethodSource("provideGardenByNameRequestForValidation")
    void gardenByNameRequest_validation(GardenByNameRequest request, boolean expectException) {
            assertDoesNotThrow(() -> GardenByNameRequest.to(request));
    }

    private static Stream<Arguments> provideGardenByNameRequestForValidation() {
        return Stream.of(
                Arguments.of(new GardenByNameRequest("진겸이네 텃밭", 0), false),
                Arguments.of(new GardenByNameRequest("도연이네 텃밭", 1), false));
    }
}
