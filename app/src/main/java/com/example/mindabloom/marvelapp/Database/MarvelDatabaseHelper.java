package com.example.mindabloom.marvelapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Abdelaziz on 11/10/2016.
 */

public class MarvelDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "marvelappdatabase";
    private static final String TABLE_NAME = "history";
    private static final int DATABASE_VERSION = 1;
    private static final String ID = "_id";
    private static final String NAME = "Name";

    public MarvelDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create the history table
        String query = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255));";
        try {
            sqLiteDatabase.execSQL(query);
        } catch (SQLException e) {
            Log.e("Database exception", "" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Dropping the table and creating new database if upgrade happens
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        try {
            sqLiteDatabase.execSQL(query);
            onCreate(sqLiteDatabase);
        } catch (SQLException e) {
            Log.e("Database exception", "" + e);
        }

    }

    public void addName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public List<String> getHistory() {

        List<String> results = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + NAME + " FROM " + TABLE_NAME + " ORDER BY " + ID + " DESC LIMIT 5";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                results.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        return results;
    }
}
