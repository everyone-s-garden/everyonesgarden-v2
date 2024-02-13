package com.garden.back.member.service;

import com.garden.back.global.image.ParallelImageUploader;
import com.garden.back.member.Member;
import com.garden.back.member.repository.MemberRepository;
import com.garden.back.member.repository.response.MemberInfoResponse;
import com.garden.back.member.service.dto.MemberMyPageResult;
import com.garden.back.member.service.dto.UpdateProfileServiceRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ParallelImageUploader imageUploader;

    public MemberService(
        MemberRepository memberRepository,
        ParallelImageUploader imageUploader
    ) {
        this.memberRepository = memberRepository;
        this.imageUploader = imageUploader;
    }

    public MemberMyPageResult getMyMember(Long memberId) {
        return MemberMyPageResult.to(memberRepository.getMyPageMemberInfo(memberId));
    }

    public String findNickname(Long memberId) {
        return memberRepository.findNickName(memberId);
    }

    @Transactional
    public void updateMannerScore(Long id, Float score) {
        memberRepository.findById(id).orElseThrow()
            .updateMannerScore(score);
    }

    @Transactional
    public void updateProfile(UpdateProfileServiceRequest serviceRequest) {
        Optional<String> uploadedImages = serviceRequest.multipartFile()
            .map(multipartFiles -> imageUploader.upload("/members", List.of(multipartFiles)).get(0));

        Member member = memberRepository.findById(serviceRequest.memberId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));
        if (member.getProfileImageUrl() != null) {
            imageUploader.delete("/members", List.of(member.getProfileImageUrl()));
        }
        member.updateProfile(serviceRequest.nickname(), uploadedImages.orElse(null));
    }

    public MemberInfoResponse findMemberById(Long id) {
        return memberRepository.findMemberById(id);
    }
}
