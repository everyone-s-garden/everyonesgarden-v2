package com.garden.back.post.service.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostUpdateServiceRequest(
    List<MultipartFile> addedImages,
    String title,
    String content,
    List<String> deleteImages
) {
}
