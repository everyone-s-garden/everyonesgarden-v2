package com.garden.back.member;

import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.member.dto.MemberMyPageResponse;
import com.garden.back.member.dto.UpdateMyProfileRequest;
import com.garden.back.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PatchMapping(
        value = "/my",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    ResponseEntity<Void> updateMyProfile(
        @CurrentUser LoginUser loginUser,
        @RequestPart(value = "texts", required = true) @Valid UpdateMyProfileRequest request,
        @RequestPart(value = "image", required = false) MultipartFile multipartFile
    ) {
        memberService.updateProfile(request.toServiceRequest(loginUser.memberId(), multipartFile));
        return ResponseEntity.ok().build();
    }
}
