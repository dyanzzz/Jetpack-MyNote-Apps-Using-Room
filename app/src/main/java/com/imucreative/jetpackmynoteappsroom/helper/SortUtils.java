package com.imucreative.jetpackmynoteappsroom.helper;

import androidx.sqlite.db.SimpleSQLiteQuery;

public class SortUtils {
    public static final String NEWEST = "Newest";
    public static final String OLDEST = "Oldest";
    public static final String RANDOM = "Random";

    public static SimpleSQLiteQuery getSortedQuery(String filter) {
        StringBuilder simpleQuery = new StringBuilder().append("SELECT * FROM note ");
        switch (filter) {
            case NEWEST:
                simpleQuery.append("ORDER BY id DESC");
                break;
            case OLDEST:
                simpleQuery.append("ORDER BY id ASC");
                break;
            case RANDOM:
                simpleQuery.append("ORDER BY RANDOM()");
                break;
        }
        return new SimpleSQLiteQuery(simpleQuery.toString());
    }
}
