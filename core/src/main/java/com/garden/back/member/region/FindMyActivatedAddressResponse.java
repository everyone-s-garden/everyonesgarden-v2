package com.garden.back.member.region;

import com.garden.back.region.Address;

public record FindMyActivatedAddressResponse(
    Long id,
    String sido,
    String sigungu,
    String upmyeondong
) {
    public static FindMyActivatedAddressResponse from(MemberAddress memberAddress) {
        Address address = memberAddress.getAddress();
        return new FindMyActivatedAddressResponse(memberAddress.getId(), address.getSido(), address.getSigungu(), address.getUpmyeondong());
    }
}
