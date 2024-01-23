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

    @Column(name = "full_address")
    private String fullAddress;

    public Address(String sido, String sigungu, String upmyeondong) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.upmyeondong = upmyeondong;
        this.fullAddress = sido + " " + sigungu + " " + upmyeondong;
    }

    @Override
    public String toString() {
        return fullAddress;
    }

}