package com.example.hsinhwang.shrimpshell;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hsinhwang.shrimpshell.Classes.StatusService;
import com.example.hsinhwang.shrimpshell.InstantPanel.InstantServiceFragment;
import com.example.hsinhwang.shrimpshell.InstantPanel.StatusServiceFragment;

import java.util.List;


public class InstantFragment extends Fragment {
    private RecyclerView rvStatusService;

    //bottomnavigation item 監聽器
    private void initView(View v) {
        BottomNavigationView navigation = v.findViewById(R.id.navigationInstant);
        navigation.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment;
                        switch (item.getItemId()) {
                            case R.id.item_instantService:
                                fragment = new InstantServiceFragment();
                                changFragment(fragment);
                                //setTitle(R.string.textInstantService);
                                return true;
                            case R.id.item_statusService:
                                fragment = new StatusServiceFragment();
                                changFragment(fragment);
                                //setTitle(R.string.textStatusService);
                                return true;
                            default:
                                initContent();
                                break;

                        }
                        return false;
                    }
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instant, container, false);



        initView(view);
        initContent();


        return view;
    }

    //fragment 換頁管理
    public void changFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentInstant, fragment);
        fragmentTransaction.commit();
    }

    //預設頁面
    private void initContent() {

        Fragment fragment = new InstantServiceFragment();
        changFragment(fragment);

    }


}
