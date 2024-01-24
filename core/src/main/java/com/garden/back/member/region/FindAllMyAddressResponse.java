package com.garden.back.member.region;

import com.garden.back.region.Address;

import java.util.List;

public record FindAllMyAddressResponse(
    List<AddressInfo> addressInfos
) {
    public record AddressInfo(
        Long addressId,
        String sido,
        String sigungu,
        String upmyeondong
    ) {
        public static AddressInfo from(MemberAddress memberAddress) {
            Address address = memberAddress.getAddress();
            return new AddressInfo(memberAddress.getId(), address.getSido(), address.getSigungu(), address.getUpmyeondong());
        }
    }

    public static FindAllMyAddressResponse from(List<AddressInfo> addressInfos) {
        return new FindAllMyAddressResponse(addressInfos);
    }
}
