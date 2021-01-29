package com.alon.androiddevtool.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alon.androiddevtool.interfaces.OnRCVClickListener;
import com.alon.androiddevtool.R;

import java.io.File;
import java.util.ArrayList;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.MyViewHolder> {

    private ArrayList<File> dataSet;
    private OnRCVClickListener onRCVClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView file_IMG_image;
        private TextView file_LBL_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            file_IMG_image = itemView.findViewById(R.id.file_IMG_image);
            file_LBL_name = itemView.findViewById(R.id.file_LBL_name);
        }

        public void bind(final File file, final OnRCVClickListener onRCVClickListener) {
            if (file.isDirectory()) {
                file_IMG_image.setImageResource(R.drawable.img_folder);
            } else {
                file_IMG_image.setImageResource(R.drawable.img_doc);
            }
            file_LBL_name.setText(file.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRCVClickListener.onItemClick(file);
                }
            });
        }
    }

    public FilesAdapter(ArrayList<File> dataSet, OnRCVClickListener onRCVClickListener) {
        this.dataSet = dataSet;
        this.onRCVClickListener = onRCVClickListener;
    }

    @NonNull
    @Override
    public FilesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_list_item, parent, false);
        FilesAdapter.MyViewHolder vh = new FilesAdapter.MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FilesAdapter.MyViewHolder holder, int position) {
        holder.bind(dataSet.get(position), onRCVClickListener);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
