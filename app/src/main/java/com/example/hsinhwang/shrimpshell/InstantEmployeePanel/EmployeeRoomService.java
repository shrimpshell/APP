package com.example.hsinhwang.shrimpshell.InstantEmployeePanel;

import android.content.Context;
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

import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.EmployeeRoom;
import com.example.hsinhwang.shrimpshell.R;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRoomService extends AppCompatActivity {
    private LocalBroadcastManager broadcastManager;
    private RecyclerView rvEmployeeRoomService;
    private List<EmployeeRoom> employeeRoomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_room_service);
        broadcastManager = LocalBroadcastManager.getInstance(this);
        registerInstantReceiver();
        rvEmployeeRoomService = findViewById(R.id.rvEmployeeRoomService);
        rvEmployeeRoomService.setLayoutManager(new LinearLayoutManager(this));
        employeeRoomList = getEmployeeRoom();
        rvEmployeeRoomService.setAdapter(new EmployeeRoomAdapter(this,employeeRoomList));

        //Common.connectServer();
    }

    private List<EmployeeRoom> getEmployeeRoom() {
        List<EmployeeRoom> employeeRoomList = new ArrayList<>();

        return employeeRoomList;
    }

    private void registerInstantReceiver() {

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
            TextView tvRoomId,tvItem,tvQuantity;

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
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
            final EmployeeRoom employeeRoom = employeeRoomList.get(position);

            myViewHolder.imageView.setImageResource(employeeRoom.getImageStatus());
            myViewHolder.tvRoomId.setText(employeeRoom.getTvRoomId());
            myViewHolder.tvItem.setText(employeeRoom.getTvItem());
            myViewHolder.tvQuantity.setText(employeeRoom.getTvQuantity());

        }


    }
}
