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

import com.example.hsinhwang.shrimpshell.Classes.Rooms;
import com.example.hsinhwang.shrimpshell.R;

import java.util.ArrayList;
import java.util.List;

public class RoomFragment extends Fragment {
    private RecyclerView roomFragmentRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        roomFragmentRecyclerView = view.findViewById(R.id.roomFragmentRecyclerView);
        List<Rooms> topFiveRooms = new ArrayList<>();
        topFiveRooms.add(new Rooms(R.drawable.ss, 1, "room1", "this is room 1"));
        topFiveRooms.add(new Rooms(R.drawable.ss, 2, "room2", "this is room 2"));
        topFiveRooms.add(new Rooms(R.drawable.ss, 3, "room3", "this is room 3"));
        topFiveRooms.add(new Rooms(R.drawable.ss, 4, "room4", "this is room 4"));
        topFiveRooms.add(new Rooms(R.drawable.ss, 5, "room5", "this is room 5"));
        roomFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        roomFragmentRecyclerView.setAdapter(new RoomAdapter(inflater, topFiveRooms));
        return view;
    }

    private class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
        LayoutInflater inflater;
        List<Rooms> roomList;
        TextView roomName, roomDetail;
        RelativeLayout roomItem;

        public RoomAdapter(LayoutInflater inflater, List<Rooms> roomList) {
            this.inflater = inflater;
            this.roomList = roomList;
        }
        @NonNull
        @Override
        public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = inflater.inflate(R.layout.item_event_room, viewGroup, false);
            return new RoomAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RoomAdapter.ViewHolder viewHolder, int i) {
            final Rooms room = roomList.get(i);
            roomName.setText(room.getRoomName());
            roomDetail.setText(room.getRoomiDetail());
            roomItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ManagerEditActivity.class);
                    Bundle bundle = new Bundle();
                    Rooms innerRoom = new Rooms(room.getRoomImageId(), room.getRoomId(), room.getRoomName(), room.getRoomiDetail());
                    bundle.putSerializable("room", innerRoom);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return roomList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                roomName = itemView.findViewById(R.id.itemName);
                roomDetail = itemView.findViewById(R.id.itemDetail);
                roomItem = itemView.findViewById(R.id.itemLayout);
            }
        }
    }

}
