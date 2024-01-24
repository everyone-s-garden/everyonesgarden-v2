package com.garden.back.member.region;

import com.garden.back.region.Address;

public record UpdateMyRegionServiceRequest(
    Double latitude,
    Double longitude,
    Long memberId
) {

    public MemberAddress toEntity(Address address) {
        return MemberAddress.create(address, memberId);
    }
}
