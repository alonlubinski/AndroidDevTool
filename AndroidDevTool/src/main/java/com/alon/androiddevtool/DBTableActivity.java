package com.alon.androiddevtool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.alon.androiddevtool.taskrunner.GetDBTableContentTask;
import com.alon.androiddevtool.taskrunner.TaskRunner;
import com.alon.androiddevtool.taskrunner.iOnDataFetched;


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
    }

    /**
     * Function that finds all the view by id.
     */
    private void findAll() {
        table_LBL_title = findViewById(R.id.table_LBL_title);
        table_LYT = findViewById(R.id.table_LYT);
        table_PGB = findViewById(R.id.table_PGB);
    }

    /**
     * Function that retrieves data from the db in other thread.
     */
    private void initData() {
        table_LBL_title.setText(tableName);
        taskRunner.executeAsync(new GetDBTableContentTask(this, getApplicationContext(), dbName, tableName, dbVersion));
    }

    /**
     * Function that shows the progress bar.
     */
    @Override
    public void showProgressBar() {
        table_PGB.setVisibility(View.VISIBLE);
    }

    /**
     * Function that hides the progress bar.
     */
    @Override
    public void hideProgressBar() {
        table_PGB.setVisibility(View.GONE);
    }

    /**
     * Function that sets the data to the ui.
     *
     * @param result Result object that arrived from other thread.
     */
    @Override
    public void setDataInPageWithResult(Object result) {
        table_LYT.addView((TableLayout) result);
    }
}