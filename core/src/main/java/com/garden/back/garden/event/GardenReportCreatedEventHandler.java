package com.garden.back.garden.event;

import com.garden.back.report.domain.GardenReportCreatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class GardenReportCreatedEventHandler {

    // gardenServcie 완료되면 만들기
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void updateGardenScore(GardenReportCreatedEvent event) {

    }
}
