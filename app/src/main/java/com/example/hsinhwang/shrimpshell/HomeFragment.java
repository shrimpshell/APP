package com.example.hsinhwang.shrimpshell;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerview;
    private boolean isLoggedIn = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        List<MainOptions> optionList = new ArrayList<>();
        if (isLoggedIn) {
        } else {
        }
        optionList.add(new MainOptions(R.string.intro_title, "飯店介紹", R.drawable.introduction));
        optionList.add(new MainOptions(R.string.location_title, "交通資訊", R.drawable.transpotation));
        optionList.add(new MainOptions(R.string.event_title, "活動優惠", R.drawable.events));
        optionList.add(new MainOptions(R.string.rp_title, "房間預覽", R.drawable.room_review));

        recyclerview = view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(new MainAdapter(inflater, optionList));
        return view;
    }

    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
        LayoutInflater inflater;
        List<MainOptions> mainOptions;
        TextView tvTitle;
        RelativeLayout main_item;


        public MainAdapter(LayoutInflater inflater, List<MainOptions> mainOptions) {
            this.inflater = inflater;
            this.mainOptions = mainOptions;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = inflater.inflate(R.layout.item_main, viewGroup, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final MainOptions mainOption = mainOptions.get(i);
            tvTitle.setText(mainOption.getOptionTitle());
            main_item.setBackgroundResource(mainOption.getImageId());
            main_item.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    switch (mainOption.getOptionId()) {
                        case R.string.intro_title:
                            onClickIntro();
                            break;
                        case R.string.location_title:
                            onClickLocation();
                            break;
                        case R.string.event_title:
                            onClickEvents();
                            break;
                        case R.string.rp_title:
                            onClickRoomPreview();
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return mainOptions.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.main_title);
                main_item = itemView.findViewById(R.id.main_item);
            }
        }
    }

    public void onClickIntro() {
        Intent intent = new Intent(getActivity(), IntroductionActivity.class);
        startActivity(intent);
    }


    public void onClickLocation() {
        Intent intent = new Intent(getActivity(), LocationActivity.class);
        startActivity(intent);
    }

    public void onClickEvents() {
        Intent intent = new Intent(getActivity(), EventActivity.class);
        startActivity(intent);
    }

    public void onClickRoomPreview() {
        Intent intent = new Intent(getActivity(), RoomPreviewActivity.class);
        startActivity(intent);
    }
}
