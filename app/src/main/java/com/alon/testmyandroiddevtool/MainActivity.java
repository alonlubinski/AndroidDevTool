package com.alon.testmyandroiddevtool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileWriter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button main_BTN_tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAll();
        setClickListeners();
//        Log.d("pttt", this.getFilesDir().getPath());
//        File file = new File(this.getFilesDir(), "folder");
//        if(!file.exists()){
//            Log.d("pttt", "Not exist");
//            file.mkdir();
//        }
//        try{
//            File file1 = new File(file, "fileName");
//            FileWriter writer = new FileWriter(file1);
//            writer.append("New File");
//            writer.flush();
//            writer.close();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
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
        Intent intent = new Intent("AndroidDevTool");
        startActivity(intent);
    }
}

