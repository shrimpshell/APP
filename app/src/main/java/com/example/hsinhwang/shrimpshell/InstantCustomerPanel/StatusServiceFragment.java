package com.example.hsinhwang.shrimpshell.InstantCustomerPanel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.ChatMessage;
import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.Instant;
import com.example.hsinhwang.shrimpshell.Classes.StatusService;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;


public class StatusServiceFragment extends Fragment {
    RecyclerView rvStatusService;
    private LocalBroadcastManager broadcastManager;
    List<StatusService> statusServiceList;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_service,
                container, false);
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        registerChatReceiver();

        Common.connectServer(getActivity(),"603","0");
        handlerView(view);

        return view;

    }



    private void handlerView(View view) {

        rvStatusService = view.findViewById(R.id.rvStatusService);
        rvStatusService.setLayoutManager(new LinearLayoutManager(getActivity()));

        statusServiceList = new ArrayList<>();
        rvStatusService.setAdapter(new StatusServiceAdapter(getActivity(),statusServiceList));


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
            int serviceId = chatMessage.getServiceId();
            String item = chatMessage.getServiceItem();
            int status = chatMessage.getStatus();
            String quantity = String.valueOf(chatMessage.getQuantity());

            switch (serviceId){
                case 1: //Clean
                    if (status == 2) {
                        statusServiceList.add(new StatusService(R.drawable.icon_playing,
                                item,quantity,"清潔服務"));

                        rvStatusService.getAdapter().notifyItemInserted(statusServiceList.size());

                    } else if (status == 3) {

                        statusServiceList.add(new StatusService(R.drawable.icon_finish,
                                item,quantity,"清潔服務"));

                        rvStatusService.getAdapter().notifyItemInserted(statusServiceList.size());

                    }

                    break;

                case 2: //Room

                    if (status == 2) {
                        statusServiceList.add(new StatusService(R.drawable.icon_playing,
                                item,quantity,"房務服務"));

                        rvStatusService.getAdapter().notifyItemInserted(statusServiceList.size());

                    } else if (status == 3) {

                        statusServiceList.add(new StatusService(R.drawable.icon_finish,
                                item,quantity,"房務服務"));

                        rvStatusService.getAdapter().notifyItemInserted(statusServiceList.size());

                    }

                    break;

                case 3: //Dinling

                    if (status == 2) {
                        statusServiceList.add(new StatusService(R.drawable.icon_playing,
                                item,quantity,"餐點服務"));

                        rvStatusService.getAdapter().notifyItemInserted(statusServiceList.size());

                    } else if (status == 3) {

                        statusServiceList.add(new StatusService(R.drawable.icon_finish,
                                item,quantity,"餐點服務"));

                        rvStatusService.getAdapter().notifyItemInserted(statusServiceList.size());

                    }

                    break;

                default:

                    break;
            }
            rvStatusService.getAdapter().notifyDataSetChanged();

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

            myViewHolder.imageView.setImageResource(statusService.getImage());
            myViewHolder.tvItem.setText(statusService.getTvitem());
            myViewHolder.tvQuantity.setText(statusService.getTvquantity());
            myViewHolder.tvService.setText(statusService.getTvservice());



        }

    }


}
