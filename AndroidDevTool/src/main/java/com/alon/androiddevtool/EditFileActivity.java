package com.alon.androiddevtool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alon.androiddevtool.adapters.EditAdapter;
import com.alon.androiddevtool.models.SharedPreferencesField;
import com.alon.androiddevtool.taskrunner.GetSPContentTask;
import com.alon.androiddevtool.taskrunner.TaskRunner;
import com.alon.androiddevtool.taskrunner.iOnDataFetched;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class EditFileActivity extends AppCompatActivity implements View.OnClickListener, iOnDataFetched {

    private TextView edit_LBL_title;
    private ImageButton edit_BTN_add;
    private Button edit_BTN_confirm, edit_BTN_cancel;
    private RecyclerView edit_RCV;
    private EditAdapter editAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String fileName;
    private ArrayList<SharedPreferencesField> spData = new ArrayList<>();
    private ArrayList<EditAdapter.MyViewHolder> viewHolderArrayList;
    private Handler handler = new Handler();
    private int selectedType;
    private TaskRunner taskRunner;
    private ProgressBar edit_PGB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_file);
        findAll();
        setClickListeners();
        taskRunner = new TaskRunner();
        edit_PGB.setVisibility(View.GONE);
        if (getIntent().getExtras() != null) {
            fileName = getIntent().getStringExtra("headerTitle");
            edit_LBL_title.setText(fileName);
            getDataFromFile(fileName);

        }
    }

    /**
     * Function that finds all the view by id.
     */
    private void findAll() {
        edit_LBL_title = findViewById(R.id.edit_LBL_title);
        edit_BTN_add = findViewById(R.id.edit_BTN_add);
        edit_BTN_confirm = findViewById(R.id.edit_BTN_confirm);
        edit_BTN_cancel = findViewById(R.id.edit_BTN_cancel);
        edit_RCV = findViewById(R.id.edit_RCV);
        edit_PGB = findViewById(R.id.edit_PGB);
    }

    /**
     * Function that sets all click listeners.
     */
    private void setClickListeners() {
        edit_BTN_add.setOnClickListener(this);
        edit_BTN_confirm.setOnClickListener(this);
        edit_BTN_cancel.setOnClickListener(this);
    }

    /**
     * Function that gets the data from specific shared preferences file.
     *
     * @param fileName Shared preferences file name.
     */
    private void getDataFromFile(String fileName) {
        taskRunner.executeAsync(new GetSPContentTask(this, getApplicationContext(), fileName));
    }

    /**
     * Function that init the recycler view.
     */
    private void initRecyclerView() {
        edit_RCV.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        edit_RCV.setLayoutManager(layoutManager);
        edit_RCV.setItemViewCacheSize(spData.size());
        editAdapter = new EditAdapter(spData);
        edit_RCV.setAdapter(editAdapter);
    }

    /**
     * Function that validates the edit shared preferences form.
     *
     * @return Boolean
     */
    private boolean validateForm() {
        boolean flag = true;
        spData = editAdapter.getDataSet();
        viewHolderArrayList = editAdapter.getViewHolderArrayList();
        for (int i = 0; i < viewHolderArrayList.size(); i++) {
            String key = spData.get(i).getKey().trim();
            String value = spData.get(i).getValue().trim();
            String type = spData.get(i).getType();
            if (key.equals("")) {
                ((EditText) viewHolderArrayList.get(i).itemView.findViewById(R.id.edit_EDT_key)).setError("Empty field");
                flag = false;
            }
            if (value.equals("")) {
                ((EditText) viewHolderArrayList.get(i).itemView.findViewById(R.id.edit_EDT_value)).setError("Empty field");
                flag = false;
            } else if (!checkValueType(value, type)) {
                if (type.equals("Boolean")) {
                    ((EditText) viewHolderArrayList.get(i).itemView.findViewById(R.id.edit_EDT_value)).setError("Fill true or false (with lowercase)");
                } else if (type.equals("HashSet")) {
                    ((EditText) viewHolderArrayList.get(i).itemView.findViewById(R.id.edit_EDT_value)).setError("HashSet must be surrounded by square brackets");
                } else {
                    ((EditText) viewHolderArrayList.get(i).itemView.findViewById(R.id.edit_EDT_value)).setError("Wrong type");
                }
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Function that checks if value type is correct.
     *
     * @param value Specific value to check.
     * @param type  The type of the value.
     * @return Boolean
     */
    private boolean checkValueType(String value, String type) {
        boolean flag = true;
        try {
            if (type.equals("Integer")) {
                Integer.parseInt(value);
            } else if (type.equals("Float")) {
                Float.parseFloat(value);
            } else if (type.equals("Long")) {
                Long.parseLong(value);
            } else if (type.equals("Boolean")) {
                if (!value.equals("false") && !value.equals("true")) {
                    flag = false;
                }
            } else if (type.equals("HashSet")) {
                if (!value.startsWith("[") || !value.endsWith("]")) {
                    flag = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }

    /**
     * Function that saves all data to shared preferences.
     */
    private void saveDataToSharedPreferences() {
        String sp_name = fileName.substring(0, fileName.length() - 4);
        SharedPreferences sp = getApplicationContext().getSharedPreferences(sp_name, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        for (int i = 0; i < spData.size(); i++) {
            String key = spData.get(i).getKey().trim();
            String value = spData.get(i).getValue().trim();
            String type = spData.get(i).getType();
            try {
                if (!key.equals("") && !value.equals("")) {
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
                    } else if (type.equals("HashSet")) {
                        String newValue = value.substring(1, value.length() - 1);
                        String[] stringSplit = newValue.split(", ");
                        List<String> list = Arrays.asList(stringSplit);
                        HashSet<String> set = new HashSet<String>(list);
                        editor.putStringSet(key, set);
                    }
                }
            } catch (Exception e) {
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

    /**
     * Function that show confirmation dialog.
     *
     * @param title   Title of the dialog.
     * @param message Message of the dialog.
     * @param id      Id of the button that triggered the dialog.
     */
    private void showConfirmationDialog(String title, String message, int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (id == R.id.edit_BTN_cancel) {
                    finish();
                } else if (id == R.id.edit_BTN_confirm) {
                    saveDataToSharedPreferences();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setButtonsClickable(true);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Function that shows add row dialog.
     */
    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select type:");
        final String[] typesArray = new String[]{"String", "Integer", "Float", "Long", "Boolean", "HashSet"};
        selectedType = 0;
        builder.setSingleChoiceItems(typesArray, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedType = which;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String type = Arrays.asList(typesArray).get(selectedType);
                editAdapter.getDataSet().add(new SharedPreferencesField("", "", type));
                edit_RCV.setItemViewCacheSize(editAdapter.getDataSet().size());
                editAdapter.notifyItemInserted(editAdapter.getDataSet().size());
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Function that sets all buttons clickable functionality.
     *
     * @param bool Boolean value to set for the clickable functionality.
     */
    private void setButtonsClickable(boolean bool) {
        edit_BTN_add.setClickable(bool);
        edit_BTN_confirm.setClickable(bool);
        edit_BTN_cancel.setClickable(bool);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit_BTN_add) {
            showAddDialog();
        } else if (v.getId() == R.id.edit_BTN_confirm) {
            setButtonsClickable(false);
            if (validateForm()) {
                showConfirmationDialog("Save", "Are you sure?", v.getId());
            } else {
                setButtonsClickable(true);
            }
        } else if (v.getId() == R.id.edit_BTN_cancel) {
            setButtonsClickable(false);
            showConfirmationDialog("Exit", "Are you sure?\nAny unsaved data will be lost.", v.getId());
        }
    }

    @Override
    public void showProgressBar() {
        edit_PGB.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        edit_PGB.setVisibility(View.GONE);
    }

    @Override
    public void setDataInPageWithResult(Object result) {
        spData = (ArrayList) result;
        initRecyclerView();
    }
}