package com.garden.back.member;

import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String findNickname(Long memberId) {
        return memberRepository.findNickName(memberId);
    }

}
