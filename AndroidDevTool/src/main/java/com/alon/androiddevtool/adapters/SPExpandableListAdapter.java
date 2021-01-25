package com.alon.androiddevtool.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alon.androiddevtool.EditFileActivity;
import com.alon.androiddevtool.R;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SPExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Context fragmentContext;
    private List<String> listDataHeader;
    private HashMap<String, Map<String, ?>> listHashMap;


    public SPExpandableListAdapter(Context context, Context fragmentContext, List<String> listDataHeader, HashMap<String, Map<String, ?>> listHashMap) {
        this.context = context;
        this.fragmentContext = fragmentContext;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition));
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
            convertView = inflater.inflate(R.layout.sp_list_header, null);
        }
        TextView header_LBL_title = (TextView) convertView.findViewById(R.id.header_LBL_title);
        header_LBL_title.setTypeface(null, Typeface.BOLD);
        header_LBL_title.setText(headerTitle);

        ImageButton header_BTN_edit = convertView.findViewById(R.id.header_BTN_edit);
        header_BTN_edit.setFocusable(false);
        header_BTN_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditFileActivity(headerTitle);
            }
        });

        ImageButton header_BTN_delete = convertView.findViewById(R.id.header_BTN_delete);
        header_BTN_delete.setFocusable(false);
        header_BTN_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(headerTitle);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Map<String, ?> map = (Map<String, ?>) getChild(groupPosition, childPosition);
        Object[] keys = map.keySet().toArray();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sp_list_item, null);
        }
        TextView list_LBL_key = (TextView) convertView.findViewById(R.id.list_LBL_key);
        TextView list_LBL_value = (TextView) convertView.findViewById(R.id.list_LBL_value);
        TextView list_LBL_type = (TextView) convertView.findViewById(R.id.list_LBL_type);
        list_LBL_key.setTypeface(null, Typeface.BOLD);
        list_LBL_key.setText(keys[childPosition].toString());
        list_LBL_value.setText(map.get(keys[childPosition].toString()).toString());
        list_LBL_type.setText(map.get(keys[childPosition]).getClass().getSimpleName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * Function that shows confirmation dialog.
     *
     * @param headerTitle Name of the file.
     */
    private void showConfirmationDialog(String headerTitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(fragmentContext);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete " + headerTitle + "?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File sp_dir = new File(context.getApplicationInfo().dataDir, "shared_prefs");
                listDataHeader.remove(headerTitle);
                listHashMap.remove(headerTitle);
                File sp_file = new File(sp_dir.getPath() + "/" + headerTitle);
                sp_file.delete();
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Function that start the EditFileActivity.
     *
     * @param headerTitle Name of the file.
     */
    private void startEditFileActivity(String headerTitle) {
        Intent intent = new Intent(fragmentContext, EditFileActivity.class);
        intent.putExtra("headerTitle", headerTitle);
        fragmentContext.startActivity(intent);
    }

}
