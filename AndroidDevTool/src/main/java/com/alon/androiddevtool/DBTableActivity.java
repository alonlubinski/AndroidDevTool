package com.alon.androiddevtool;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.alon.androiddevtool.taskrunner.GetDBTableContentTask;
import com.alon.androiddevtool.taskrunner.TaskRunner;
import com.alon.androiddevtool.taskrunner.iOnDataFetched;
import com.alon.androiddevtool.utils.DBHelper;

import java.util.ArrayList;

public class DBTableActivity extends AppCompatActivity implements iOnDataFetched {

    private TextView table_LBL_title;
    private TableLayout table_LYT;
    private String dbName, tableName;
    private int dbVersion;
    private DBHelper dbHelper;
    private Cursor cursor;
    private TaskRunner taskRunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_b_table);
        findAll();
        taskRunner = new TaskRunner();
        if (getIntent().getExtras() != null) {
            dbName = getIntent().getStringExtra("dbName");
            tableName = getIntent().getStringExtra("tableName");
            dbVersion = getIntent().getIntExtra("dbVersion", 0);
            initData();
        }
    }

    // Function that finds all the views by id.
    private void findAll(){
        table_LBL_title = findViewById(R.id.table_LBL_title);
        table_LYT = findViewById(R.id.table_LYT);
    }

    private void initData(){
        table_LBL_title.setText(tableName);
        taskRunner.executeAsync(new GetDBTableContentTask(this, getApplicationContext(), dbName, tableName, dbVersion));
    }

    @Override
    public void showProgressBar() {
        Log.d("pttt", "Loading");
    }

    @Override
    public void hideProgressBar() {
        Log.d("pttt", "Finish Loading");
    }

    @Override
    public void setDataInPageWithResult(Object result) {
        Log.d("pttt", "Set Data");
        table_LYT.setStretchAllColumns(true);
        //table_LYT.setShrinkAllColumns(true);

        for(int i = 0; i < ((ArrayList) result).size(); i++){
            TableRow row = new TableRow(this);
            row.setPadding(10, 5, 10, 5);
            row.setGravity(Gravity.CENTER);
            ArrayList<String> arr = ((ArrayList)((ArrayList) result).get(i));
            for(int j = 0; j < arr.size(); j++){
                TextView textView = new TextView(this);
                textView.setText(arr.get(j));
                textView.setPadding(10, 5, 10, 5);
                if(i == 0){
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setTextSize(15);
                }
                textView.setGravity(Gravity.CENTER);
                row.addView(textView);
            }
            table_LYT.addView(row);
        }
    }
}