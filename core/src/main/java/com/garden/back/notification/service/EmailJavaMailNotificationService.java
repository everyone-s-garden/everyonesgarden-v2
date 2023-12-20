package com.garden.back.notification.service;

import com.garden.back.notification.domain.Notification;
import com.garden.back.notification.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailJavaMailNotificationService implements NotificationService{

    @Value("example@gmail.com")
    private String OFFICIAL_EMAIL;
    private final JavaMailSender javaMailSender;

    // TODO : Guarantee at least once delivery by retry
    @Async
    @Override
    public void send(Notification notification) {
        if (!EmailUtils.isEmailAddress(notification.recipient())) {
            throw new IllegalStateException("Recipient must be an email address");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(OFFICIAL_EMAIL);
        message.setTo((String) notification.recipient());
        message.setSubject(notification.title());
        message.setText(notification.content());

        javaMailSender.send(message);
    }
}
