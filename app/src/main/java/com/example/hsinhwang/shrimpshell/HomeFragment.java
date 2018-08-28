package com.example.hsinhwang.shrimpshell;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private RecyclerView mainRecyclerView, eventRecyclerView, roomRecyclerView;
    private boolean isLoggedIn = false;
    private GoogleMap map;
    private Marker hotel_mark;
    private LatLng hotel_latlng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initialization();
        List<MainOptions> optionList = new ArrayList<>();
        if (isLoggedIn) {
        } else {
            optionList.add(new MainOptions(R.string.login_title, "會員登入", R.drawable.login));
        }
        optionList.add(new MainOptions(R.string.intro_title, "飯店介紹", R.drawable.introduction));
        mainRecyclerView = view.findViewById(R.id.mainRecyclerView);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainRecyclerView.setAdapter(new MainAdapter(inflater, optionList));
        List<Events> actualEventList = new ArrayList<>();
        actualEventList.add(new Events(R.drawable.ss, "event1", "this is item_event 1", Date.valueOf("2018-09-01"), Date.valueOf("2018-09-02")));
        actualEventList.add(new Events(R.drawable.ss, "event2", "this is item_event 2", Date.valueOf("2018-09-03"), Date.valueOf("2018-09-04")));
        actualEventList.add(new Events(R.drawable.ss, "event3", "this is item_event 3", Date.valueOf("2018-09-05"), Date.valueOf("2018-09-06")));
        actualEventList.add(new Events(R.drawable.ss, "event4", "this is item_event 4", Date.valueOf("2018-09-07"), Date.valueOf("2018-09-08")));
        actualEventList.add(new Events(R.drawable.ss, "event5", "this is item_event 5", Date.valueOf("2018-09-09"), Date.valueOf("2018-09-10")));
        eventRecyclerView = view.findViewById(R.id.eventRecyclerView);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        eventRecyclerView.setAdapter(new EventAdapter(inflater, actualEventList));
        List<Rooms> topFiveRooms = new ArrayList<>();
        topFiveRooms.add(new Rooms(R.drawable.ss, 1, "room1", "this is room 1"));
        topFiveRooms.add(new Rooms(R.drawable.ss, 2, "room2", "this is room 2"));
        topFiveRooms.add(new Rooms(R.drawable.ss, 3, "room3", "this is room 3"));
        topFiveRooms.add(new Rooms(R.drawable.ss, 4, "room4", "this is room 4"));
        topFiveRooms.add(new Rooms(R.drawable.ss, 5, "room5", "this is room 5"));
        roomRecyclerView = view.findViewById(R.id.roomRecyclerView);
        roomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        roomRecyclerView.setAdapter(new RoomAdapter(inflater, topFiveRooms));
        return view;
    }

    private void initialization() {
        initPoints();
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        initPoints();
        setUpMap();
        map.getUiSettings().setAllGesturesEnabled(false);
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
                        case R.string.login_title:
                            onClickLogin();
                            break;
                        case R.string.intro_title:
                            onClickIntro();
                            break;
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

    private class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
        LayoutInflater inflater;
        List<Events> eventList;
        ImageView eventImageView;
        TextView eventTextView;
        RelativeLayout eventItem;

        public EventAdapter(LayoutInflater inflater, List<Events> eventList) {
            this.inflater = inflater;
            this.eventList = eventList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = inflater.inflate(R.layout.item_event, viewGroup, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final Events event = eventList.get(i);
            eventImageView.setImageResource(event.getImageId());
            eventTextView.setText(event.getName());
            eventItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), event.getName(), Toast.LENGTH_SHORT).show();
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
                eventImageView = itemView.findViewById(R.id.eventImageView);
                eventTextView = itemView.findViewById(R.id.eventTextView);
                eventItem = itemView.findViewById(R.id.eventItem);
            }
        }
    }

    private class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
        LayoutInflater inflater;
        List<Rooms> roomList;
        ImageView roomImageView;
        TextView roomTextView;
        RelativeLayout roomItem;

        public RoomAdapter(LayoutInflater inflater, List<Rooms> roomList) {
            this.inflater = inflater;
            this.roomList = roomList;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = inflater.inflate(R.layout.item_event, viewGroup, false);
            return new RoomAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final Rooms room = roomList.get(i);
            roomImageView.setImageResource(room.getRoomImageId());
            roomTextView.setText(room.getRoomName());
            roomItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), room.getRoomName(), Toast.LENGTH_SHORT).show();
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
                roomImageView = itemView.findViewById(R.id.eventImageView);
                roomTextView = itemView.findViewById(R.id.eventTextView);
                roomItem = itemView.findViewById(R.id.eventItem);
            }
        }
    }

    public void onClickLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


    public void onClickIntro() {
        Intent intent = new Intent(getActivity(), IntroductionActivity.class);
        startActivity(intent);
    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }
        addMarkers();
        moveMap();
    }

    private void initPoints() {
        hotel_latlng = new LatLng(24.9674601, 121.1918223);
    }

    private void moveMap() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(hotel_latlng)
                .zoom(18)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);
    }

    private void addMarkers() {
        hotel_mark = map.addMarker(new MarkerOptions().position(hotel_latlng));
    }
}
