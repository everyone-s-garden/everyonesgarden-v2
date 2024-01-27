package com.garden.back.alarm;

import com.garden.back.crop.domain.event.AssignBuyerEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class CropNotifier {

    @TransactionalEventListener
    @Async
    public void sendReviewNotificationToBuyerAndSeller(AssignBuyerEvent event) {
        //TODO: AFTER COMMIT 옵션으로 이벤트 받아서 구매한 사람, 판매한 사람 양쪽에 리뷰 쓰라고 알람 보내기  NOSONAR
    }
}
