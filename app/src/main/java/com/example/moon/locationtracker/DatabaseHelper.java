package com.example.moon.locationtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "locationDB";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create dataframe table
        db.execSQL(dataFrame.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + dataFrame.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    public long insertDataframe(double longitude,double latitude) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(dataFrame.COLUMN_LONG, longitude);
        values.put(dataFrame.COLUMN_LATI,latitude);

        // insert row
        long id = db.insert(dataFrame.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public dataFrame getdataFrame(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(dataFrame.TABLE_NAME,
                new String[]{dataFrame.COLUMN_ID, dataFrame.COLUMN_TIME, dataFrame.COLUMN_LONG,dataFrame.COLUMN_LATI},
                dataFrame.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare dataframe object
        dataFrame dataframe= new dataFrame(
                cursor.getInt(cursor.getColumnIndex(dataFrame.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(dataFrame.COLUMN_TIME)),
                cursor.getDouble(cursor.getColumnIndex(dataFrame.COLUMN_LONG)),
                cursor.getDouble(cursor.getColumnIndex(dataFrame.COLUMN_LATI)));

        // close the db connection
        cursor.close();

        return dataframe;
    }

    public List<dataFrame> getAlldataFrames() {
        List<dataFrame> dataframes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + dataFrame.TABLE_NAME + " ORDER BY " +
                dataFrame.COLUMN_TIME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                dataFrame dataframe = new dataFrame();
                dataframe.setId(cursor.getInt(cursor.getColumnIndex(dataFrame.COLUMN_ID)));
                dataframe.setTime(cursor.getString(cursor.getColumnIndex(dataFrame.COLUMN_TIME)));
                dataframe.setLongitude(cursor.getDouble(cursor.getColumnIndex(dataFrame.COLUMN_LONG)));
                dataframe.setLatitude(cursor.getDouble(cursor.getColumnIndex(dataFrame.COLUMN_LATI)));

                dataframes.add(dataframe);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return list
        return dataframes;
    }

    public int getDataframeCount() {
        String countQuery = "SELECT  * FROM " + dataFrame.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
    public int updatedataFrame(dataFrame dataframe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dataFrame.COLUMN_LONG, dataframe.getLongitude());
        values.put(dataFrame.COLUMN_LATI, dataframe.getLatitude());

        // updating row
        return db.update(dataFrame.TABLE_NAME, values, dataFrame.COLUMN_ID + " = ?",
                new String[]{String.valueOf(dataframe.getId())});
    }
    public void deletedataFrame(dataFrame dataframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(dataFrame.TABLE_NAME, dataFrame.COLUMN_ID + " = ?",
                new String[]{String.valueOf(dataframe.getId())});
        db.close();
    }
}