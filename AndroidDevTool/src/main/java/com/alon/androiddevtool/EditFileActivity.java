package com.alon.androiddevtool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alon.androiddevtool.adapters.EditAdapter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EditFileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView edit_LBL_title;
    private Button edit_BTN_confirm, edit_BTN_cancel;
    private RecyclerView edit_RCV;
    private RecyclerView.Adapter editAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String fileName;
    private Map<String, ?> fileContent = new HashMap<>();
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_file);
        findAll();
        setClickListeners();
        if(getIntent().getExtras() != null){
            fileName = getIntent().getStringExtra("headerTitle");
            edit_LBL_title.setText(fileName);
            getDataFromFile(fileName);
            initRecyclerView();
        }
    }

    // Function that finds all the views by id.
    private void findAll(){
        edit_LBL_title = findViewById(R.id.edit_LBL_title);
        edit_BTN_confirm = findViewById(R.id.edit_BTN_confirm);
        edit_BTN_cancel = findViewById(R.id.edit_BTN_cancel);
        edit_RCV = findViewById(R.id.edit_RCV);
    }

    // Function that sets click listeners.
    private void setClickListeners() {
        edit_BTN_confirm.setOnClickListener(this);
        edit_BTN_cancel.setOnClickListener(this);
    }

    // Function that gets the data from file.
    private void getDataFromFile(String fileName){
        File sp_dir = new File(getApplicationContext().getApplicationInfo().dataDir, "shared_prefs");
        if(sp_dir.exists() && sp_dir.isDirectory()){
            String[] list = sp_dir.list();
            for(int i = 0; i < list.length; i++){
                if(list[i].equals(fileName)){
                    Log.d("pttt", list[i]);
                    String sp_name = list[i].substring(0, list[i].length()-4);
                    SharedPreferences sp = getApplicationContext().getSharedPreferences(sp_name, Context.MODE_PRIVATE);
                    fileContent = sp.getAll();
                }
            }
        }
    }

    // Function that init the recycler view.
    private void initRecyclerView(){
        edit_RCV.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        edit_RCV.setLayoutManager(layoutManager);
        editAdapter = new EditAdapter(fileContent);
        edit_RCV.setAdapter(editAdapter);
    }

    // Function that validates the edit shared preferences form.
    private boolean validateForm(){
        boolean flag = true;
        for(int i = 0; i < editAdapter.getItemCount(); i++){
            View view = edit_RCV.findViewHolderForAdapterPosition(i).itemView;
            String key = ((EditText)view.findViewById(R.id.edit_EDT_key)).getText().toString().trim();
            String value = ((EditText)view.findViewById(R.id.edit_EDT_value)).getText().toString().trim();
            String type = ((TextView)view.findViewById(R.id.edit_LBL_type)).getText().toString();
            if(key.equals("")){
                ((EditText)view.findViewById(R.id.edit_EDT_key)).setError("Empty field");
                flag = false;
            }
            if(value.equals("")){
                ((EditText)view.findViewById(R.id.edit_EDT_value)).setError("Empty field");
                flag = false;
            } else if(!checkValueType(value, type)){
                if(type.equals("Boolean")){
                    ((EditText)view.findViewById(R.id.edit_EDT_value)).setError("Fill true or false (with lowercase)");
                } else {
                    ((EditText)view.findViewById(R.id.edit_EDT_value)).setError("Wrong type");
                }
                flag = false;
            }
        }
        return flag;
    }

    // Function that check if value type is correct.
    private boolean checkValueType(String value, String type){
        boolean flag = true;
        try {
            if (type.equals("Integer")) {
                Integer.parseInt(value);
            } else if (type.equals("Float")) {
                Float.parseFloat(value);
            } else if (type.equals("Long")) {
                Long.parseLong(value);
            } else if (type.equals("Boolean")) {
                if(!value.equals("false") && !value.equals("true")){
                    flag = false;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }

    // Function that save all data to shared preferences.
    private void saveDataToSharedPreferences(){
        String sp_name = fileName.substring(0, fileName.length()-4);
        SharedPreferences sp = getApplicationContext().getSharedPreferences(sp_name, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        for(int i = 0; i < editAdapter.getItemCount(); i++){
            View view = edit_RCV.findViewHolderForAdapterPosition(i).itemView;
            String key = ((EditText)view.findViewById(R.id.edit_EDT_key)).getText().toString().trim();
            String value = ((EditText)view.findViewById(R.id.edit_EDT_value)).getText().toString().trim();
            String type = ((TextView)view.findViewById(R.id.edit_LBL_type)).getText().toString();
            try {
                if (type.equals("String")) {
                    editor.putString(key, value);
                } else if (type.equals("Integer")) {
                    int integerNum = Integer.parseInt(value);
                    editor.putInt(key, integerNum);
                } else if (type.equals("Float")) {
                    float floatNum = Float.parseFloat(value);
                    editor.putFloat(key, floatNum);
                } else if (type.equals("Long")) {
                    long longNum = Long.parseLong(value);
                    editor.putLong(key, longNum);
                } else if (type.equals("Boolean")) {
                    boolean boolValue = Boolean.parseBoolean(value);
                    editor.putBoolean(key, boolValue);
                }
            } catch(Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_LONG);
                return;
            }
        }
        editor.apply();
        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.edit_BTN_confirm){
            Log.d("pttt", "Confirm");
            if(validateForm()){
                Log.d("pttt", "Form is ok");
                saveDataToSharedPreferences();
            } else {
                Log.d("pttt", "Form is not ok");
            }
        } else if(v.getId() == R.id.edit_BTN_cancel){
            Log.d("pttt", "Cancel");
            finish();
        }
    }
}