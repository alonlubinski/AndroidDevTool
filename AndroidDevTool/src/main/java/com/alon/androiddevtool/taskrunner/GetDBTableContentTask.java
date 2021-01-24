package com.alon.androiddevtool.taskrunner;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.alon.androiddevtool.utils.DBHelper;

import java.util.ArrayList;

public class GetDBTableContentTask extends BaseTask {

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
    public Object call() {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context, dbName, dbVersion);
        Cursor cursor = dbHelper.getInfo(tableName);
        String[] columnNames = cursor.getColumnNames();
        data.add(new ArrayList<>());
        for (int i = 0; i < columnNames.length; i++) {
            data.get(0).add(columnNames[i]);
        }
        while (cursor.moveToNext()) {
            data.add(new ArrayList<>());
            for (int i = 0; i < columnNames.length; i++) {
                data.get(data.size() - 1).add(cursor.getString(i));
            }
        }
        dbHelper.closeDB();
        dbHelper.close();
        TableLayout table_LYT = new TableLayout(context);
        table_LYT.setStretchAllColumns(true);

        for (int i = 0; i < data.size(); i++) {
            TableRow row = new TableRow(context);
            row.setPadding(10, 5, 10, 5);
            row.setGravity(Gravity.CENTER);
            ArrayList<String> arr = data.get(i);
            for (int j = 0; j < arr.size(); j++) {
                TextView textView = new TextView(context);
                textView.setText(arr.get(j));
                textView.setPadding(10, 5, 10, 5);
                if (i == 0) {
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setTextSize(15);
                }
                textView.setGravity(Gravity.CENTER);
                row.addView(textView);
            }
            table_LYT.addView(row);
        }
        return table_LYT;
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
}
