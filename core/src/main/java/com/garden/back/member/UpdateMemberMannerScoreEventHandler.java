package com.garden.back.member;

import com.garden.back.member.service.MemberService;
import com.garden.back.review.CropPostReviewCreateEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UpdateMemberMannerScoreEventHandler {

    private final MemberService memberService;

    public UpdateMemberMannerScoreEventHandler(MemberService memberService) {
        this.memberService = memberService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handle(CropPostReviewCreateEvent event) {
        memberService.updateMannerScore(
            event.reviewReceiverId(),
            event.reviewScore()
        );
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handle(ChatReportEvent event) {
        memberService.updateMannerScore(
            event.reportedMemberId(),
            event.score()
        );
    }

}
