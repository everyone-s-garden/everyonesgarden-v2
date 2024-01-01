package com.graden.back.auth;

import com.garden.back.member.Member;
import com.garden.back.member.MemberRepository;
import com.graden.back.auth.client.MemberProvider;
import com.graden.back.auth.jwt.TokenProvider;
import com.graden.back.auth.jwt.response.RefreshTokenResponse;
import com.graden.back.auth.jwt.response.TokenResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final Map<AuthProvider, MemberProvider> authRegistrations;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public AuthService(
        Map<AuthProvider, MemberProvider> authRegistrations,
        MemberRepository memberRepository,
        TokenProvider tokenProvider
    ) {
        this.authRegistrations = authRegistrations;
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public TokenResponse login(AuthProvider authProvider, String accessToken) {
        Member member = authRegistrations.get(authProvider).getMember(accessToken);
        Member savedMember = saveOrUpdate(member);
        return tokenProvider.generateTokenDto(savedMember);
    }

    private Member saveOrUpdate(Member member) {
        Member savedMember = memberRepository.findByEmail(member.getEmail()).orElse(member);
        return memberRepository.save(savedMember);
    }

    public RefreshTokenResponse generateAccessToken(String refreshToken) {
        String generatedToken = tokenProvider.generateAccessToken(refreshToken);
        return new RefreshTokenResponse(generatedToken);
    }
}
