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
import com.example.hsinhwang.shrimpshell.Classes.EmployeeRoom;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRoomService extends AppCompatActivity {
    private static final String TAG = "EmployeeRoom";
    private LocalBroadcastManager broadcastManager;
    RecyclerView rvEmployeeRoomService;
    List<EmployeeRoom> employeeRoomList;
    SharedPreferences preferences;
    private String employeeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_room_service);
        broadcastManager = LocalBroadcastManager.getInstance(this);
        registerInstantReceiver();
        rvEmployeeRoomService = findViewById(R.id.rvEmployeeRoomService);
        rvEmployeeRoomService.setLayoutManager(new LinearLayoutManager(this));
        employeeRoomList = new ArrayList<>();
        rvEmployeeRoomService.setAdapter(new EmployeeRoomAdapter(this, employeeRoomList));

        preferences = getSharedPreferences(Common.EMPLOYEE_LOGIN, MODE_PRIVATE);
        employeeName = preferences.getString("email", "");

        Common.connectServer(this, employeeName, "2");

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
            String sender = chatMessage.getSenderId();
            String item = chatMessage.getServiceItem();
            int status = chatMessage.getStatus();
            String quantity = String.valueOf(chatMessage.getQuantity());
            Log.d(TAG, "Room get: " + message);
            switch (status) {
                case 1:
                    employeeRoomList.add(new EmployeeRoom(R.drawable.icon_unfinish,
                            "1", sender, item, quantity));
                    rvEmployeeRoomService.getAdapter().notifyItemInserted(employeeRoomList.size());

                    break;

                case 2:
                    employeeRoomList.add(new EmployeeRoom(R.drawable.icon_playing,
                            "2", sender, item, quantity));
                    rvEmployeeRoomService.getAdapter().notifyItemInserted(employeeRoomList.size());

                    break;

                case 3:
                    employeeRoomList.add(new EmployeeRoom(R.drawable.icon_finish,
                            "3", sender, item, quantity));
                    rvEmployeeRoomService.getAdapter().notifyItemInserted(employeeRoomList.size());

                    break;

                default:
                    break;
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
            TextView tvRoomId, tvItem, tvQuantity, tvStatusNumber;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.ivEmployeeRoomService);
                tvRoomId = itemView.findViewById(R.id.tvEmployeeRoomServiceRoomId);
                tvItem = itemView.findViewById(R.id.tvEmployeeRoomServiceItem);
                tvQuantity = itemView.findViewById(R.id.tvEmployeeRoomServiceQuantity);
                tvStatusNumber = itemView.findViewById(R.id.tvEmployeeRoomServiceStatus);
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

            myViewHolder.imageView.setImageResource(employeeRoom.getImageStatus());
            myViewHolder.tvRoomId.setText(employeeRoom.getTvRoomId());
            myViewHolder.tvItem.setText(employeeRoom.getTvItem());
            myViewHolder.tvQuantity.setText(employeeRoom.getTvQuantity());
            myViewHolder.tvStatusNumber.setText(employeeRoom.getTvStatusNumber());

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatMessage chatMessage;
                    String chatMessageJson;
                    switch (Integer.parseInt(myViewHolder.tvStatusNumber.getText().toString())) {

                        case 1:
                            chatMessage = new ChatMessage(employeeName,
                                    myViewHolder.tvRoomId.getText().toString()
                                    , "2", "0",
                                    myViewHolder.tvItem.getText().toString()
                                    , 2, 2,
                                    Integer.parseInt(myViewHolder.tvQuantity.getText().toString()));
                            chatMessageJson = new Gson().toJson(chatMessage);
                            Common.chatwebSocketClient.send(chatMessageJson);
                            Log.d(TAG, "output: " + chatMessageJson);

                            break;
                        case 2:
                            chatMessage = new ChatMessage(employeeName,
                                    myViewHolder.tvRoomId.getText().toString()
                                    , "2", "0",
                                    myViewHolder.tvItem.getText().toString()
                                    , 2, 3,
                                    Integer.parseInt(myViewHolder.tvQuantity.getText().toString()));
                            chatMessageJson = new Gson().toJson(chatMessage);
                            Common.chatwebSocketClient.send(chatMessageJson);
                            Log.d(TAG, "output: " + chatMessageJson);

                            break;

                    }
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
