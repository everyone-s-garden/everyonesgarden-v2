package com.garden.back.global.cache;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class FallbackResponseAspect {

    private final ExternalApiFallbackCache cache;

    public FallbackResponseAspect(ExternalApiFallbackCache cache) {
        this.cache = cache;
    }

    @Pointcut("@annotation(com.garden.back.global.cache.FallbackResponse)")
    public void methodAnnotatedWithFallbackResponse() {}

    @Pointcut("within(@com.garden.back.global.cache.FallbackResponse *)")
    public void classAnnotatedWithFallbackResponse() {}

    @Pointcut("methodAnnotatedWithFallbackResponse() || classAnnotatedWithFallbackResponse()")
    public void fallbackResponseMethods() {}

    @Around("fallbackResponseMethods()")
    public Object handleFallback(ProceedingJoinPoint joinPoint) throws Throwable {
        String cacheKey = generateCacheKey(joinPoint);

        try {
            // Proceed with the method execution
            Object result = joinPoint.proceed();
            // Store the result in the cache
            cache.put(cacheKey, result);
            return result;
        } catch (Throwable ex) {
            Object cachedResult = cache.get(cacheKey);
            log.warn("외부 api 에러 발생 일단 이전 캐싱한 응답 하겠음: {} 에러 메시지: {}", cachedResult, ex.getMessage());
            if (cachedResult != null) {
                return cachedResult;
            } else {
                log.error("캐시에도 값이 없어서 에러 터짐", ex);
                throw ex;
            }
        }
    }

    private String generateCacheKey(ProceedingJoinPoint joinPoint) {
        StringBuilder keyBuilder = new StringBuilder();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();

        keyBuilder.append(targetClass.getName()).append("#");
        keyBuilder.append(method.getName()).append("(");

        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            keyBuilder.append(arg != null ? arg.hashCode() : 0);
            if (i < args.length - 1) {
                keyBuilder.append(",");
            }
        }
        keyBuilder.append(")");

        return keyBuilder.toString();
    }
}
