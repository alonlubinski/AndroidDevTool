package com.alon.androiddevtool.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ExpandableListView;

import com.alon.androiddevtool.R;
import com.alon.androiddevtool.adapters.ExpandableListAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SharedPreferencesFragment extends Fragment {

    private ExpandableListView sp_ELV;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    private Context context;
    public SharedPreferencesFragment(Context context) {
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
        View view = inflater.inflate(R.layout.fragment_shared_preferences, container, false);
        findAll(view);
        initData();
        expandableListAdapter = new ExpandableListAdapter(context, listDataHeader, listHash);
        sp_ELV.setAdapter(expandableListAdapter);
        return view;
    }

    // Method that find all the views by id.
    private void findAll(View view){
        sp_ELV = view.findViewById(R.id.sp_ELV);
    }

    private void initData(){
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        File sp_dir = new File(context.getApplicationInfo().dataDir, "shared_prefs");
        if(sp_dir.exists() && sp_dir.isDirectory()){
            String[] list = sp_dir.list();
            for(int i = 0; i < list.length; i++){
                Log.d("pttt", list[i]);
                listDataHeader.add(list[i]);
            }
        }

    }
}