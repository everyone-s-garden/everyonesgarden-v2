package com.garden.back.region.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverFeignConfig {

    @Bean
    public NaverRequestInterceptor naverRequestInterceptor() {
        return new NaverRequestInterceptor();
    }
}
