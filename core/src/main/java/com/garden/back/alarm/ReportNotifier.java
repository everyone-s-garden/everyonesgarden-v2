package com.garden.back.alarm;

import com.garden.back.report.domain.comment.CommentReportCreateEvent;
import com.garden.back.report.domain.crop.CropPostReportCreateEvent;
import com.garden.back.report.domain.garden.GardenReportCreatedEvent;
import com.garden.back.report.domain.post.PostReportCreateEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
// TODO: 신고 받으면 알리기 NOSONAR
public class ReportNotifier {

    @TransactionalEventListener
    @Async
    public void sendAlarmToCropPostReportReceiver(CropPostReportCreateEvent event) {
        // 이 메소드는 CropPostReportCreateEvent에 대한 알람을 보내는 로직을 구현할 예정입니다.
        // 현재는 비어있으며, 필요한 로직을 이곳에 구현해야 합니다.
    }

    @TransactionalEventListener
    @Async
    public void sendAlarmToPostReportReceiver(PostReportCreateEvent event) {
        // 이 메소드는 PostReportCreateEvent에 대한 알람을 보내는 로직을 구현할 예정입니다.
        // 현재는 구현되지 않았으며, 이벤트 수신 후 처리할 로직을 여기에 추가해야 합니다.
    }

    @TransactionalEventListener
    @Async
    public void sendAlarmToGardenReportReceiver(GardenReportCreatedEvent event) {
        // 이 메소드는 GardenReportCreatedEvent에 대한 알람을 처리하는 로직을 포함하게 됩니다.
        // 현재는 아직 구현되지 않았으며, 이벤트를 받았을 때 수행할 작업을 여기에 추가해야 합니다.
    }

    @TransactionalEventListener
    @Async
    public void sendAlarmToCommentReportReceiver(CommentReportCreateEvent event) {
        // 이 메소드는 CommentReportCreateEvent에 대한 알람을 보내는 로직을 구현할 예정입니다.
        // 현재 구현이 되지 않았으며, 이벤트 처리 로직을 이곳에 구현해야 합니다.
    }
}
