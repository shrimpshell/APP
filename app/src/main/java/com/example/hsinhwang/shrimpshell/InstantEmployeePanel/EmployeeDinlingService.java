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
import com.example.hsinhwang.shrimpshell.Classes.EmployeeDinling;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDinlingService extends AppCompatActivity {
    private LocalBroadcastManager broadcastManager;
    RecyclerView rvEmployeeDinling;
    private List<EmployeeDinling> employeeDinlingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dinling_service);
        broadcastManager = LocalBroadcastManager.getInstance(this);
        registerInstantReceiver();
        rvEmployeeDinling = findViewById(R.id.rvEmployeeDinling);
        rvEmployeeDinling.setLayoutManager(new LinearLayoutManager(this));
        employeeDinlingList = new ArrayList<>();
        rvEmployeeDinling.setAdapter(new EmployeeDinlingAdapter(this,employeeDinlingList));

        Common.connectServer(this,"E001","3");

    }



    private void registerInstantReceiver() {
        IntentFilter dinlingFilter = new IntentFilter("3");
        ChatReceiver chatReceiver = new ChatReceiver();
        broadcastManager.registerReceiver(chatReceiver, dinlingFilter);



    }

    private class ChatReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
            String sender = chatMessage.getSenderId();
            String item = chatMessage.getServiceItem();
            int status = chatMessage.getStatus();
            String quantiyty = String.valueOf(chatMessage.getQuantity());

            switch (status){
                case 1: //未完成
                    employeeDinlingList.add(new EmployeeDinling(R.drawable.icon_unfinish
                    ,sender,item,quantiyty));

                    rvEmployeeDinling.getAdapter().notifyItemInserted(employeeDinlingList.size());

                    break;

                case 2: //處理中
                    employeeDinlingList.add(new EmployeeDinling(R.drawable.icon_playing
                            ,sender,item,quantiyty));

                    rvEmployeeDinling.getAdapter().notifyItemInserted(employeeDinlingList.size());

                    break;


                case 3: //已完成
                    employeeDinlingList.add(new EmployeeDinling(R.drawable.icon_finish
                            ,sender,item,quantiyty));

                    rvEmployeeDinling.getAdapter().notifyItemInserted(employeeDinlingList.size());

                    break;

                default:

                    break;
            }

            rvEmployeeDinling.getAdapter().notifyDataSetChanged();

        }
    }





    private class EmployeeDinlingAdapter extends
            RecyclerView.Adapter<EmployeeDinlingAdapter.MyViewHolder> {
        Context context;
        List<EmployeeDinling> employeeDinlingList;

        EmployeeDinlingAdapter(Context context, List<EmployeeDinling> employeeDinlingList) {
            this.context = context;
            this.employeeDinlingList = employeeDinlingList;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView tvRoomId,tvItem,tvQuantity;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.ivEmployeeDinling);
                tvRoomId = itemView.findViewById(R.id.tvEmployeeDinlingRoomId);
                tvItem = itemView.findViewById(R.id.tvEmployeeDinlingItem);
                tvQuantity = itemView.findViewById(R.id.tvEmployeeDinlingQuantity);
            }
        }

        @Override
        public int getItemCount() {
            return employeeDinlingList.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int typeView) {
            View itemView = LayoutInflater.from(context).
                    inflate(R.layout.item_status_employee_instant_dinling,
                            viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
            final EmployeeDinling employeeDinling = employeeDinlingList.get(position);

            myViewHolder.imageView.setImageResource(employeeDinling.getImageStatus());
            myViewHolder.tvRoomId.setText(employeeDinling.getTvRoomId());
            myViewHolder.tvItem.setText(employeeDinling.getTvItem());
            myViewHolder.tvQuantity.setText(employeeDinling.getTvQuantity());

        }




    }


}
