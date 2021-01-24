package com.alon.testmyandroiddevtool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button main_BTN_tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAll();
        setClickListeners();
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

