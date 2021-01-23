package com.alon.testmyandroiddevtool;

import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alon.androiddevtool.utils.DBHelper;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button main_BTN_tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAll();
        setClickListeners();
//        DBHelper dbHelper = new DBHelper(this, "test.db", 1);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues c = new ContentValues();
//        for(int i = 0; i < 50; i++){
//            c.put("name", "Alon");
//            c.put("age", 26);
//            c.put("gender", "Male");
//            c.put("email", "alonlubi14@gmail.com");
//            c.put("message", "Hello");
//            c.put("message2", "World");
//            c.put("message3", "!");
//            db.insert("users", null, c);
//        }
//        db.close();
//        dbHelper.close();
    }

    private void findAll() {
        main_BTN_tool = findViewById(R.id.main_BTN_tool);
    }

    private void setClickListeners() {
        main_BTN_tool.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_BTN_tool:
                startDevActivity();
                break;

        }
    }

    private void startDevActivity() {
        Intent intent = new Intent("com.alon.androiddevtool");
        startActivity(intent);
    }
}

