package com.garden.back.feedback;

import com.garden.back.global.FixtureSupport;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FeedbackImagesTest extends FixtureSupport {

    @Test
    public void shouldThrowExceptionWhenMoreThan10Images() {
        // given
        List<FeedbackImage> images = sut.giveMeBuilder(FeedbackImage.class).sampleList(11);

        // when & then
        assertThatThrownBy(() -> FeedbackImages.from(images))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("피드백 사진은 10장까지 등록이 가능합니다.");
    }
}
