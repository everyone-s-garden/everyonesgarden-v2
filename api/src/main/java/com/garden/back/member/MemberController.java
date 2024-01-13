package com.garden.back.member;

import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.member.dto.MemberMyPageResponse;
import com.garden.back.member.service.MemberService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(
            path = "/my",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<MemberMyPageResponse> getMyPage(@CurrentUser LoginUser loginUser) {
        return ResponseEntity.ok(
                MemberMyPageResponse.to(
                        memberService.getMyMember(loginUser.memberId())));
    }
}
