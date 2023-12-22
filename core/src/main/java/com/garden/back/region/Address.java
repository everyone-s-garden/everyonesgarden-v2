package com.garden.back.region;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    protected Address() {}

    @Column(name = "sido", nullable = false)
    private String sido;

    @Column(name = "sigungu", nullable = false)
    private String sigungu;

    @Column(name = "upmyeondong", nullable = false)
    private String upmyeondong;

    public Address(String sido, String sigungu, String upmyeondong) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.upmyeondong = upmyeondong;
    }
}