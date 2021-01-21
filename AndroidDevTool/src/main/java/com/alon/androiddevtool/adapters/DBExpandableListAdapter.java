package com.alon.androiddevtool.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alon.androiddevtool.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Context fragmentContext;
    private List<String> listDataHeader;

    public DBExpandableListAdapter(Context context, Context fragmentContext, List<String> listDataHeader) {
        this.context = context;
        this.fragmentContext = fragmentContext;
        this.listDataHeader = listDataHeader;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
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
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
