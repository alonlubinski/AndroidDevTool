package com.alon.androiddevtool.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.alon.androiddevtool.R;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private ImageButton home_BTN_linkedin, home_BTN_github;
    private Context context;

    public HomeFragment(Context context) {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findAll(view);
        setClickListeners();
        return view;
    }

    /**
     * Function that finds all the view by id.
     *
     * @param view The root view.
     */
    private void findAll(View view) {
        home_BTN_linkedin = view.findViewById(R.id.home_BTN_linkedin);
        home_BTN_github = view.findViewById(R.id.home_BTN_github);
    }

    /**
     * Function that sets all click listeners.
     */
    private void setClickListeners() {
        home_BTN_linkedin.setOnClickListener(this);
        home_BTN_github.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.home_BTN_linkedin) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/profile/alon-lubinski-640491184/")));
        } else if (v.getId() == R.id.home_BTN_github) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/alonlubinski/AndroidDevTool")));
        }
    }
}