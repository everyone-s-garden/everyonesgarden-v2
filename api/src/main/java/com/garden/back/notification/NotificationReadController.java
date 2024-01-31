package com.garden.back.notification;

import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.notification.domain.Notification;
import com.garden.back.notification.model.GetNotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/notification")
@RestController
public class NotificationReadController {

    private final NotificationReadApplication notificationReadApplication;

    @PatchMapping("/{notificationId}/mark-as-read")
    public GetNotificationResponse markNewNotificationsAsRead(
            @CurrentUser LoginUser loginUser,
            @PathVariable Long notificationId
    ) {
        Notification modified = notificationReadApplication.markAsRead(
                loginUser.memberId(),
                notificationId
        );

        return GetNotificationResponse.of(modified);
    }
}
