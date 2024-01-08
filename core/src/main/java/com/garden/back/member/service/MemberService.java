package com.garden.back.member.service;

import com.garden.back.member.repository.MemberRepository;
import com.garden.back.member.service.dto.MemberMyPageResult;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberMyPageResult getMyMember(Long memberId) {
        return MemberMyPageResult.to(memberRepository.getMyPageMemberInfo(memberId));
    }

    public String findNickname(Long memberId) {
        return memberRepository.findNickName(memberId);
    }


}
