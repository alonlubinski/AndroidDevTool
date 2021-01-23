package com.alon.androiddevtool.taskrunner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alon.androiddevtool.models.DBPojo;
import com.alon.androiddevtool.utils.DBHelper;

import java.util.ArrayList;

public class GetDBTableContentTask extends BaseTask{

    private final iOnDataFetched listener;//listener in fragment that shows and hides ProgressBar
    private final Context context;
    private final String dbName;
    private final String tableName;
    private final int dbVersion;

    public GetDBTableContentTask(iOnDataFetched listener, Context context, String dbName, String tableName, int dbVersion) {
        this.listener = listener;
        this.context = context;
        this.dbName = dbName;
        this.tableName = tableName;
        this.dbVersion = dbVersion;
    }

    @Override
    public Object call() throws Exception {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context, dbName, dbVersion);
        Cursor cursor = dbHelper.getInfo(tableName);
        String[] columnNames = cursor.getColumnNames();
        data.add(new ArrayList<>());
        for(int i = 0; i < columnNames.length; i++){
            data.get(0).add(columnNames[i]);
        }
        while(cursor.moveToNext()){
            data.add(new ArrayList<>());
            for(int i = 0; i < columnNames.length; i++){
                data.get(data.size() - 1).add(cursor.getString(i));
            }
        }
        dbHelper.closeDB();
        dbHelper.close();
        return data;
    }

    @Override
    public void setUiForLoading() {
        listener.showProgressBar();
    }

    @Override
    public void setDataAfterLoading(Object result) {
        listener.hideProgressBar();
        listener.setDataInPageWithResult(result);
    }
}
