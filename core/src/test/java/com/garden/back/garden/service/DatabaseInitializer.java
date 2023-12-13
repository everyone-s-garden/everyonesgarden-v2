package com.garden.back.garden.service;

import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        initializeDatabase();
    }

    private void initializeDatabase() {
        String checkFulltextIndexQuery = "SELECT COUNT(*) FROM information_schema.statistics " +
                "WHERE table_schema = DATABASE() AND table_name = 'gardens' AND index_name = 'ft_index'";

        Integer fulltextIndexCount = jdbcTemplate.queryForObject(checkFulltextIndexQuery, Integer.class);

        if (fulltextIndexCount != null && fulltextIndexCount == 0) {
            String createIndexQuery = "CREATE FULLTEXT INDEX ft_index ON gardens (garden_name) WITH PARSER ngram";
            jdbcTemplate.execute(createIndexQuery);
        }

    }
}
