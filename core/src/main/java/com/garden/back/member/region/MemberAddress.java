package com.garden.back.member.region;

import com.garden.back.region.Address;
import com.mysema.commons.lang.Assert;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member_addresses")
public class MemberAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Address address;

    @Column(name = "member_id")
    private Long memberId;

    private MemberAddress(Address address, Long memberId) {
        Assert.notNull(address, "주소는 null값이 안됩니다.");
        Assert.notNull(memberId, "어떤 사용자의 주소인지 입력해주세요.");
        this.address = address;
        this.memberId = memberId;
    }

    public static MemberAddress create(Address address, Long memberId) {
        return new MemberAddress(address, memberId);
    }
}
