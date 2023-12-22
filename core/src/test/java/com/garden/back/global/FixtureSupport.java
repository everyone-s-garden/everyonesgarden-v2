package com.garden.back.global;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FailoverIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;

import java.util.List;

/**
 * 프로젝트에 사용한 핵심 내용: https://www.notion.so/joyfulviper/FixtureMonkey-e555a9bc0639419e87bd10c7e32dfb24<br>
 * 공식문서: https://naver.github.io/fixture-monkey/docs/introduction/overview/ <br>
 * (레코드 클래스) 또는 (기본 생성자 + (getter 또는 setter))가 있는 클래스의 Fixture를 만들어 줌
 */
public abstract class FixtureSupport {
    protected final FixtureMonkey sut = FixtureMonkey.builder()
        .objectIntrospector(new FailoverIntrospector(
            List.of(
                FieldReflectionArbitraryIntrospector.INSTANCE,
                ConstructorPropertiesArbitraryIntrospector.INSTANCE
            )
        ))
        .defaultNotNull(true)
        .build();
}