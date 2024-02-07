package com.garden.back.api.user;

import com.garden.back.api.user.request.GetMemberByMannerGradeOrderRequest;
import com.garden.back.api.user.request.GetMemberByPostCountRequest;
import com.garden.back.domain.user.AdminMemberService;
import com.garden.back.domain.user.response.FindAllByMemberMannerGradeResponse;
import com.garden.back.domain.user.response.FindAllByPostCountResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin/members")
public class AdminUserController {

    private final AdminMemberService adminMemberService;

    public AdminUserController(AdminMemberService adminMemberService) {
        this.adminMemberService = adminMemberService;
    }

    @GetMapping(value = "/post-count", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FindAllByPostCountResponse> getMemberByPostCount(@ModelAttribute @Valid GetMemberByPostCountRequest request) {
        return ResponseEntity.ok(adminMemberService.findAllMemberByPostCountOrder(request.toServiceRequest()));
    }

    @GetMapping(value = "/member-manner-grade", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FindAllByMemberMannerGradeResponse> getMemberByMannerGradeOrder(@ModelAttribute @Valid GetMemberByMannerGradeOrderRequest request) {
        return ResponseEntity.ok(adminMemberService.findAllByMemberMannerGrade(request.toServiceRequest()));
    }
}
