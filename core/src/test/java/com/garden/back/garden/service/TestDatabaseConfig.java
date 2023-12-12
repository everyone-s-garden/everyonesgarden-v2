package com.garden.back.garden.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@TestConfiguration
public class TestDatabaseConfig {

    @Bean
    public DatabaseInitializer databaseInitializer(JdbcTemplate jdbcTemplate) {
        return new DatabaseInitializer(jdbcTemplate);
    }
}
