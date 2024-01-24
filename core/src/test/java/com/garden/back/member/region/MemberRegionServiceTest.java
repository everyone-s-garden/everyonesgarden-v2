package com.garden.back.member.region;

import com.garden.back.global.MockTestSupport;
import com.garden.back.member.repository.MemberAddressRepository;
import com.garden.back.region.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class MemberRegionServiceTest extends MockTestSupport {

    @Mock
    MemberAddressRepository memberAddressRepository;

    @InjectMocks
    MemberRegionService memberRegionService;

    @DisplayName("나의 주소를 등록한다.")
    @Test
    void registerMyAddress() {
        // given
        UpdateMyRegionServiceRequest request = new UpdateMyRegionServiceRequest(37.1234, 1236.124, 1L);
        Address address = new Address("서울특별시", "강남구", "역삼동");
        MemberAddress expectedMemberAddress = sut.giveMeBuilder(MemberAddress.class)
            .set("id", 1L)
            .set("address", address)
            .set("memberId", 1L)
            .sample();

        given(memberAddressRepository.countByMemberId(anyLong())).willReturn(2L);
        given(memberAddressRepository.findByPointContains(any())).willReturn(address);
        given(memberAddressRepository.save(any(MemberAddress.class))).willReturn(expectedMemberAddress);

        // when
        Long result = memberRegionService.registerMyAddress(request);

        // then
        assertThat(result).isEqualTo(1L);
    }


    @DisplayName("나의 주소를 삭제한다.")
    @Test
    void deleteMyAddress() {
        // given
        Long addressId = 1L;
        Address address = new Address("서울특별시", "강남구", "역삼동");
        MemberAddress memberAddress = MemberAddress.create(address, 1L);

        given(memberAddressRepository.findById(addressId)).willReturn(Optional.of(memberAddress));

        // when
        memberRegionService.deleteMyAddress(addressId);

        // then
        then(memberAddressRepository).should().delete(any(MemberAddress.class));
    }


    @DisplayName("나의 주소를 조회한다.")
    @Test
    void findAllMyAddresses() {
        // given
        Long memberId = 1L;
        Address address = new Address("서울특별시", "강남구", "역삼동");
        List<MemberAddress> memberAddresses = List.of(MemberAddress.create(address, memberId));
        given(memberAddressRepository.findAllByMemberId(memberId)).willReturn(memberAddresses);

        // when
        FindAllMyAddressResponse result = memberRegionService.findAllMyAddresses(memberId);

        // then
        assertThat(result.addressInfos()).hasSize(memberAddresses.size());
    }

    @DisplayName("나의 현재 주소를 조회한다.")
    @Test
    void findMyCurrentRegions() {
        Address address = new Address("서울시", "성동구", "금호동");
        given(memberAddressRepository.findByPointContains(any())).willReturn(address);

        FindMyCurrentRegionResponse response = memberRegionService.findMyCurrentRegions("37.567924", "127.07849");

        assertThat(address.getFullAddress()).isEqualTo(response.address());

    }
}