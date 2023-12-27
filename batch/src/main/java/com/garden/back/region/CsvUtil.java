package com.garden.back.region;

import java.util.List;

public class CsvUtil {

    private CsvUtil() {}
    public static String getColumnValue(List<String> row, RegionColumn column) {
        return row.get(column.getIndex());
    }
}
