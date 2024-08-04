package com.garden.back.global.jpa;

import org.hibernate.dialect.H2Dialect;

public class CustomH2Dialect extends H2Dialect {
    @Override
    public boolean hasAlterTable() {
        return false;
    }
}
