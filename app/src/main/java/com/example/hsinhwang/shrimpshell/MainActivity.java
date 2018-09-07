package com.example.hsinhwang.shrimpshell;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.example.hsinhwang.shrimpshell.Classes.LogIn;
import com.example.hsinhwang.shrimpshell.EmployeePanel.EmployeeHomeActivity;

public class MainActivity extends AppCompatActivity {
    private Window window;
    boolean login = false;
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.item_booking:
                    fragment = new BookingFragment();
                    changeFragment(fragment);
                    setTitle(R.string.booking);
                    return true;
                case R.id.item_reserved:
                    fragment = new ReservedFragment();
                    changeFragment(fragment);
                    setTitle(R.string.reserved);
                    return true;
                case R.id.item_profile:
                    if (LogIn.EmployeeLogIn()) {
                        Intent intent = new Intent(MainActivity.this, EmployeeHomeActivity.class);
                        startActivity(intent);
                    } else {
                        fragment = new ProfileFragment();
                        changeFragment(fragment);
                        setTitle(R.string.profile);
                    }

                    return true;
                default:
                    item.setChecked(true);
                    initContent();
                    return true;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initContent();
        initialization();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!LogIn.CustomerLogIn()){
            initContent();
            navigation.setSelectedItemId(R.id.item_home);
        }

    }

    private void initialization() {
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.setStatusBarColor(Color.parseColor("#01728B"));
        }
    }

    private void initContent() {
        Fragment fragment = new HomeFragment();
        changeFragment(fragment);
        setTitle(R.string.home);
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

}
