package com.example.hsinhwang.shrimpshell.InstantCustomerPanel;

import android.content.SharedPreferences;
import android.nfc.Tag;
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

import com.example.hsinhwang.shrimpshell.Classes.Common;

import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.OrderRoomDetail;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import terranovaproductions.newcomicreader.FloatingActionMenu;
import static android.content.Context.MODE_PRIVATE;
import static com.example.hsinhwang.shrimpshell.Classes.Common.chatwebSocketClient;


public class InstantServiceFragment extends Fragment {
    private String TAG = "Debug";
    FragmentActivity activity;
    SharedPreferences preferences;
    String customerName;
    String roomNumber;
    int idRoomStatus;
    private CommonTask userRoomNumber;

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

        List<OrderRoomDetail> orderRoomDetails = null;

        if (chatwebSocketClient == null) {
            Common.connectServer(activity, customerName, "0");
        }

        int idCustomer = preferences.getInt("IdCustomer", 0);
        String id = String.valueOf(idCustomer);
        if (idCustomer == 0){
            Common.showToast(activity, R.string.msg_NoProfileFound);
        }

        if (Common.networkConnected(activity)) {
            String url = Common.URL + "/PayDetailServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getUserRoomNumber");
            jsonObject.addProperty("idCustomer", id);
            String jsonOut = jsonObject.toString();
            userRoomNumber = new CommonTask(url, jsonOut);
            try {
                String jsonIn = userRoomNumber.execute().get();
                Type listType = new TypeToken<List<OrderRoomDetail>>() {
                }.getType();
                orderRoomDetails = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Common.showToast(activity, R.string.msg_NoNetwork);
        }
        for (OrderRoomDetail detail : orderRoomDetails) {
            if (detail.getRoomReservationStatus().equals("1")) {
                if (roomNumber == null || roomNumber.isEmpty()) {
                    roomNumber = "0";
                }
                roomNumber = detail.getRoomNumber();
                idRoomStatus = detail.getIdRoomStatus();
                Log.d(TAG, roomNumber);

            }
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
