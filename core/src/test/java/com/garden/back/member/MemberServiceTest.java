package com.garden.back.member;

import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.member.repository.MemberRepository;
import com.garden.back.member.service.MemberService;
import com.garden.back.member.service.dto.UpdateProfileServiceRequest;
import com.garden.back.testutil.member.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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

    @DisplayName("회원의 매너 점수를 올린다.")
    @Test
    void updateMannerScore() {
        //given
        Member member = MemberFixture.member();
        Member savedMember = memberRepository.save(member);

        //when
        memberService.updateMannerScore(savedMember.getId(), 20f);

        //then
        Member updatedMember = memberRepository.findById(member.getId()).orElseThrow(() -> new AssertionError("회원 조회 실패"));
        assertThat(updatedMember.getMemberMannerGrade()).isEqualTo(MemberMannerGrade.SPROUT);
    }

    @DisplayName("회원의 프로필을 수정한다.")
    @Test
    void updateProfile() {
        //given
        Member member = MemberFixture.member();
        Member savedMember = memberRepository.save(member);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "images",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        given(imageUploader.upload(any(), any())).willReturn(expectedUrl);
        UpdateProfileServiceRequest request = new UpdateProfileServiceRequest("닉네임", savedMember.getId(), Optional.of(mockMultipartFile));

        //when
        memberService.updateProfile(request);

        //then
        Member updatedMember = memberRepository.findById(savedMember.getId()).orElseThrow(() -> new AssertionError("회원 조회 실패"));
        assertThat(updatedMember)
            .extracting(Member::getNickname, Member::getProfileImageUrl)
            .containsExactly("닉네임", expectedUrl);
    }
}
