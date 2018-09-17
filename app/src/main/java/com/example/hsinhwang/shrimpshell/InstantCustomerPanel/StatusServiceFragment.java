package com.example.hsinhwang.shrimpshell.InstantCustomerPanel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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


public class StatusServiceFragment extends Fragment {
    RecyclerView rvStatusService;
    private LocalBroadcastManager broadcastManager;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_service,
                container, false);


        handlerView(view);

        return view;

    }

    private void handlerView(View view) {

        rvStatusService = view.findViewById(R.id.rvStatusService);
        rvStatusService.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<StatusService> statusServicesList = getStatusServiceList();



        rvStatusService.setAdapter(new StatusServiceAdapter(getActivity(),statusServicesList));


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
    private List<StatusService> getStatusServiceList() {
        final List<StatusService> statusServicesList = new ArrayList<>();



        statusServicesList.add(new StatusService(R.drawable.icon_finish,"123",
                "321"));
        return statusServicesList;

    }





}
