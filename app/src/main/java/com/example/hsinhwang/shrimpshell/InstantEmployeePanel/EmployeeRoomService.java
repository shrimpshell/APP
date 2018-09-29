package com.example.hsinhwang.shrimpshell.InstantEmployeePanel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.hsinhwang.shrimpshell.Classes.EmployeeRoom;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.example.hsinhwang.shrimpshell.Classes.Common.chatwebSocketClient;

public class EmployeeRoomService extends AppCompatActivity {
    private static final String TAG = "EmployeeRoom";
    private LocalBroadcastManager broadcastManager;
    RecyclerView rvEmployeeRoomService;
    List<EmployeeRoom> employeeRooms;
    SharedPreferences preferences;
    private String employeeName;
    private CommonTask employeeStatus;
    EmployeeRoomAdapter adapter;
    int idInstantDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_room_service);
        broadcastManager = LocalBroadcastManager.getInstance(this);
        registerInstantReceiver();
        rvEmployeeRoomService = findViewById(R.id.rvEmployeeRoomService);
        rvEmployeeRoomService.setLayoutManager(new LinearLayoutManager(this));
        employeeRooms = getEmployeeRoomList();

        adapter = new EmployeeRoomAdapter(this, employeeRooms);
        rvEmployeeRoomService.setAdapter(adapter);

        preferences = getSharedPreferences(Common.EMPLOYEE_LOGIN, MODE_PRIVATE);
        employeeName = preferences.getString("email", "");

        Common.connectServer(this, employeeName, "2");

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (chatwebSocketClient == null) {
            Common.connectServer(this,employeeName,"2");
        }

        if (Common.networkConnected(this)) {
            String url = Common.URL + "/InstantServlet";
            List<EmployeeRoom> employeeRooms = null;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getEmployeeStatus");
            jsonObject.addProperty("idInstantService", 2);
            String jsonOut = jsonObject.toString();
            employeeStatus = new CommonTask(url, jsonOut);
            try {
                String jsonIn = employeeStatus.execute().get();
                Type listType = new TypeToken<List<EmployeeRoom>>() {
                }.getType();
                employeeRooms = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (employeeRooms == null || employeeRooms.isEmpty()) {
                Common.showToast(this, R.string.msg_NoInstantFound);
            } else {
                rvEmployeeRoomService.setAdapter
                        (new EmployeeRoomAdapter(this, employeeRooms));
            }

        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
    }

    private List<EmployeeRoom> getEmployeeRoomList() {
        List<EmployeeRoom> employeeRoomList = new ArrayList<>();



        return employeeRoomList;
    }


    private void registerInstantReceiver() {
        IntentFilter roomFilter = new IntentFilter("2");
        ChatReceiver chatReceiver = new ChatReceiver();
        broadcastManager.registerReceiver(chatReceiver, roomFilter);


    }

    private class ChatReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
            idInstantDetail = chatMessage.getInstantNumber();
            if (idInstantDetail != 0 ) {

                if (Common.networkConnected(EmployeeRoomService.this)) {
                    String url = Common.URL + "/InstantServlet";
                    List<EmployeeRoom> employeeRooms = null;
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("action", "getEmployeeStatus");
                    jsonObject.addProperty("idInstantService", 2);
                    String jsonOut = jsonObject.toString();
                    employeeStatus = new CommonTask(url, jsonOut);
                    try {
                        String jsonIn = employeeStatus.execute().get();
                        Type listType = new TypeToken<List<EmployeeRoom>>() {
                        }.getType();
                        employeeRooms = new Gson().fromJson(jsonIn, listType);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                    if (employeeRooms == null || employeeRooms.isEmpty()) {
                        Common.showToast(EmployeeRoomService.this, R.string.msg_NoInstantFound);
                    } else {
                        rvEmployeeRoomService.setAdapter(null);
                        rvEmployeeRoomService.setAdapter
                                (new EmployeeRoomAdapter(EmployeeRoomService.this, employeeRooms));
                    }

                } else {
                    Common.showToast(EmployeeRoomService.this, R.string.msg_NoNetwork);
                }

                rvEmployeeRoomService.getAdapter().notifyDataSetChanged();

            }


        }
    }


    private class EmployeeRoomAdapter extends RecyclerView.Adapter<EmployeeRoomAdapter.MyViewHolder> {
        Context context;
        List<EmployeeRoom> employeeRoomList;

        EmployeeRoomAdapter(Context context, List<EmployeeRoom> employeeRoomList) {
            this.context = context;
            this.employeeRoomList = employeeRoomList;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView tvRoomId, tvItem, tvQuantity;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.ivEmployeeRoomService);
                tvRoomId = itemView.findViewById(R.id.tvEmployeeRoomServiceRoomId);
                tvItem = itemView.findViewById(R.id.tvEmployeeRoomServiceItem);
                tvQuantity = itemView.findViewById(R.id.tvEmployeeRoomServiceQuantity);

            }
        }

        @Override
        public int getItemCount() {
            return employeeRoomList.size();
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viweType) {
            View itemView = LayoutInflater.from(context).
                    inflate(R.layout.item_status_employee_instant_roomservice,
                            viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {
            final EmployeeRoom employeeRoom = employeeRoomList.get(position);

            switch (employeeRoom.getStatus()) {
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

            switch (employeeRoom.getIdInstantType()) {
                case 4:
                    myViewHolder.tvItem.setText(R.string.service_type_4);
                    break;
                case 5:
                    myViewHolder.tvItem.setText(R.string.service_type_5);
                    break;
                case 6:
                    myViewHolder.tvItem.setText(R.string.service_type_6);
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

            if (String.valueOf(employeeRoom.getQuantity()).equals("0")) {

                myViewHolder.tvQuantity.setVisibility(View.GONE);

            } else {

                myViewHolder.tvQuantity.setText(String.valueOf(employeeRoom.getQuantity()));
            }

            myViewHolder.tvRoomId.setText(employeeRoom.getRoomNumber());

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int status = 0;
                    String roomNumber = employeeRoom.getRoomNumber();
                    idInstantDetail = employeeRoom.getIdInstantDetail();


                    if (employeeRoom.getStatus() == 1) {

                        status = 2;

                    } else if (employeeRoom.getStatus() == 2 || employeeRoom.getStatus() == 3) {

                        status = 3;

                    }

                    if (Common.networkConnected(EmployeeRoomService.this)) {
                        String url = Common.URL + "/InstantServlet";
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "updateStatus");
                        jsonObject.addProperty("idInstantDetail", new Gson().toJson(idInstantDetail));
                        jsonObject.addProperty("status", new Gson().toJson(status));
                        int count = 0;
                        try {
                            String result = new CommonTask(url, jsonObject.toString()).execute().get();
                            count = Integer.valueOf(result);
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                        if (count == 0) {
                            Common.showToast(EmployeeRoomService.this, R.string.msg_UpdateFail);
                        } else {
                            Common.showToast(EmployeeRoomService.this, R.string.msg_UpdateSuccess);
                        }
                    } else {
                        Common.showToast(EmployeeRoomService.this, R.string.msg_NoNetwork);
                    }

                    if (Common.networkConnected(EmployeeRoomService.this)) {
                        String url = Common.URL + "/InstantServlet";
                        List<EmployeeRoom> employeeRooms = null;
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "getEmployeeStatus");
                        jsonObject.addProperty("idInstantService", 2);
                        String jsonOut = jsonObject.toString();
                        employeeStatus = new CommonTask(url, jsonOut);
                        try {
                            String jsonIn = employeeStatus.execute().get();
                            Type listType = new TypeToken<List<EmployeeRoom>>() {
                            }.getType();
                            employeeRooms = new Gson().fromJson(jsonIn, listType);
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                        if (employeeRooms == null || employeeRooms.isEmpty()) {
                            Common.showToast(EmployeeRoomService.this, R.string.msg_NoInstantFound);
                        } else {
                            rvEmployeeRoomService.setAdapter(null);
                            rvEmployeeRoomService.setAdapter
                                    (new EmployeeRoomAdapter(EmployeeRoomService.this, employeeRooms));
                            myViewHolder.itemView.setEnabled(false);

                        }

                    } else {
                        Common.showToast(EmployeeRoomService.this, R.string.msg_NoNetwork);
                    }



                    ChatMessage chatMessage =
                            new ChatMessage(employeeName, roomNumber, "2",
                                    "0", 2, idInstantDetail);
                    String chatMessageJson = new Gson().toJson(chatMessage);
                    Common.chatwebSocketClient.send(chatMessageJson);
                    Log.d(TAG, "output: " + chatMessageJson);

                    myViewHolder.itemView.setEnabled(true);

                    adapter.notifyDataSetChanged();



                }
            });

        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Common.disconnectServer();
    }


}
