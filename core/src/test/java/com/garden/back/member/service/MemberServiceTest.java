package com.garden.back.member.service;

import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.member.Member;
import com.garden.back.member.Role;
import com.garden.back.member.repository.MemberRepository;
import com.garden.back.member.service.dto.MemberMyPageResult;
import org.geolatte.geom.M;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @DisplayName("마이 페이지 조회시 회원에 대한 기본 정보를 제공한다.")
    @Test
    void getMyMember() {
        // Given
        Member member = Member.create(
                "j234534a@naver.com",
                "금쪽이",
                Role.USER
        );
        Member savedMember = memberRepository.save(member);

        // When
        MemberMyPageResult myMember = memberService.getMyMember(savedMember.getId());

        // Then
        assertThat(savedMember.getNickname()).isEqualTo(myMember.nickname());
    }
}