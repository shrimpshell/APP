package com.example.hsinhwang.shrimpshell;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    private RecyclerView event_recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        initialization();
    }

    private void initialization() {
        event_recyclerView = findViewById(R.id.event_recyclerView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        event_recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(1,
                        StaggeredGridLayoutManager.HORIZONTAL));

        event_recyclerView.setAdapter(new EventAdapter(this, getEventList()));

        /* 不處理捲動事件所以監聽器設為null */
        event_recyclerView.setOnFlingListener(null); // 因為PagerSnapHelper也有setOnFlingListener。怕衝突所以要把recyclerView這邊的監聽器改null
        /* 如果希望一次滑動一頁資料，要加上PagerSnapHelper物件 */
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper(); // 監聽就以PagerSnapHelper為主
        pagerSnapHelper.attachToRecyclerView(event_recyclerView);
    }

    private class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
        private Context context;
        private List<Events> constructorEventList;

        public EventAdapter(Context context, List<Events> constructorEventList) {
            this.context = context;
            this.constructorEventList = constructorEventList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_event, viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            final Events event = constructorEventList.get(i);
            myViewHolder.event_name.setText(event.getName());
            myViewHolder.event_time_range.setText("活動期間：\n" + String.valueOf(event.getStart_date() + " ~ " + event.getEnd_date()));
            myViewHolder.event_description.setText(event.getDescription());
        }

        @Override
        public int getItemCount() {
            return constructorEventList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView event_name, event_time_range, event_description;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                event_name = itemView.findViewById(R.id.event_name);
                event_time_range = itemView.findViewById(R.id.event_time_range);
                event_description = itemView.findViewById(R.id.event_description);
            }
        }
    }

    public List<Events> getEventList() {
        List<Events> eventList = new ArrayList<>();
        eventList.add(new Events("活動1", "活動1的內容", Date.valueOf("2018-09-01"), Date.valueOf("2018-09-02")));
        eventList.add(new Events("活動2", "活動2的內容", Date.valueOf("2018-09-03"), Date.valueOf("2018-09-04")));
        eventList.add(new Events("活動3", "活動3的內容", Date.valueOf("2018-09-05"), Date.valueOf("2018-09-06")));
        eventList.add(new Events("活動4", "活動4的內容", Date.valueOf("2018-09-07"), Date.valueOf("2018-09-08")));
        return eventList;
    }
}
