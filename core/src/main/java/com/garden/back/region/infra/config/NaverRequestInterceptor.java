package com.garden.back.region.infra.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

public class NaverRequestInterceptor implements RequestInterceptor {

    @Value("${api.reverseGeo.id}")
    private String naverApiId;

    @Value("${api.reverseGeo.secret}")
    private String naverApiSecret;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("X-NCP-APIGW-API-KEY-ID", naverApiId);
        requestTemplate.header("X-NCP-APIGW-API-KEY", naverApiSecret);
    }
}
