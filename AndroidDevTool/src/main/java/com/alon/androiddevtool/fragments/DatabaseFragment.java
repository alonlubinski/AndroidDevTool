package com.alon.androiddevtool.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.alon.androiddevtool.R;
import com.alon.androiddevtool.adapters.DBExpandableListAdapter;
import com.alon.androiddevtool.models.DBPojo;
import com.alon.androiddevtool.taskrunner.GetDBContentTask;
import com.alon.androiddevtool.taskrunner.TaskRunner;
import com.alon.androiddevtool.taskrunner.iOnDataFetched;
import com.alon.androiddevtool.utils.DBHelper;

import java.io.File;
import java.util.ArrayList;


public class DatabaseFragment extends Fragment implements iOnDataFetched {

    private Context context;
    private TextView database_LBL_empty;
    private ExpandableListView db_ELV;
    private DBExpandableListAdapter dbExpandableListAdapter;
    private ArrayList<DBPojo> dataSet;
    private ArrayList<String> tablesNames;
    private DBHelper dbHelper;
    private Cursor cursor;
    private String tableName;
    private TaskRunner taskRunner;

    public DatabaseFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_database, container, false);
        findAll(view);
        taskRunner = new TaskRunner();

        return view;
    }

    // Function that finds all the views by id.
    private void findAll(View view){
        database_LBL_empty = view.findViewById(R.id.database_LBL_empty);
        db_ELV = view.findViewById(R.id.db_ELV);
    }

    private void initData(){
        Log.d("pttt", "initData");
        dataSet = new ArrayList<>();

        File db_dir = new File(context.getApplicationInfo().dataDir, "databases");
        if(db_dir.exists() && db_dir.isDirectory()){
            Log.d("pttt", "Exist");
            String[] list = db_dir.list();
            if(list.length > 0) {
                database_LBL_empty.setVisibility(View.GONE);
                taskRunner.executeAsync(new GetDBContentTask(this, context, list));
            } else {
                database_LBL_empty.setVisibility(View.VISIBLE);
            }
        } else {
            Log.d("pttt", "Not Exist");
            database_LBL_empty.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        database_LBL_empty.setVisibility(View.GONE);
        if(db_ELV != null){
            db_ELV.removeAllViewsInLayout();
        }
        initData();
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
        dbExpandableListAdapter = new DBExpandableListAdapter(context, getContext(), (ArrayList)result);
        db_ELV.setAdapter(dbExpandableListAdapter);
    }
}