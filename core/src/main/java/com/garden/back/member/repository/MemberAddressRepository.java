package com.garden.back.member.repository;

import com.garden.back.member.region.MemberAddress;
import com.garden.back.region.Address;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long> {

    Long countByMemberId(Long memberId);

    List<MemberAddress> findAllByMemberId(Long memberId);

    @Query("select r.address from Region as r where ST_Contains(r.area, :point)")
    Address findByPointContains(@Param("point") Point point);
}
