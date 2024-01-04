package com.garden.back.member;

import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.testutil.member.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("아이디로 검색하면 별명을 반환한다.")
    @Test
    void findNickName() {
        // Given
        Member member = MemberFixture.member();
        Member savedMember = memberRepository.save(member);

        // When
        String nickName = memberService.findNickname(savedMember.getId());

        // Then
        assertThat(nickName).isEqualTo(member.getNickname());
    }
}
