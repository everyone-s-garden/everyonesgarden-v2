package com.garden.back.post.service.request;

import com.garden.back.post.domain.Post;
import com.garden.back.post.domain.PostType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostCreateServiceRequest(
    String title,
    String content,
    PostType postType,
    List<MultipartFile> images
) {
    public Post toEntity(Long postAuthorId, List<String> postImageUrls) {
        return Post.create(title, content, postAuthorId, postImageUrls, postType);
    }
}
