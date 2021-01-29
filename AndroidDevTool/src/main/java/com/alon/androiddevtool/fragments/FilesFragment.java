package com.alon.androiddevtool.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alon.androiddevtool.interfaces.OnRCVClickListener;
import com.alon.androiddevtool.R;
import com.alon.androiddevtool.adapters.FilesAdapter;
import com.alon.androiddevtool.models.FileRCVState;
import com.alon.androiddevtool.taskrunner.GetFilesTask;
import com.alon.androiddevtool.taskrunner.TaskRunner;
import com.alon.androiddevtool.taskrunner.iOnDataFetched;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class FilesFragment extends Fragment implements iOnDataFetched, OnRCVClickListener, View.OnClickListener {

    private Context context;
    private TaskRunner taskRunner;
    private TextView files_LBL_hierarchy, files_LBL_empty;
    private ImageButton files_BTN_back;
    private RecyclerView files_RCV;
    private FilesAdapter filesAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar files_PGB;
    private ArrayList<File> filesData = new ArrayList<>();
    private Stack<FileRCVState> filesStack = new Stack<>();

    public FilesFragment(Context context) {
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
        View view = inflater.inflate(R.layout.fragment_files, container, false);
        findAll(view);
        setClickListeners();
        files_PGB.setVisibility(View.GONE);
        files_LBL_empty.setVisibility(View.GONE);
        taskRunner = new TaskRunner();
        initRecyclerView();
        initData();
        return view;
    }

    /**
     * Function that find all the views by id.
     *
     * @param view The root view.
     */
    private void findAll(View view) {
        files_LBL_hierarchy = view.findViewById(R.id.files_LBL_hierarchy);
        files_LBL_empty = view.findViewById(R.id.files_LBL_empty);
        files_BTN_back = view.findViewById(R.id.files_BTN_back);
        files_RCV = view.findViewById(R.id.files_RCV);
        files_PGB = view.findViewById(R.id.files_PGB);
    }

    /**
     * Function that sets all click listeners.
     */
    private void setClickListeners() {
        files_BTN_back.setOnClickListener(this);
    }

    /**
     * Function that gets all the databases names and tables of each db in other thread.
     */
    private void initData() {
        files_BTN_back.setVisibility(View.GONE);
        File file = context.getFilesDir();
        files_LBL_hierarchy.setText(file.getName() + "/");
        taskRunner.executeAsync(new GetFilesTask(this, context, file));
    }

    /**
     * Function that init the recycler view.
     */
    private void initRecyclerView() {
        files_RCV.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(context, 2);
        files_RCV.setLayoutManager(layoutManager);
    }

    /**
     * Function that sets the recycler view state.
     */
    private void setRecyclerviewState() {
        if (files_RCV != null) {
            files_RCV.removeAllViewsInLayout();
        }
        Collections.sort(filesData);
        filesAdapter = new FilesAdapter(filesData, this);
        files_RCV.setAdapter(filesAdapter);
        if (filesStack.size() > 1) {
            files_BTN_back.setVisibility(View.VISIBLE);
        } else {
            files_BTN_back.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showProgressBar() {
        files_PGB.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        files_PGB.setVisibility(View.GONE);
    }

    @Override
    public void setDataInPageWithResult(Object result) {
        File[] files = (File[]) result;
        filesData = new ArrayList<>();
        if (files.length > 0) {
            files_LBL_empty.setVisibility(View.GONE);
            for (File file : files) {
                filesData.add(file);
            }
        } else {
            files_LBL_empty.setVisibility(View.VISIBLE);
        }
        FileRCVState fileRCVState = new FileRCVState();
        String path = files_LBL_hierarchy.getText().toString();
        fileRCVState.setPath(path);
        fileRCVState.setFiles(filesData);
        filesStack.push(fileRCVState);
        setRecyclerviewState();
    }

    @Override
    public void onItemClick(File file) {
        if (file.isDirectory()) {
            String hierarchy = files_LBL_hierarchy.getText().toString();
            if (filesStack.size() < 4) {
                hierarchy += file.getName();
                files_LBL_hierarchy.setText(hierarchy + "/");
            } else {
                hierarchy = "files/.../" + file.getName() + "/";
                files_LBL_hierarchy.setText(hierarchy);
            }
            taskRunner.executeAsync(new GetFilesTask(this, context, file));
        } else if (file.isFile()) {
            StringBuilder sb = new StringBuilder();
            long kb = file.length() / 1024;
            String path = file.getPath();
            sb.append("Name: " + file.getName() + "\n");
            sb.append("Size: " + Long.valueOf(kb).toString() + "KB\n");
            sb.append("Path: " + path + "\n");
            showInformationDialog(file.getName(), sb.toString());
        }
    }

    /**
     * Function that shows file's information dialog.
     *
     * @param fileName Name of the file.
     * @param info     File's information.
     */
    private void showInformationDialog(String fileName, String info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(fileName);
        builder.setMessage(info);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.files_BTN_back) {
            files_LBL_empty.setVisibility(View.GONE);
            if (filesStack.size() > 1) {
                filesStack.pop();
                files_LBL_hierarchy.setText(filesStack.peek().getPath());
                filesData = filesStack.peek().getFiles();
            }
            setRecyclerviewState();
        }
    }
}