package com.garden.back.post.request;

import com.garden.back.global.validation.EnumValue;
import com.garden.back.post.domain.PostType;
import com.garden.back.post.service.request.PostUpdateServiceRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public record PostUpdateRequest(
    String title,
    String content,
    List<String> deleteImages,

    @EnumValue(enumClass = PostType.class)
    String postType
) {

    public PostUpdateServiceRequest toServiceDto(List<MultipartFile> images) {
        if (images == null) {
            images = new ArrayList<>();
        }

        return new PostUpdateServiceRequest(images, title, content, deleteImages, PostType.valueOf(postType));
    }
}
