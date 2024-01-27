package com.garden.back.alarm;

import com.garden.back.report.domain.comment.CommentReportCreateEvent;
import com.garden.back.report.domain.crop.CropPostReportCreateEvent;
import com.garden.back.report.domain.garden.GardenReportCreatedEvent;
import com.garden.back.report.domain.post.PostReportCreateEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

//TODO: 신고 받으면 알리기
@Component
public class ReportNotifier {

    @TransactionalEventListener
    @Async
    public void sendAlarmToCropPostReportReceiver(CropPostReportCreateEvent event) {

    }

    @TransactionalEventListener
    @Async
    public void sendAlarmToPostReportReceiver(PostReportCreateEvent event) {

    }

    @TransactionalEventListener
    @Async
    public void sendAlarmToGardenReportReceiver(GardenReportCreatedEvent event) {

    }

    @TransactionalEventListener
    @Async
    public void sendAlarmToCommentReportReceiver(CommentReportCreateEvent event) {

    }
}
