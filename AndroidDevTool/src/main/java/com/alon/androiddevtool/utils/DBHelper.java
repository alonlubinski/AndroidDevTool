package com.alon.androiddevtool.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private String dbName;
    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context, @Nullable String name, int version) {
        super(context, name, null, version);
        this.context = context;
        this.dbName = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Function that gets content of specific table.
     *
     * @param tableName Table name.
     * @return Cursor
     */
    public Cursor getInfo(String tableName) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tableName, null);
        return cursor;
    }

    /**
     * Functions that gets all tables name in the database.
     *
     * @return Cursor
     */
    public Cursor getTablesName() {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name from sqlite_master where type='table'", null);
        return cursor;
    }

    /**
     * Function that close the database.
     */
    public void closeDB() {
        db.close();
    }
}
