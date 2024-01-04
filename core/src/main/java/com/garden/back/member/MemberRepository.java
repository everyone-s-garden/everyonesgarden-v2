package com.garden.back.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @Query(
            """
            select
             m.nickname as nickname
            from Member as m
            where m.id =:memberId
            """
    )
    String findNickName(@Param("memberId") Long memberId);
}
