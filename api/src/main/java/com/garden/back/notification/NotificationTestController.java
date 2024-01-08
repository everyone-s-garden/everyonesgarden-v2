package com.garden.back.notification;

import com.garden.back.notification.domain.slack.SlackChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("local")
@RequiredArgsConstructor
@RequestMapping("test")
@RestController
public class NotificationTestController {

    private final NotificationApplication notificationApplication;

    @PostMapping("email")
    public void email() {
        notificationApplication.toEmail("me@jinkyumpark.com", "TEST", "TEST");
    }

    @PostMapping("slack")
    public void slack() {
        notificationApplication.toSlack(SlackChannel.BOT, "TEST");
    }
}
