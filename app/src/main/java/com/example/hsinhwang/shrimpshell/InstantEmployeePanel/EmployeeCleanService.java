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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.ChatMessage;
import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.EmployeeClean;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class EmployeeCleanService extends AppCompatActivity {
    private LocalBroadcastManager broadcastManager;
    private RecyclerView rvEmployeeClean;
    private List<EmployeeClean> employeeCleanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_clean_service);
        broadcastManager = LocalBroadcastManager.getInstance(this);
        registerInstantReceiver();
        rvEmployeeClean = findViewById(R.id.rvEmployeeClean);
        rvEmployeeClean.setLayoutManager(new LinearLayoutManager(this));
        employeeCleanList = getEmployeeClean();
        rvEmployeeClean.setAdapter(new EmployeeCleanAdapter(this, employeeCleanList));

        //Common.connectServer(this, "EmployeeCall","2");

    }

    private void registerInstantReceiver() {
        IntentFilter instantFilter = new IntentFilter("Call");
        InstantReceiver instantReceiver = new InstantReceiver();
        broadcastManager.registerReceiver(instantReceiver, instantFilter);

    }

    public List<EmployeeClean> getEmployeeClean() {
        List<EmployeeClean> employeeCleanList = new ArrayList<>();



        return employeeCleanList;
    }

    private class InstantReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
            String Sender = chatMessage.getSender();
            employeeCleanList.add(new EmployeeClean(R.drawable.icon_finish,Sender));

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
            TextView tvRoomId;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.ivEmployeeClean);
                tvRoomId = itemView.findViewById(R.id.tvEmployeeCleanRoomId);

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
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
            final EmployeeClean employeeClean = employeeCleanList.get(position);

            myViewHolder.imageView.setImageResource(employeeClean.getImageStatus());
            myViewHolder.tvRoomId.setText(employeeClean.getTvRooId());


        }




    }
}
