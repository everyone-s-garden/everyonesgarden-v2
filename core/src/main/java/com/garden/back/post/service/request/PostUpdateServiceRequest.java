package com.garden.back.post.service.request;

import com.garden.back.post.domain.Post;
import com.garden.back.post.domain.PostType;
import com.garden.back.post.domain.repository.request.PostUpdateRepositoryRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostUpdateServiceRequest(
    List<MultipartFile> addedImages,
    String title,
    String content,
    List<String> deleteImages,
    PostType postType
) {
    public Integer addedImagesCount() {
        return addedImages.size();
    }

    public Integer deletedImagesCount() {
        return deleteImages.size();
    }

    public PostUpdateRepositoryRequest toRepositoryRequest(Post post, Long memberId, List<String> addedImages) {
        return new PostUpdateRepositoryRequest(post, memberId, addedImages, title, content, deleteImages, postType);
    }
}
