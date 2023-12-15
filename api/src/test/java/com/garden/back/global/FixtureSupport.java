package com.garden.back.global;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FailoverIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;

import java.util.List;

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