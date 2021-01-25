package com.alon.androiddevtool.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alon.androiddevtool.R;
import com.alon.androiddevtool.models.SharedPreferencesField;

import java.util.ArrayList;

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.MyViewHolder> {

    private ArrayList<SharedPreferencesField> dataSet;
    private ArrayList<EditAdapter.MyViewHolder> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private String key, value, type;
        private EditText edit_EDT_key, edit_EDT_value;
        private TextView edit_LBL_type;
        private ImageButton edit_BTN_delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            edit_EDT_key = itemView.findViewById(R.id.edit_EDT_key);
            edit_EDT_value = itemView.findViewById(R.id.edit_EDT_value);
            edit_LBL_type = itemView.findViewById(R.id.edit_LBL_type);
            edit_BTN_delete = itemView.findViewById(R.id.edit_BTN_delete);
        }
    }

    public EditAdapter(ArrayList<SharedPreferencesField> dataSet) {
        this.dataSet = dataSet;
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public EditAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        EditAdapter.MyViewHolder vh = new EditAdapter.MyViewHolder(view);
        data.add(vh);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EditAdapter.MyViewHolder holder, int position) {
        String key = dataSet.get(position).getKey();
        String value = dataSet.get(position).getValue();
        String type = dataSet.get(position).getType();
        holder.edit_EDT_key.setText(key);
        holder.edit_EDT_key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                holder.key = holder.edit_EDT_key.getText().toString();
                dataSet.get(holder.getAdapterPosition()).setKey(holder.key);
            }
        });
        holder.edit_EDT_value.setText(value);
        holder.edit_EDT_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                holder.value = holder.edit_EDT_value.getText().toString();
                dataSet.get(holder.getAdapterPosition()).setValue(holder.value);
            }
        });
        holder.edit_LBL_type.setText(type);
        holder.type = holder.edit_LBL_type.getText().toString();
        holder.edit_BTN_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSet.remove(holder.getAdapterPosition());
                data.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public ArrayList<EditAdapter.MyViewHolder> getViewHolderArrayList() {
        return data;
    }

    public ArrayList<SharedPreferencesField> getDataSet() {
        return dataSet;
    }


}
