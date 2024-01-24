package com.garden.back.member.service.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public record UpdateProfileServiceRequest(
    String nickname,
    Long memberId,
    Optional<MultipartFile> multipartFile
) {
}
