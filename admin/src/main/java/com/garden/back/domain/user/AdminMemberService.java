package com.garden.back.domain.user;

import com.garden.back.domain.user.request.FindAllMemberByMannerGradeRequest;
import com.garden.back.domain.user.request.FindAllMemberByPostCountRequest;
import com.garden.back.domain.user.response.FindAllByMemberMannerGradeResponse;
import com.garden.back.domain.user.response.FindAllByPostCountResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminMemberService {

    private final AdminMemberRepository adminMemberRepository;

    public AdminMemberService(AdminMemberRepository adminMemberRepository) {
        this.adminMemberRepository = adminMemberRepository;
    }

    public FindAllByPostCountResponse findAllMemberByPostCountOrder(FindAllMemberByPostCountRequest request) {
        return FindAllByPostCountResponse.from(
            adminMemberRepository.findAllByPostCountDesc(request.offset(), request.limit())
        );
    }

    public FindAllByMemberMannerGradeResponse findAllByMemberMannerGrade(FindAllMemberByMannerGradeRequest request) {
        Pageable pageable = PageRequest.of(request.offset(), request.limit());

        return FindAllByMemberMannerGradeResponse.from(
            adminMemberRepository.findAllByMemberMannerGrade(request.memberMannerGrade(), pageable)
        );
    }
}
