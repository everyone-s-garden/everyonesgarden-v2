package com.garden.back.domain.user;

import com.garden.back.domain.user.request.FindAllMemberByMannerGradeRequest;
import com.garden.back.domain.user.request.FindAllMemberByPostCountRequest;
import com.garden.back.domain.user.response.FindAllByMemberMannerGradeResponse;
import com.garden.back.domain.user.response.FindAllByPostCountResponse;
import com.garden.back.global.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class AdminMemberServiceTest extends IntegrationTestSupport {

    @Autowired
    AdminMemberService adminMemberService;

    @Autowired
    AdminMemberRepository adminMemberRepository;

    @Test
    @DisplayName("게시글을 많이 작성한 순으로 회원을 조회할 수 있다.")
    @Sql("classpath:init-post.sql")
    void findAllMemberByPostCount() {
        //given
        FindAllMemberByPostCountRequest request = new FindAllMemberByPostCountRequest(0, 10);

        //when
        FindAllByPostCountResponse actual = adminMemberService.findAllMemberByPostCountOrder(request);

        //then
        FindAllByPostCountResponse expected = new FindAllByPostCountResponse(Collections.EMPTY_LIST);
        assertThat(actual).isEqualTo(expected);

    }

    @DisplayName("등급별로 회원을 조회한다.")
    @Test
    void findAllByMemberMannerGrade() {
        //given
        FindAllMemberByMannerGradeRequest request = new FindAllMemberByMannerGradeRequest(MemberMannerGrade.SEED, 0, 10);

        //when
        FindAllByMemberMannerGradeResponse actual = adminMemberService.findAllByMemberMannerGrade(request);

        //then
        FindAllByMemberMannerGradeResponse expected = new FindAllByMemberMannerGradeResponse(
            adminMemberRepository.findAll()
            .stream()
            .filter(member -> member.getMannerScore().equals(MemberMannerGrade.SEED))
            .map(member -> new FindAllByMemberMannerGradeResponse.Result(member.getId(), member.getNickname()))
            .toList()
        );

        assertThat(expected).isEqualTo(actual);
    }
}