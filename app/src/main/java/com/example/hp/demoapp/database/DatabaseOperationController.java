package com.example.hp.demoapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hp.demoapp.model.CityVO;

/**
 * Created by kunal.sale on 25-08-2017.
 *
 * This class is used to handle all database operations
 */

public class DatabaseOperationController
{
    private CityDatabaseHelper mCityDatabaseHelper;

    public DatabaseOperationController(CityDatabaseHelper cityDatabaseHelper)
    {
        mCityDatabaseHelper = cityDatabaseHelper;
    }

    // saves the list of cities in the database
    public boolean insertData(CityVO cityVO)
    {
        SQLiteDatabase db = mCityDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CityTableContract.CityTable.COLUMN_ID , cityVO.getId());
        values.put(CityTableContract.CityTable.COLUMN_NAME_CITY , cityVO.getName());
        values.put(CityTableContract.CityTable.COLUMN_SLUG , cityVO.getSlug());

        long insert = db.insert(CityTableContract.CityTable.TABLE_NAME, null, values);
        return insert > 0;
    }

    // fetch the data from database if not present in the database then returns empty cursor
    public Cursor fetchData()
    {
        SQLiteDatabase db = mCityDatabaseHelper.getReadableDatabase();

        Cursor cursor = db.query(
                CityTableContract.CityTable.TABLE_NAME,                     // The table to query
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        return cursor;
    }
}
