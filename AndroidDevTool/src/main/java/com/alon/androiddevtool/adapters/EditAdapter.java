package com.alon.androiddevtool.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alon.androiddevtool.R;

import java.util.Map;

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.MyViewHolder> {

    private Map<String, ?> dataSet;
    private Object[] keys;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private EditText edit_EDT_key, edit_EDT_value;
        private TextView edit_LBL_type;
        private ImageButton edit_BTN_delete;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            edit_EDT_key = itemView.findViewById(R.id.edit_EDT_key);
            edit_EDT_value = itemView.findViewById(R.id.edit_EDT_value);
            edit_LBL_type = itemView.findViewById(R.id.edit_LBL_type);
            edit_BTN_delete = itemView.findViewById(R.id.edit_BTN_delete);
        }
    }

    public EditAdapter(Map<String, ?> fileContent){
        this.dataSet = fileContent;
        keys = dataSet.keySet().toArray();
    }

    @NonNull
    @Override
    public EditAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        EditAdapter.MyViewHolder vh = new EditAdapter.MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EditAdapter.MyViewHolder holder, int position) {
        Log.d("pttt", Integer.valueOf(position).toString());
        String key = keys[position].toString();
        String value = dataSet.get(keys[position].toString()).toString();
        String type = dataSet.get(keys[position]).getClass().getSimpleName();
        holder.edit_EDT_key.setText(key);
        holder.edit_EDT_value.setText(value);
        holder.edit_LBL_type.setText(type);
        holder.edit_BTN_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSet.remove(key);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
