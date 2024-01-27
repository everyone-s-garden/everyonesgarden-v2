package com.garden.back.post.service;

import com.garden.back.report.domain.comment.CommentReportCreateEvent;
import com.garden.back.report.domain.post.PostReportCreateEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class DeletePostAfterPostReportCreateEvent {

    private final PostCommandService postCommandService;

    public DeletePostAfterPostReportCreateEvent(PostCommandService postCommandService) {
        this.postCommandService = postCommandService;
    }

    @TransactionalEventListener
    @Async
    public void deletePost(PostReportCreateEvent event) {
        postCommandService.deletePostByReport(event.postReport().getPostId());
    }

    @TransactionalEventListener
    @Async
    public void deletePostComment(CommentReportCreateEvent event) {
        postCommandService.deletePostCommentByReport(event.commentReport().getCommentId());
    }
}
