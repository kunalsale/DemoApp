package com.example.hp.demoapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kunal.sale on 25-08-2017.
 */

public class CityDatabaseHelper extends SQLiteOpenHelper
{
    private static final int    DATABASE_VERSION = 1;
    private static final String DATABASE_NAME    = "City.db";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + CityTableContract.CityTable.TABLE_NAME + " (" +
                    CityTableContract.CityTable.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    CityTableContract.CityTable.COLUMN_NAME_CITY + " TEXT," +
                    CityTableContract.CityTable.COLUMN_SLUG + " TEXT)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + CityTableContract.CityTable.TABLE_NAME;

    public CityDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
