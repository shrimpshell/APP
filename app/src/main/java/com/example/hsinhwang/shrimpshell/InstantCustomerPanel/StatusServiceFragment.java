package com.example.hsinhwang.shrimpshell.InstantCustomerPanel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.ChatMessage;
import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.EmployeeDinling;
import com.example.hsinhwang.shrimpshell.Classes.Instant;
import com.example.hsinhwang.shrimpshell.Classes.StatusService;
import com.example.hsinhwang.shrimpshell.InstantEmployeePanel.EmployeeDinlingService;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class StatusServiceFragment extends Fragment {
    FragmentActivity activity;
    RecyclerView rvStatusService;
    private LocalBroadcastManager broadcastManager;
    private CommonTask customerStatus;
    List<StatusService> statusServiceList;
    SharedPreferences preferences, roomNumber;
    String customerName;
    StatusServiceAdapter adapter;
    int idInstantDetail;
    String roomNumber_A;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_service,
                container, false);
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        registerChatReceiver();

        activity = getActivity();
        rvStatusService = view.findViewById(R.id.rvStatusService);
        rvStatusService.setLayoutManager(new LinearLayoutManager(getActivity()));

        statusServiceList = getStatusServiceList();
        adapter = new StatusServiceAdapter(getActivity(),statusServiceList);
        rvStatusService.setAdapter(adapter);

        preferences = getActivity().getSharedPreferences(Common.LOGIN, MODE_PRIVATE);
        customerName = preferences.getString("email", "");






        Common.connectServer(activity,customerName,"0");


        return view;

    }

    private List<StatusService> getStatusServiceList() {
        List<StatusService> statusServiceList = new ArrayList<>();

        return statusServiceList;
    }

    @Override
    public void onStart() {
        super.onStart();

        roomNumber = getActivity().getSharedPreferences(Common.INSTANT_TEST, MODE_PRIVATE);
        if (customerName.equals("cc@gmail.com")) {
            roomNumber_A = roomNumber.getString("roomNumber1","");
        } else {
            roomNumber_A = roomNumber.getString("roomNumber2","");
        }



        if (Common.networkConnected(activity)) {
            String url = Common.URL + "/InstantServlet";
            List<StatusService> statusServices = null;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getCustomerStatus");
            jsonObject.addProperty("roomNumber", roomNumber_A);
            String jsonOut = jsonObject.toString();
            customerStatus = new CommonTask(url, jsonOut);
            try {
                String jsonIn = customerStatus.execute().get();
                Type listType = new TypeToken<List<StatusService>>() {
                }.getType();
                statusServices = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (statusServices == null || statusServices.isEmpty()) {
                Common.showToast(activity, R.string.msg_NoInstantFound);
            } else {
                rvStatusService.setAdapter
                        (new StatusServiceAdapter(activity, statusServices));
            }

        } else {
            Common.showToast(activity, R.string.msg_NoNetwork);
        }
    }

    private List<StatusService> statusServiceList() {
        List<StatusService> statusServiceList = new ArrayList<>();

        return statusServiceList;
    }

    private void registerChatReceiver() {
        IntentFilter cleanFilter = new IntentFilter("1");
        IntentFilter roomFilter = new IntentFilter("2");
        IntentFilter dinlingFilter = new IntentFilter("3");
        ChatReceiver chatReceiver = new ChatReceiver();
        broadcastManager.registerReceiver(chatReceiver, cleanFilter);
        broadcastManager.registerReceiver(chatReceiver, roomFilter);
        broadcastManager.registerReceiver(chatReceiver, dinlingFilter);
    }


    private class ChatReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
            idInstantDetail = chatMessage.getInstantNumber();

            if (idInstantDetail != 0) {

                if (Common.networkConnected(activity)) {
                    String url = Common.URL + "/InstantServlet";
                    List<StatusService> statusServices = null;
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("action", "getCustomerStatus");
                    jsonObject.addProperty("roomNumber", roomNumber_A);
                    String jsonOut = jsonObject.toString();
                    customerStatus = new CommonTask(url, jsonOut);
                    try {
                        String jsonIn = customerStatus.execute().get();
                        Type listType = new TypeToken<List<StatusService>>() {
                        }.getType();
                        statusServices = new Gson().fromJson(jsonIn, listType);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                    if (statusServices == null || statusServices.isEmpty()) {
                        Common.showToast(activity, R.string.msg_NoInstantFound);
                    } else {
                        rvStatusService.setAdapter
                                (new StatusServiceAdapter(activity, statusServices));
                    }

                } else {
                    Common.showToast(activity, R.string.msg_NoNetwork);
                }

                rvStatusService.getAdapter().notifyDataSetChanged();


            }


        }
    }



    private class StatusServiceAdapter
            extends RecyclerView.Adapter<StatusServiceAdapter.MyViewHolder> {
        private Context context;
        private List<StatusService> statusServiceList;



        StatusServiceAdapter(Context context, List<StatusService> statusServiceList) {
            this.context = context;
            this.statusServiceList = statusServiceList;


        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView tvItem, tvQuantity, tvService;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.ivStatusService);
                tvItem = itemView.findViewById(R.id.tvItemService);
                tvQuantity = itemView.findViewById(R.id.tvQuantityService);
                tvService = itemView.findViewById(R.id.tvService);
            }
        }


        @Override
        public int getItemCount() {
            return statusServiceList.size();
        }

        @NonNull
        @Override
        public StatusServiceAdapter.MyViewHolder onCreateViewHolder
                (@NonNull ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(context).
                    inflate(R.layout.item_status_service, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull StatusServiceAdapter.
                MyViewHolder myViewHolder, int position) {
            final StatusService statusService = statusServiceList.get(position);

            switch (statusService.getStatus()) {
                case 1:
                    myViewHolder.imageView.setImageResource(R.drawable.icon_unfinish);
                    break;
                case 2:
                    myViewHolder.imageView.setImageResource(R.drawable.icon_playing);
                    break;
                case 3:
                    myViewHolder.imageView.setImageResource(R.drawable.icon_finish);
                    break;
                default:
                    break;
            }

            switch (statusService.getIdInstantType()) {
                case 1:
                    myViewHolder.tvItem.setText(R.string.service_type_1);
                    break;
                case 2:
                    myViewHolder.tvItem.setText(R.string.service_type_2);
                    break;
                case 3:
                    myViewHolder.tvItem.setText(R.string.service_type_3);
                    break;
                case 4:
                    myViewHolder.tvItem.setText(R.string.service_type_4);
                    break;
                case 5:
                    myViewHolder.tvItem.setText(R.string.service_type_5);
                    break;
                case 6:
                    myViewHolder.tvItem.setText(R.string.service_type_6);
                    break;
                case 7:
                    myViewHolder.tvItem.setText(R.string.service_type_7);
                    break;
                case 8:
                    myViewHolder.tvItem.setText(R.string.service_type_8);
                    break;
                case 9:
                    myViewHolder.tvItem.setText(R.string.service_type_9);
                    break;
                case 10:
                    myViewHolder.tvItem.setText(R.string.service_type_10);
                    break;
                default:
                    break;
            }

            switch (statusService.getIdInstantService()) {
                case 1:
                    myViewHolder.tvService.setText(R.string.service_1);
                    break;
                case 2:
                    myViewHolder.tvService.setText(R.string.service_2);
                    break;
                case 3:
                    myViewHolder.tvService.setText(R.string.service_3);
                    break;
                default:
                    break;
            }

            myViewHolder.tvQuantity.setText(String.valueOf(statusService.getQuantity()));



        }

    }

}
