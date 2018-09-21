package com.example.hsinhwang.shrimpshell;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.InstantCustomerPanel.InstantServiceFragment;
import com.example.hsinhwang.shrimpshell.InstantCustomerPanel.StatusServiceFragment;



public class InstantActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.item_instantService:
                    fragment = new InstantServiceFragment();
                    changeFragment(fragment);
                    return true;
                case R.id.item_statusService:
                    fragment = new StatusServiceFragment();
                    changeFragment(fragment);
                    return true;
                default:
                    initContent();
                    break;
            }
            return false;


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant);

        BottomNavigationView navigation = findViewById(R.id.navigationInstant);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);


        Common.connectServer(this,"603","0");

        initContent();


    }

    private void initContent() {
        Fragment fragment = new InstantServiceFragment();
        changeFragment(fragment);


    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentInstant,fragment);
        fragmentTransaction.commit();

    }



}
