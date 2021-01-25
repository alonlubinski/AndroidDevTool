package com.alon.androiddevtool.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.alon.androiddevtool.R;
import com.alon.androiddevtool.adapters.SPExpandableListAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SharedPreferencesFragment extends Fragment {

    private ExpandableListView sp_ELV;
    private SPExpandableListAdapter expandableListAdapter;
    private ProgressBar sp_PGB;
    private List<String> listDataHeader;
    private HashMap<String, Map<String, ?>> listHash;
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
        sp_PGB.setVisibility(View.GONE);
        return view;
    }

    /**
     * Function that finds all the view by id.
     *
     * @param view The root view.
     */
    private void findAll(View view) {
        sp_ELV = view.findViewById(R.id.sp_ELV);
        sp_PGB = view.findViewById(R.id.sp_PGB);
    }

    /**
     * Function that gets all the shared preferences.
     */
    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        File sp_dir = new File(context.getApplicationInfo().dataDir, "shared_prefs");
        if (sp_dir.exists() && sp_dir.isDirectory()) {
            String[] list = sp_dir.list();
            for (int i = 0; i < list.length; i++) {
                listDataHeader.add(list[i]);
                String sp_name = list[i].substring(0, list[i].length() - 4);
                SharedPreferences sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
                Map<String, ?> map = sp.getAll();
                listHash.put(list[i], map);
            }
        }
        sp_PGB.setVisibility(View.GONE);
        expandableListAdapter = new SPExpandableListAdapter(context, getContext(), listDataHeader, listHash);
        sp_ELV.setAdapter(expandableListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sp_ELV != null) {
            sp_ELV.removeAllViewsInLayout();
        }
        sp_PGB.setVisibility(View.VISIBLE);
        initData();
    }
}