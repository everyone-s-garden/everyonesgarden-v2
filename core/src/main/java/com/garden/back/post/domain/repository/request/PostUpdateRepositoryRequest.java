package com.garden.back.post.domain.repository.request;

import com.garden.back.post.domain.Post;

import java.util.List;

public record PostUpdateRepositoryRequest(
    Post post,
    Long memberId,
    List<String> addedImages,
    String title,
    String content,
    List<String> deleteImages
) {}
