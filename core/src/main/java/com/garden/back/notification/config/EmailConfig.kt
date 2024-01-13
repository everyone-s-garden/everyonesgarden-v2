package com.garden.back.notification.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
open class EmailConfig {

    @Bean
    open fun javaMailSender(): JavaMailSender {
        return JavaMailSenderImpl().apply {}
    }
}
