package com.garden.back.member;

import com.garden.back.member.service.MemberService;
import com.garden.back.review.CropPostReview;
import com.garden.back.review.CropPostReviewCreateEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UpdateMemberMannerScoreAfterCropReviewEventHandler {

    private final MemberService memberService;

    public UpdateMemberMannerScoreAfterCropReviewEventHandler(MemberService memberService) {
        this.memberService = memberService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handle(CropPostReviewCreateEvent event) {
        CropPostReview cropPostReview = event.cropPostReview();
        cropPostReview.getReviewReceiverId();

        memberService.updateMannerScore(
            cropPostReview.getReviewReceiverId(),
            cropPostReview.getReviewScore()
        );
    }
}
