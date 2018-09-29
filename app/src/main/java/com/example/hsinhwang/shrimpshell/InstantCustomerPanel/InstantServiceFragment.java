package com.example.hsinhwang.shrimpshell.InstantCustomerPanel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hsinhwang.shrimpshell.Classes.ChatMessage;
import com.example.hsinhwang.shrimpshell.Classes.Common;

import com.example.hsinhwang.shrimpshell.InstantActivity;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;

import terranovaproductions.newcomicreader.FloatingActionMenu;
import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;
import static com.example.hsinhwang.shrimpshell.Classes.Common.chatwebSocketClient;


public class InstantServiceFragment extends Fragment {
    FragmentActivity activity;
    SharedPreferences preferences,roomNumber;
    String customerName;
    String roomNumber_A;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_instant_service,
                container, false);

        activity = getActivity();
        initView(view);
        initContent();

        preferences = getActivity().getSharedPreferences(Common.LOGIN, MODE_PRIVATE);
        customerName = preferences.getString("email", "");

        return view;


    }

    @Override
    public void onStart() {
        super.onStart();

        if (chatwebSocketClient == null) {
            Common.connectServer(activity,customerName,"0");
        }
        roomNumber = getActivity().getSharedPreferences(Common.INSTANT_TEST, MODE_PRIVATE);
        if (customerName.equals("cc@gmail.com")) {
            roomNumber_A = roomNumber.getString("roomNumber1","");
        } else {
            roomNumber_A = roomNumber.getString("roomNumber2","");
        }
    }


    private void initView(View v) {
        final FloatingActionMenu menu = (FloatingActionMenu) v.findViewById(R.id.fab_menu_list);
        menu.setMultipleOfFB(3.2f);
        menu.setIsCircle(true);

        menu.setOnMenuItemClickListener(new FloatingActionMenu.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionMenu fam, int index, FloatingActionButton item) {

                Fragment fragment;
                switch (index) {
                    case 0:
                        fragment = new RoomServiceCleanFragment();
                        changFragment(fragment);
                        break;
                    case 1:
                        fragment = new DinlingServiceFragment();
                        changFragment(fragment);
                        break;
                    case 2:
                        fragment = new TrafficServiceFragment();
                        changFragment(fragment);
                        break;
                    default:
                        initContent();
                        break;
                }

            }
        });


    }


    private void changFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fab_item_content, fragment);
        fragmentTransaction.commit();
    }

    private void initContent() {
        Fragment fragment = new RoomServiceCleanFragment();
        changFragment(fragment);
    }


}
