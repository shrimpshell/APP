package com.example.hsinhwang.shrimpshell.InstantEmployeePanel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.example.hsinhwang.shrimpshell.Classes.EmployeeCall;
import com.example.hsinhwang.shrimpshell.Classes.EmployeeClean;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class EmployeeCleanService extends AppCompatActivity {
    private static final String TAG = "EmployeeClean";
    private LocalBroadcastManager broadcastManager;
    RecyclerView rvEmployeeClean;
    List<EmployeeClean> employeeCleanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_clean_service);
        broadcastManager = LocalBroadcastManager.getInstance(this);
        registerInstantReceiver();
        rvEmployeeClean = findViewById(R.id.rvEmployeeClean);
        rvEmployeeClean.setLayoutManager(new LinearLayoutManager(this));
        employeeCleanList = new ArrayList<>();
        rvEmployeeClean.setAdapter(new EmployeeCleanAdapter(this, employeeCleanList));

        Common.connectServer(this, Common.EMPLOYEE_LOGIN, "1");

    }

    private void registerInstantReceiver() {
        IntentFilter cleanFilter = new IntentFilter("1");
        ChatReceiver chatReceiver = new ChatReceiver();
        broadcastManager.registerReceiver(chatReceiver, cleanFilter);

    }


    private class ChatReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
            String sender = chatMessage.getSenderId();
            int status = chatMessage.getStatus();
            Log.d(TAG, "Clean get: " + message);
            switch (status) {
                case 1:
                    employeeCleanList.add(new EmployeeClean(R.drawable.icon_unfinish,
                            "1", sender));
                    rvEmployeeClean.getAdapter().notifyItemInserted(employeeCleanList.size());

                    break;
                case 2:
                    employeeCleanList.add(new EmployeeClean(R.drawable.icon_playing,
                            "2", sender));
                    rvEmployeeClean.getAdapter().notifyItemInserted(employeeCleanList.size());

                    break;
                case 3:
                    employeeCleanList.add(new EmployeeClean(R.drawable.icon_finish,
                            "3", sender));
                    rvEmployeeClean.getAdapter().notifyItemInserted(employeeCleanList.size());
                    break;
                default:
                    break;

            }
            rvEmployeeClean.getAdapter().notifyDataSetChanged();

        }


    }

    private class EmployeeCleanAdapter extends RecyclerView.Adapter<EmployeeCleanAdapter.MyViewHolder> {
        Context context;
        List<EmployeeClean> employeeCleanList;

        EmployeeCleanAdapter(Context context, List<EmployeeClean> employeeCleanList) {
            this.context = context;
            this.employeeCleanList = employeeCleanList;


        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView tvRoomId, tvStatusNumber;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.ivEmployeeClean);
                tvRoomId = itemView.findViewById(R.id.tvEmployeeCleanRoomId);
                tvStatusNumber = itemView.findViewById(R.id.tvEmployeeCleanStatus);

            }
        }

        @Override
        public int getItemCount() {
            return employeeCleanList.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(context).
                    inflate(R.layout.item_status_employee_instant_clean, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {
            final EmployeeClean employeeClean = employeeCleanList.get(position);

            myViewHolder.imageView.setImageResource(employeeClean.getImageStatus());
            myViewHolder.tvRoomId.setText(employeeClean.getTvRooId());
            myViewHolder.tvStatusNumber.setText(employeeClean.getTvStatusNumber());

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatMessage chatMessage;
                    String chatMessageJson;
                    switch (Integer.parseInt(myViewHolder.tvStatusNumber.getText().toString())) {

                        case 1:
                            chatMessage = new ChatMessage
                                    (Common.EMPLOYEE_LOGIN,
                                            myViewHolder.tvRoomId.getText().toString(),
                                            "1", "0", "0",
                                            1, 2, 0);
                            chatMessageJson = new Gson().toJson(chatMessage);
                            Common.chatwebSocketClient.send(chatMessageJson);
                            Log.d(TAG, "output: " + chatMessageJson);
                            break;

                        case 2:
                            chatMessage = new ChatMessage
                                    (Common.EMPLOYEE_LOGIN,
                                            myViewHolder.tvRoomId.getText().toString(),
                                            "1", "0", "0",
                                            1, 3, 0);
                            chatMessageJson = new Gson().toJson(chatMessage);
                            Common.chatwebSocketClient.send(chatMessageJson);
                            Log.d(TAG, "output: " + chatMessageJson);
                            break;

                    }
                }
            });


        }


    }


}
