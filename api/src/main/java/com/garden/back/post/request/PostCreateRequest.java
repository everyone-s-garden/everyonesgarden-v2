package com.garden.back.post.request;

import com.garden.back.global.validation.EnumValue;
import com.garden.back.post.domain.PostType;
import com.garden.back.post.service.request.PostCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

public record PostCreateRequest(
    @NotBlank(message = "제목을 입력해주세요") @Length(min = 1, max = 255, message = "1~255 글자 사이의 제목을 입력해주세요.")
    String title,

    @NotBlank(message = "내용을 입력해주세요")
    String content,

    @EnumValue(enumClass = PostType.class, message = "INFORMATION_SHARE, GARDEN_SHOWCASE, QUESTION, ETC 중 하나만 가능합니다.")
    String postType

) {
    public PostCreateServiceRequest toServiceRequest(List<MultipartFile> images) {
        if (images == null) {
            images = Collections.emptyList();
        }

        if (images.size() > 10) {
            throw new IllegalArgumentException("게시글 한개당 10장의 이미지만 등록할 수 있습니다.");
        }

        return new PostCreateServiceRequest(title, content, PostType.valueOf(postType), images);
    }

}
