package com.garden.back.region;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    protected Address() {}

    @Column(name = "sido")
    private String sido;

    @Column(name = "sigungu")
    private String sigungu;

    @Column(name = "upmyeondong")
    private String upmyeondong;

    public Address(String sido, String sigungu, String upmyeondong) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.upmyeondong = upmyeondong;
    }

    @Override
    public String toString() {
        return sido + " " + sigungu + " " + upmyeondong;
    }

}