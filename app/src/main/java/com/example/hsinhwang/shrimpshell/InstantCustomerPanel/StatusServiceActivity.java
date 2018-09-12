package com.example.hsinhwang.shrimpshell.InstantCustomerPanel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.StatusService;
import com.example.hsinhwang.shrimpshell.R;


import java.util.ArrayList;
import java.util.List;


public class StatusServiceActivity extends AppCompatActivity {
    RecyclerView rvStatusService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_service);

        handlerView();
    }



    public void handlerView() {

        rvStatusService = findViewById(R.id.rvStatusService);
        rvStatusService.setLayoutManager(new LinearLayoutManager(this));


        List<StatusService> statusServiceList = getStatusServiceList();

        rvStatusService.setAdapter(new StatusServiceAdapter(this, statusServiceList));


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
            private TextView tvItem, tvStatus;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.ivStatusService);
                tvItem = itemView.findViewById(R.id.tvItemService);
                tvStatus = itemView.findViewById(R.id.tvStatusService);
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

            myViewHolder.imageView.setImageResource(statusService.getImage());
            myViewHolder.tvItem.setText(statusService.getTvitem());
            myViewHolder.tvStatus.setText(statusService.getTvstatus());





        }

    }


    public List<StatusService> getStatusServiceList() {
        List<StatusService> statusServicesList = new ArrayList<>();

        String intent = getIntent().getStringExtra("123");

        statusServicesList.add(new StatusService(R.drawable.icon_finish,
                "123",intent));



        return statusServicesList;
    }

}
