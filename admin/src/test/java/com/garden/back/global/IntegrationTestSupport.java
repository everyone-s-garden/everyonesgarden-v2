package com.garden.back.global;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = NONE)
@Execution(ExecutionMode.SAME_THREAD)
public abstract class IntegrationTestSupport {

}
