package com.garden.back.member.dto;

import com.garden.back.member.service.dto.UpdateProfileServiceRequest;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public record UpdateMyProfileRequest(
    @NotBlank(message = "닉네임을 입력해 주세요")
    String nickname
) {
    public UpdateProfileServiceRequest toServiceRequest(Long memberId, MultipartFile multipartFile) {
        return new UpdateProfileServiceRequest(nickname, memberId, Optional.ofNullable(multipartFile));
    }
}
