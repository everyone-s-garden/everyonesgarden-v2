package com.garden.back.global.loginuser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OptionalUserArgumentResolverTest {

    OptionalUserArgumentResolver resolver = new OptionalUserArgumentResolver();

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @DisplayName("@OptionalUser 어노테이션이 붙은 어노테이션은 ArgumentResolver가 동작한다.")
    @Test
    void supportsParameterWithCorrectConditions() {
        MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.hasParameterAnnotation(OptionalUser.class)).thenReturn(true);
        when(parameter.getParameterType()).thenReturn((Class) LoginUser.class); // 수정된 부분

        OptionalUserArgumentResolver resolver = new OptionalUserArgumentResolver();

        boolean supports = resolver.supportsParameter(parameter);

        assertThat(supports).isTrue();
    }


    @DisplayName("인증된 유저는 securityContextHolder에 등록된 유저의 아이디가 반환된다.")
    @Test
    void resolveArgumentWithAuthenticatedUser() {
        MemberInfo memberInfo = () -> 1L;
        Authentication auth = new UsernamePasswordAuthenticationToken(memberInfo, null, null);

        SecurityContextHolder.getContext().setAuthentication(auth);

        MethodParameter parameter = mock(MethodParameter.class);
        ModelAndViewContainer mavContainer = mock(ModelAndViewContainer.class);
        NativeWebRequest webRequest = mock(NativeWebRequest.class);

        LoginUser result = (LoginUser) resolver.resolveArgument(parameter, mavContainer, webRequest, null);

        assertThat(result.memberId()).isEqualTo(1L);
    }

    @DisplayName("인증되지 않은 유저는 argumentResolver가 -1을 반환한다.")
    @Test
    void resolveArgumentWithUnauthenticatedUser() {
        Authentication auth = new UsernamePasswordAuthenticationToken("anonymousUser", null, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        MethodParameter parameter = mock(MethodParameter.class);
        ModelAndViewContainer mavContainer = mock(ModelAndViewContainer.class);
        NativeWebRequest webRequest = mock(NativeWebRequest.class);

        LoginUser result = (LoginUser) resolver.resolveArgument(parameter, mavContainer, webRequest, null);

        assertThat(result.memberId()).isEqualTo(-1L);
    }
}
