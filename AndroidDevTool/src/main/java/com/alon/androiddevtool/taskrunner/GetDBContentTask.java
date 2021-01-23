package com.alon.androiddevtool.taskrunner;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.alon.androiddevtool.models.DBPojo;
import com.alon.androiddevtool.utils.DBHelper;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class GetDBContentTask extends BaseTask {

    private final iOnDataFetched listener;//listener in fragment that shows and hides ProgressBar
    private final Context context;
    private final String[] list;

    public GetDBContentTask(iOnDataFetched onDataFetchedListener, Context context, String[] list) {
        this.listener = onDataFetchedListener;
        this.context = context;
        this.list = list;
    }

    @Override
    public Object call() throws Exception {
        ArrayList<DBPojo> dataSet = new ArrayList<>();
        ArrayList<String> tablesNames;
        DBHelper dbHelper;
        Cursor cursor;
        String tableName;
        int version;
        for(int i = 0; i < list.length; i++){
            if(!list[i].endsWith("-journal")) {
                Log.d("pttt", list[i]);
                tablesNames = new ArrayList<>();
                version = getDBVersion(list[i]);
                dbHelper = new DBHelper(context, list[i], version);
                DBPojo dbPojo = new DBPojo();
                dbPojo.setName(list[i]);
                dbPojo.setVersion(version);
                cursor = dbHelper.getTablesName();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        tableName = cursor.getString(cursor.getColumnIndex("name"));
                        Log.d("pttt", cursor.getString(cursor.getColumnIndex("name")));
                        if (!tableName.equals("sqlite_sequence") && !tableName.equals("android_metadata")) {
                            tablesNames.add(tableName);
                        }
                        cursor.moveToNext();
                    }
                }
                dbPojo.setTables(tablesNames);
                dataSet.add(dbPojo);
                dbHelper.close();
            }
        }
        return dataSet;
    }

    @Override
    public void setUiForLoading() {
        listener.showProgressBar();
    }

    @Override
    public void setDataAfterLoading(Object result) {
        listener.setDataInPageWithResult(result);
        listener.hideProgressBar();
    }

    // Function that returns the version of db file.
    private int getDBVersion(String fileName) throws IOException {
        File file = new File(context.getApplicationInfo().dataDir + "/databases/" + fileName);
        RandomAccessFile fp = new RandomAccessFile(file, "r");
        fp.seek(60);
        byte[] buff = new byte[4];
        fp.read(buff, 0, 4);
        return ByteBuffer.wrap(buff).getInt();
    }
}

