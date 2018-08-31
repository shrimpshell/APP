package com.example.hsinhwang.shrimpshell.ManagerPanel;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.Events;
import com.example.hsinhwang.shrimpshell.EventActivity;
import com.example.hsinhwang.shrimpshell.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment {
    private RecyclerView eventFragmentRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 資料庫在這裡處理
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        eventFragmentRecyclerView = view.findViewById(R.id.eventFragmentRecyclerView);
        List<Events> actualEventList = new ArrayList<>();
        actualEventList.add(new Events(R.drawable.ss, "event1", "this is item_event 1", Date.valueOf("2018-09-01"), Date.valueOf("2018-09-02")));
        actualEventList.add(new Events(R.drawable.ss, "event2", "this is item_event 2", Date.valueOf("2018-09-03"), Date.valueOf("2018-09-04")));
        actualEventList.add(new Events(R.drawable.ss, "event3", "this is item_event 3", Date.valueOf("2018-09-05"), Date.valueOf("2018-09-06")));
        actualEventList.add(new Events(R.drawable.ss, "event4", "this is item_event 4", Date.valueOf("2018-09-07"), Date.valueOf("2018-09-08")));
        actualEventList.add(new Events(R.drawable.ss, "event5", "this is item_event 5", Date.valueOf("2018-09-09"), Date.valueOf("2018-09-10")));
        eventFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventFragmentRecyclerView.setAdapter(new EventAdapter(inflater, actualEventList));
        return view;

    }

    private class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
        LayoutInflater inflater;
        List<Events> eventList;
        TextView eventName, eventDetail;
        RelativeLayout eventItem;

        public EventAdapter(LayoutInflater inflater, List<Events> eventList) {
            this.inflater = inflater;
            this.eventList = eventList;
        }

        @NonNull
        @Override
        public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = inflater.inflate(R.layout.item_event_room, viewGroup, false);
            return new EventAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull EventAdapter.ViewHolder viewHolder, int i) {
            final Events event = eventList.get(i);
            eventName.setText(event.getName());
            eventDetail.setText(event.getDescription());
            eventItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ManagerEditActivity.class);
                    Bundle bundle = new Bundle();
                    Events innerEvent = new Events(event.getImageId(), event.getName(), event.getDescription(), event.getStartDate(), event.getEndDate());
                    bundle.putSerializable("event", innerEvent);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                eventName = itemView.findViewById(R.id.itemName);
                eventDetail = itemView.findViewById(R.id.itemDetail);;
                eventItem = itemView.findViewById(R.id.itemLayout);;
            }
        }
    }

}
