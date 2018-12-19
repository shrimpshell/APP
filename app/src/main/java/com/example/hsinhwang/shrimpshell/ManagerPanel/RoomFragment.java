package com.example.hsinhwang.shrimpshell.ManagerPanel;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsinhwang.shrimpshell.Classes.RoomType;
import com.example.hsinhwang.shrimpshell.Classes.Rooms;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class RoomFragment extends Fragment {
    private final static String TAG = "RoomFragment";
    private SwipeRefreshLayout swiperefreshlayout;
    private RecyclerView roomFragmentRecyclerView;
    private FloatingActionButton toAddRoom;
    private CommonTask roomGetAllTask, roomDeleteTask;
    private FragmentActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        showAllRooms();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        roomFragmentRecyclerView = view.findViewById(R.id.roomFragmentRecyclerView);
        swiperefreshlayout = view.findViewById(R.id.swiperefreshlayout);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefreshlayout.setRefreshing(true);
                showAllRooms();
                swiperefreshlayout.setRefreshing(false);
            }
        });

        roomFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

        toAddRoom = view.findViewById(R.id.toAddRoom);
        toAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, AddRoomActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
        LayoutInflater inflater;
        List<RoomType> roomList;
        TextView roomName, roomDetail, itemId;
        RelativeLayout roomItem;

        public RoomAdapter(Context context, List<RoomType> roomList) {
            this.inflater = LayoutInflater.from(context);
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
            final RoomType room = roomList.get(i);
            itemId.setText(String.valueOf(room.getId()));
            roomName.setText(room.getName());
            roomDetail.setText(room.getDetail());
            roomItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, ManagerEditActivity.class);
                    Bundle bundle = new Bundle();
                    RoomType innerRoom = new RoomType(room.getId(), room.getName(), room.getRoomSize(), room.getBed(), room.getAdultQuantity(), room.getChildQuantity(), room.getRoomQuantity(), room.getPrice());
                    bundle.putSerializable("room", innerRoom);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            roomItem.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(activity, view, Gravity.END);
                    popupMenu.inflate(R.menu.popup_menu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.item_remove:
                                    if (Common.networkConnected(activity)) {
                                        String url = Common.URL + "/RoomTypeServlet";
                                        JsonObject jsonObject = new JsonObject();
                                        jsonObject.addProperty("action", "roomRemove");
                                        jsonObject.addProperty("room", new Gson().toJson(room));
                                        int count = 0;
                                        try {
                                            roomDeleteTask = new CommonTask(url, jsonObject.toString());
                                            String result = roomDeleteTask.execute().get();
                                            count = Integer.valueOf(result);
                                        } catch (Exception e) {
                                            Log.e(TAG, e.toString());
                                        }
                                        if (count == 0) {
                                            Common.showToast(activity, R.string.msg_DeleteFail);
                                        } else {
                                            roomList.remove(room);
                                            swiperefreshlayout.setRefreshing(true);
                                            roomFragmentRecyclerView.setAdapter(null);
                                            showAllRooms();
                                            swiperefreshlayout.setRefreshing(false);
                                            Common.showToast(activity, R.string.msg_DeleteSuccess);
                                        }
                                    } else {
                                        Common.showToast(activity, R.string.msg_NoNetwork);
                                    }
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                    return true;
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
                itemId = itemView.findViewById(R.id.itemId);
                roomName = itemView.findViewById(R.id.itemName);
                roomDetail = itemView.findViewById(R.id.itemDetail);
                roomItem = itemView.findViewById(R.id.itemLayout);
            }
        }
    }

    private void showAllRooms() {
        if (Common.networkConnected(activity)) {
            String url = Common.URL + "/RoomTypeServlet";
            List<RoomType> rooms = null;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getAll");
            String jsonOut = jsonObject.toString();
            roomGetAllTask = new CommonTask(url, jsonOut);
            try {
                String jsonIn = roomGetAllTask.execute().get();
                Type listType = new TypeToken<List<RoomType>>() {
                }.getType();
                rooms = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (rooms == null || rooms.isEmpty()) {
                Common.showToast(activity, R.string.msg_NoRoomsFound);
            } else {
                roomFragmentRecyclerView.setAdapter(new RoomAdapter(activity, rooms));
            }
        } else {
            Common.showToast(activity, R.string.msg_NoNetwork);
        }
    }
}
