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

    public Cursor getInfo(String tableName){
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tableName, null);
        return cursor;
    }

    public Cursor getTablesName(){
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name from sqlite_master where type='table'", null);
        return cursor;
    }

    public void closeDB(){
        db.close();
    }
}
