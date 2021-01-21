package com.alon.androiddevtool.fragments;

import android.content.Context;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class DatabaseFragment extends Fragment {

    private Context context;
    private TextView database_LBL_empty;
    private ExpandableListView db_ELV;
    private DBExpandableListAdapter dbExpandableListAdapter;
    private List<String> listDataHeader;

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

        return view;
    }

    // Function that finds all the views by id.
    private void findAll(View view){
        database_LBL_empty = view.findViewById(R.id.database_LBL_empty);
        db_ELV = view.findViewById(R.id.db_ELV);
    }

    private void initData(){
        Log.d("pttt", "initData");
        listDataHeader = new ArrayList<>();
        //listHash = new HashMap<>();
        
        File db_dir = new File(context.getApplicationInfo().dataDir, "databases");
        if(db_dir.exists() && db_dir.isDirectory()){
            Log.d("pttt", "Exist");
            String[] list = db_dir.list();
            if(list.length > 0) {
                database_LBL_empty.setVisibility(View.GONE);
                for (int i = 0; i < list.length; i = i + 2) {
                    Log.d("pttt", list[i]);
                    listDataHeader.add(list[i]);
//                listDataHeader.add(list[i]);
//                String sp_name = list[i].substring(0, list[i].length()-4);
//                SharedPreferences sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
//                Map<String, ?> map = sp.getAll();
//                for(Map.Entry<String, ?> entry : map.entrySet()){
//                    Log.d("pttt", "key is: " + entry.getKey() + " --- value is: " + entry.getValue());
//                }
                    //listHash.put(list[i], map);
                }
            } else {
                database_LBL_empty.setVisibility(View.VISIBLE);
            }
        } else {
            Log.d("pttt", "Not Exist");
            database_LBL_empty.setVisibility(View.VISIBLE);
        }
        dbExpandableListAdapter = new DBExpandableListAdapter(context, getContext(), listDataHeader);
        db_ELV.setAdapter(dbExpandableListAdapter);
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
}