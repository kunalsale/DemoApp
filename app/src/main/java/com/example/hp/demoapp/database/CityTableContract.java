package com.example.hp.demoapp.database;

/**
 * Created by kunal.sale on 25-08-2017.
 */

public final class CityTableContract
{
    private CityTableContract()
    {}

    public static class CityTable
    {
        public static final String TABLE_NAME           = "city";
        public static final String COLUMN_ID            = "id";
        public static final String COLUMN_SLUG          = "slug";
        public static final String COLUMN_NAME_CITY     = "name";
    }
}
