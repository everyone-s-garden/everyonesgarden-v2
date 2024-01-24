package com.garden.back.member.region;

import com.garden.back.global.GeometryUtil;
import com.garden.back.member.repository.MemberAddressRepository;
import com.garden.back.region.Address;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberRegionService {

    private final MemberAddressRepository memberAddressRepository;
    private static final int MAXIMUM_REGION_COUNT = 3;

    public MemberRegionService(MemberAddressRepository memberAddressRepository) {
        this.memberAddressRepository = memberAddressRepository;
    }

    @Transactional
    public Long registerMyAddress(UpdateMyRegionServiceRequest request) {
        if (memberAddressRepository.countByMemberId(request.memberId()) > MAXIMUM_REGION_COUNT) {
            throw new IllegalArgumentException("사용자가 사는 지역은 " + MAXIMUM_REGION_COUNT + "까지 등록 가능합니다.");
        }
        Address address = memberAddressRepository.findByPointContains(GeometryUtil.createPoint(request.latitude(), request.longitude()));
        MemberAddress memberAddress = memberAddressRepository.save(request.toEntity(address));

        return memberAddress.getId();
    }

    public void deleteMyAddress(Long addressId) {
        MemberAddress memberAddress = memberAddressRepository.findById(addressId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id 입니다.(addressId)"));
        memberAddressRepository.delete(memberAddress);
    }

    public FindAllMyAddressResponse findAllMyAddresses(Long memberId) {

        List<FindAllMyAddressResponse.AddressInfo> addressInfos = memberAddressRepository.findAllByMemberId(memberId)
            .stream()
            .map(FindAllMyAddressResponse.AddressInfo::from)
            .toList();

        return FindAllMyAddressResponse.from(addressInfos);
    }

    public FindMyCurrentRegionResponse findMyCurrentRegions(String latitude, String longitude) {
        Address address = memberAddressRepository.findByPointContains(GeometryUtil.createPoint(Double.valueOf(latitude), Double.valueOf(
            longitude)));

        return new FindMyCurrentRegionResponse(address.getFullAddress());
    }
}
