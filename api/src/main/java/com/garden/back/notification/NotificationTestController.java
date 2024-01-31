package com.garden.back.notification;

import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
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

    private final NotificationSentApplication notificationSentApplication;

    @PostMapping("/test/email")
    public void email(
            @CurrentUser LoginUser loginUser,
            @RequestParam String to
    ) {
        notificationSentApplication.toEmail(
                loginUser.memberId(),
                to,
                "TEST",
                "TEST"
        );
    }

    @PostMapping("/test/slack")
    public void slack() {
        notificationSentApplication.toSlack(SlackChannel.BOT, "TEST");
    }
}
