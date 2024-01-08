package com.garden.back.service;

import org.apache.catalina.security.SecurityConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class IntegrationTestSupport {

    @MockBean
    protected SecurityConfig securityConfig;



}
