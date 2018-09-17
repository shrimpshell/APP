package com.example.hsinhwang.shrimpshell.InstantEmployeePanel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.ChatMessage;
import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.EmployeeCall;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class EmployeeCallService extends AppCompatActivity {
    private LocalBroadcastManager broadcastManager;
    private RecyclerView rvEmployeeCall;
    private List<EmployeeCall> employeeCallList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_call_service);
        broadcastManager = LocalBroadcastManager.getInstance(this);
        registerInstantReceiver();
        rvEmployeeCall = findViewById(R.id.rvEmployeeCall);
        rvEmployeeCall.setLayoutManager(new LinearLayoutManager(this));
        employeeCallList = getEmployeeCall();
        rvEmployeeCall.setAdapter(new EmployeeCallAdapter(this, employeeCallList));

        Common.connectServer(this, "E007", "1");

    }

    private List<EmployeeCall> getEmployeeCall() {
        List<EmployeeCall> employeeCallList = new ArrayList<>();

        return employeeCallList;
    }

    private void registerInstantReceiver() {
        IntentFilter unFinishFilter = new IntentFilter("未處理");
        IntentFilter finishFilter = new IntentFilter("已完成");
        InstantReceiver instantReceiver = new InstantReceiver();
        broadcastManager.registerReceiver(instantReceiver, unFinishFilter);
        broadcastManager.registerReceiver(instantReceiver, finishFilter);

    }

    private class InstantReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
            String sender = chatMessage.getSender();
            String state = chatMessage.getState();
            if (state.equals("未處理")) {
                employeeCallList.add(new EmployeeCall(R.drawable.icon_unfinish, sender));

                rvEmployeeCall.getAdapter().notifyDataSetChanged();

            } else if (state.equals("已完成")) {


            }


        }
    }


    private class EmployeeCallAdapter extends RecyclerView.Adapter<EmployeeCallAdapter.MyViewHolder> {
        Context context;
        List<EmployeeCall> employeeCallList;

        public EmployeeCallAdapter(Context context, List<EmployeeCall> employeeCallList) {
            this.context = context;
            this.employeeCallList = employeeCallList;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView tvRoomId;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.ivEmployeeCall);
                tvRoomId = itemView.findViewById(R.id.tvEmployeeCallRoomId);
            }
        }

        @Override
        public int getItemCount() {
            return employeeCallList.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(context).
                    inflate(R.layout.item_status_employee_instant_call, viewGroup, false);

            return new MyViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
            final EmployeeCall employeeCall = employeeCallList.get(position);

            myViewHolder.imageView.setImageResource(employeeCall.getImageStatus());
            myViewHolder.tvRoomId.setText(employeeCall.getTvRoomID());
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });


        }


    }


}
