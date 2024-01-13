package com.garden.back.post.request;

import com.garden.back.post.service.request.CommentCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record CommentCreateRequest(
    @NotBlank(message = "댓글 내용을 입력해주세요") @Length(min = 1, max = 255, message = "1글자 이상 255글자 이하의 글자수만 가능합니다.")
    String content,

    @Positive(message = "부모 댓글의 id는 양수만 가능합니다.")
    Long parentCommentId
) {
    public CommentCreateServiceRequest toServiceDto() {
        return new CommentCreateServiceRequest(content, parentCommentId);
    }
}
