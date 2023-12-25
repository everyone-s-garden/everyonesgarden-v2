package com.garden.back.global;

import com.garden.back.auth.AuthProvider;
import com.garden.back.auth.client.MemberProvider;
import com.garden.back.auth.config.SecurityConfig;
import com.garden.back.auth.jwt.TokenProvider;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

@ActiveProfiles("test")
@SpringBootTest
public class IntegrationTestSupport extends FixtureSupport {

    @MockBean
    protected TokenProvider tokenProvider;

    @MockBean
    protected Map<AuthProvider, MemberProvider> authRegistrations;

    @MockBean
    protected MemberProvider memberProvider;

    @MockBean
    protected SecurityConfig securityConfig;
}
