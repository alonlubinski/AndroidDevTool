package com.alon.androiddevtool;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.alon.androiddevtool.taskrunner.GetDBTableContentTask;
import com.alon.androiddevtool.taskrunner.TaskRunner;
import com.alon.androiddevtool.taskrunner.iOnDataFetched;
import com.alon.androiddevtool.utils.DBHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DBTableActivity extends AppCompatActivity implements iOnDataFetched {

    private TextView table_LBL_title;
    private TableLayout table_LYT;
    private ProgressBar table_PGB;
    private String dbName, tableName;
    private int dbVersion;
    private TaskRunner taskRunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_b_table);
        Log.d("pttt", "onCreate");
        findAll();
        table_PGB.setVisibility(View.GONE);
        taskRunner = new TaskRunner();
        if (getIntent().getExtras() != null) {
            dbName = getIntent().getStringExtra("dbName");
            tableName = getIntent().getStringExtra("tableName");
            dbVersion = getIntent().getIntExtra("dbVersion", 1);
            initData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("pttt", "onResume");
    }

    // Function that finds all the views by id.
    private void findAll() {
        table_LBL_title = findViewById(R.id.table_LBL_title);
        table_LYT = findViewById(R.id.table_LYT);
        table_PGB = findViewById(R.id.table_PGB);
    }

    private void initData() {
        table_LBL_title.setText(tableName);
        taskRunner.executeAsync(new GetDBTableContentTask(this, getApplicationContext(), dbName, tableName, dbVersion));
    }

    @Override
    public void showProgressBar() {
        Log.d("pttt", "Loading");
        table_PGB.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        Log.d("pttt", "Finish Loading");
        table_PGB.setVisibility(View.GONE);
    }

    @Override
    public void setDataInPageWithResult(Object result) {
        Log.d("pttt", "Set Data");
        table_LYT.addView((TableLayout) result);
    }
}