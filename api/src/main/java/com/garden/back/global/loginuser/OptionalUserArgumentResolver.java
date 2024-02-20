package com.garden.back.global.loginuser;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class OptionalUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(OptionalUser.class)
            && parameter.getParameterType().equals(LoginUser.class);
    }


    //로그인 하지 않은 사용자인 경우 회원 아이디에 -1이 들어감
    @SuppressWarnings("NullableProblems")
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            return LoginUser.from(() -> -1L);
        }
        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
        return LoginUser.from(memberInfo);
    }
}
