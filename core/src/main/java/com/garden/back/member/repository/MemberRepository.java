package com.garden.back.member.repository;

import com.garden.back.member.Member;
import com.garden.back.member.repository.response.MemberInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @Query(
        """
          select m.nickname as nickname,
          m.profileImageUrl as profileImage,
          m.memberMannerGrade as memberMannerGrade,
          m.email as email
          from Member as m
          where m.id =:memberId
        """
    )
    MyPageMemberRepositoryResponse getMyPageMemberInfo(@Param("memberId") Long memberId);

    @Query(
        """
        select
         m.nickname as nickname
        from Member as m
        where m.id =:memberId
        """
    )
    String findNickName(@Param("memberId") Long memberId);

    @Query("""
           SELECT new com.garden.back.member.repository.response.MemberInfoResponse(m.nickname, m.profileImageUrl, m.memberMannerGrade, m.email)
           FROM Member m
           WHERE m.id = :id
           """)
    MemberInfoResponse findMemberById(@Param("id") Long id);

}
