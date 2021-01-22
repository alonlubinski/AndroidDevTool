package com.alon.androiddevtool.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alon.androiddevtool.DBTableActivity;
import com.alon.androiddevtool.EditFileActivity;
import com.alon.androiddevtool.R;
import com.alon.androiddevtool.models.DBPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Context fragmentContext;
    private ArrayList<DBPojo> dataSet;

    public DBExpandableListAdapter(Context context, Context fragmentContext, ArrayList<DBPojo> dataSet) {
        this.context = context;
        this.fragmentContext = fragmentContext;
        this.dataSet = dataSet;
    }

    @Override
    public int getGroupCount() {
        return dataSet.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataSet.get(groupPosition).getTables().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataSet.get(groupPosition).getName();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataSet.get(groupPosition).getTables().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.db_list_header, null);
        }
        TextView header_LBL_title = (TextView) convertView.findViewById(R.id.header_LBL_title);
        header_LBL_title.setTypeface(null, Typeface.BOLD);
        header_LBL_title.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childTitle = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.db_list_item, null);
        }
        TextView list_LBL_title = (TextView) convertView.findViewById(R.id.list_LBL_title);
        //list_LBL_title.setTypeface(null, Typeface.BOLD);
        list_LBL_title.setText(childTitle);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pttt", childTitle);
                startDBTableActivity(dataSet.get(groupPosition).getName(), childTitle);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void startDBTableActivity(String dbName, String tableName) {
        Intent intent = new Intent(fragmentContext, DBTableActivity.class);
        intent.putExtra("dbName", dbName);
        intent.putExtra("tableName", tableName);
        fragmentContext.startActivity(intent);
    }
}
