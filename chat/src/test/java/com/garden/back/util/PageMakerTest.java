package com.garden.back.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PageMakerTest {

    @Test
    void makePage_minusPageNumber_throwException(){
        assertThatThrownBy(() -> PageMaker.makePage(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}