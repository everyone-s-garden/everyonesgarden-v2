package com.garden.back.post.request;

import com.garden.back.post.service.request.CommentUpdateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CommentUpdateRequest(
    @NotBlank(message = "댓글을 입력해 주세요.") @Length(min = 1, max = 255, message = "댓글은 최소 1글자 이상, 최대 255글자 이하로 작성해주세요.")
    String content
) {
    public CommentUpdateServiceRequest toServiceRequest() {
        return new CommentUpdateServiceRequest(content);
    }
}
