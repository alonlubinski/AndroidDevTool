package com.alon.androiddevtool;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.alon.androiddevtool.utils.DBHelper;

public class DBTableActivity extends AppCompatActivity {

    private TextView table_LBL_title;
    private TableLayout table_LYT;
    private String dbName, tableName;
    private DBHelper dbHelper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_b_table);
        findAll();
        if (getIntent().getExtras() != null) {
            dbName = getIntent().getStringExtra("dbName");
            tableName = getIntent().getStringExtra("tableName");
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
        dbHelper = new DBHelper(getApplicationContext(), dbName, 2);
        cursor = dbHelper.getInfo(tableName);
        if(cursor.getCount() == 0){
            Log.d("pttt", "No data found");
        } else {

        }
        dbHelper.closeDB();
        dbHelper.close();
    }
}