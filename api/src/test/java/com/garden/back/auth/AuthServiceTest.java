package com.garden.back.auth;

import com.garden.back.auth.jwt.response.TokenResponse;
import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.member.Member;
import com.garden.back.member.repository.MemberRepository;
import com.garden.back.member.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Transactional
class AuthServiceTest extends IntegrationTestSupport {

    @Autowired
    AuthService authService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void login() {
        //given
        TokenResponse token = sut.giveMeOne(TokenResponse.class);
        Member expected = sut.giveMeBuilder(Member.class)
            .set("id", 1L)
            .set("email", "adsf@example.com")
            .set("nickname", "김도연")
            .set("role", Role.USER)
            .sample();
        given(tokenProvider.generateTokenDto(any(Member.class))).willReturn(token);
        given(authRegistrations.get(any(AuthProvider.class))).willReturn(kakaoMemberProvider);
        given(kakaoMemberProvider.getMember(anyString())).willReturn(expected);

        //when
        authService.login(AuthProvider.KAKAO, "asdfasdf");

        //then
        assertThat(memberRepository.findByEmail(expected.getEmail()))
            .hasValueSatisfying(member -> assertThat(member)
                .extracting("id", "email", "nickname", "role")
                .containsExactly(expected.getId(), expected.getEmail(), expected.getNickname(), expected.getRole()));
    }
}