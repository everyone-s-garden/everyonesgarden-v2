package com.garden.back.post.service.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostCreateServiceRequest(
    String title,
    String content,
    List<MultipartFile> images
) {
}
