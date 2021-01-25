package com.alon.androiddevtool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.alon.androiddevtool.fragments.DatabaseFragment;
import com.alon.androiddevtool.fragments.HomeFragment;
import com.alon.androiddevtool.fragments.SharedPreferencesFragment;
import com.google.android.material.navigation.NavigationView;


public class AndroidDevToolActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private View navHeader;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Fragment fragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_dev_tool);
        Log.d("pttt", "onCreate");

        findAll();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.main_FL, new HomeFragment()).commit();
    }


    /**
     * Function that finds all views by id.
     */
    private void findAll() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.main_DL);
        navigationView = findViewById(R.id.main_NV);
        navHeader = navigationView.getHeaderView(0);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    private void selectDrawerItem(MenuItem item) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_home) {
            Log.d("pttt", "Home");
            fragment = new HomeFragment();
        } else if (item.getItemId() == R.id.menu_sp) {
            fragment = new SharedPreferencesFragment(getApplicationContext());
        } else if (item.getItemId() == R.id.menu_db) {
            Log.d("pttt", "Database");
            fragment = new DatabaseFragment(getApplicationContext());
        }
        if (fragment != null) {
            changeFragment(fragment, item);
        }
        return false;
    }

    // Method that change the layout to the chosen fragment.

    /**
     * Function that changes the layout to the chosen fragment.
     *
     * @param fragment The chosen fragment.
     * @param item     The chosen menu item.
     */
    private void changeFragment(Fragment fragment, MenuItem item) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_FL, fragment).commit();
            setTitle(item.getTitle());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("pttt", "AndroidDevToolActivity - onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("pttt", "AndroidDevToolActivity - onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("pttt", "AndroidDevToolActivity - onStop");
    }
}