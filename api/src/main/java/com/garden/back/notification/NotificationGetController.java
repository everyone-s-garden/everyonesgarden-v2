package com.garden.back.notification;

import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.notification.model.GetNotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/notification")
@RestController
public class NotificationGetController {

    private final NotificationGetApplication notificationGetApplication;

    @GetMapping("/all")
    public List<GetNotificationResponse> getAllNotifications(
            @CurrentUser LoginUser loginUser
    ) {
        return notificationGetApplication
                .getAll(loginUser.memberId())
                .stream()
                .map(GetNotificationResponse::of)
                .collect(Collectors.toList())
                ;
    }

    @GetMapping("/new")
    public List<GetNotificationResponse> getNewNotifications(
            @CurrentUser LoginUser loginUser
    ) {
        return notificationGetApplication
                .getAllUnread(loginUser.memberId())
                .stream()
                .map(GetNotificationResponse::of)
                .collect(Collectors.toList())
                ;
    }

    @GetMapping("/new/poll")
    public int poll(
            @CurrentUser LoginUser loginUser
    ) {
        return notificationGetApplication
                .pollUnreadCount(loginUser.memberId());
    }
}
