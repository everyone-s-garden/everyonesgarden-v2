package com.garden.back.notification;

import com.garden.back.notification.domain.slack.SlackChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile("local")
@RequiredArgsConstructor
@RestController
public class NotificationTestController {

    private final NotificationApplication notificationApplication;

    @PostMapping("/test/email")
    public void email(@RequestParam String to) {
        notificationApplication.toEmail(to, "TEST", "TEST");
    }

    @PostMapping("/test/slack")
    public void slack() {
        notificationApplication.toSlack(SlackChannel.BOT, "TEST");
    }
}
