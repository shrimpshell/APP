package com.example.hsinhwang.shrimpshell;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Authentication.LoginActivity;
import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.Events;
import com.example.hsinhwang.shrimpshell.Classes.ImageTask;
import com.example.hsinhwang.shrimpshell.Classes.MainOptions;
import com.example.hsinhwang.shrimpshell.Classes.RoomType;
import com.example.hsinhwang.shrimpshell.GeneralPages.AllRatingActivity;
import com.example.hsinhwang.shrimpshell.GeneralPages.EventActivity;
import com.example.hsinhwang.shrimpshell.GeneralPages.IntroductionActivity;
import com.example.hsinhwang.shrimpshell.GeneralPages.RoomDetailActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private final static String TAG = "HomeFragment";
    private RecyclerView mainRecyclerView, eventRecyclerView, roomRecyclerView;
    private GoogleMap map;
    private Marker hotel_mark;
    private LatLng hotel_latlng;
    private FragmentActivity activity;
    private CommonTask roomGetAllTask, eventGetAllTask;
    private SharedPreferences pref;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        showAllRooms();
        showAllEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initialization();
        SharedPreferences pref = getActivity().getSharedPreferences(Common.LOGIN,
                MODE_PRIVATE);
        List<MainOptions> optionList = new ArrayList<>();
        SharedPreferences customer_pref = getActivity().getSharedPreferences(Common.LOGIN, MODE_PRIVATE);
        SharedPreferences employee_pref = getActivity().getSharedPreferences(Common.EMPLOYEE_LOGIN, MODE_PRIVATE);
        boolean customer_login = customer_pref.getBoolean("login", false);
        boolean employee_login = employee_pref.getBoolean("login", false);
        if (!customer_login && !employee_login) {
            optionList.add(new MainOptions(R.string.login_title, (String)getText(R.string.login), R.drawable.login));
        }
        optionList.add(new MainOptions(R.string.intro_title, (String)getText(R.string.about), R.drawable.introduction));
        optionList.add(new MainOptions(R.string.all_rating, (String)getText(R.string.rating), R.drawable.view_rating));
        mainRecyclerView = view.findViewById(R.id.mainRecyclerView);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainRecyclerView.setAdapter(new MainAdapter(inflater, optionList));

        eventRecyclerView = view.findViewById(R.id.eventRecyclerView);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        roomRecyclerView = view.findViewById(R.id.roomRecyclerView);
        roomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

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
                        case R.string.all_rating:
                            onClickRating();

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

        public EventAdapter(Context context, List<Events> eventList) {
            this.inflater = LayoutInflater.from(context);
            this.eventList = eventList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = inflater.inflate(R.layout.item_one_event, viewGroup, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final Events event = eventList.get(i);
            String url = Common.URL + "/EventServlet";
            int imageSize = getResources().getDisplayMetrics().widthPixels / 3;
            Bitmap bitmap = null;

            try {
                bitmap = new ImageTask(url, event.getEventId(), imageSize).execute().get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (bitmap != null) {
                eventImageView.setImageBitmap(bitmap);
            } else {
                eventImageView.setImageResource(R.drawable.events);
            }

            eventTextView.setText(event.getName());
            eventItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), EventActivity.class);
                    Bundle bundle = new Bundle();
                    Events innerEvent = new Events(event.getEventId(), event.getName(), event.getDescription(), event.getStart(), event.getEnd(), event.getDiscount());
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
                eventImageView = itemView.findViewById(R.id.eventImageView);
                eventTextView = itemView.findViewById(R.id.eventTextView);
                eventItem = itemView.findViewById(R.id.eventItem);
            }
        }
    }

    private class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
        LayoutInflater inflater;
        List<RoomType> roomList;
        ImageView roomImageView;
        TextView roomTextView;
        RelativeLayout roomItem;

        public RoomAdapter(Context context, List<RoomType> roomList) {
            this.inflater = LayoutInflater.from(context);
            this.roomList = roomList;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = inflater.inflate(R.layout.item_one_event, viewGroup, false);
            return new RoomAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final RoomType room = roomList.get(i);
            String url = Common.URL + "/RoomTypeServlet";
            int imageSize = getResources().getDisplayMetrics().widthPixels / 3;
            Bitmap bitmap = null;

            try {
                bitmap = new ImageTask(url, room.getId(), imageSize).execute().get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (bitmap != null) {
                roomImageView.setImageBitmap(bitmap);
            } else {
                roomImageView.setImageResource(R.drawable.room_review);
            }
            roomTextView.setText(room.getName());
            roomItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), RoomDetailActivity.class);
                    Bundle bundle = new Bundle();
                    RoomType innerRoom = new RoomType(room.getId(), room.getName(), room.getRoomSize(), room.getBed(), room.getAdultQuantity(), room.getChildQuantity(), room.getRoomQuantity(), room.getPrice());
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
                roomImageView = itemView.findViewById(R.id.eventImageView);
                roomTextView = itemView.findViewById(R.id.eventTextView);
                roomItem = itemView.findViewById(R.id.eventItem);
            }
        }
    }

    //轉頁在這裡
    public void onClickLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    public void onClickIntro() {
        Intent intent = new Intent(getActivity(), IntroductionActivity.class);
        startActivity(intent);
    }

    public void onClickRating(){
        Intent intent = new Intent(getActivity(), AllRatingActivity.class);
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

    private void showAllRooms() {
        if (Common.networkConnected(activity)) {
            String url = Common.URL + "/RoomTypeServlet";
            List<RoomType> rooms = null;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getFive");
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
                roomRecyclerView.setAdapter(new HomeFragment.RoomAdapter(activity, rooms));
            }
        } else {
            Common.showToast(activity, R.string.msg_NoNetwork);
        }
    }

    private void showAllEvents() {
        if (Common.networkConnected(activity)) {
            String url = Common.URL + "/EventServlet";
            List<Events> events = null;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getFive");
            String jsonOut = jsonObject.toString();
            eventGetAllTask = new CommonTask(url, jsonOut);
            try {
                String jsonIn = eventGetAllTask.execute().get();
                Type listType = new TypeToken<List<Events>>() {
                }.getType();
                events = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
//                Log.e(TAG, e.toString());
            }
            if (events == null || events.isEmpty()) {
                Common.showToast(activity, R.string.msg_NoEventsFound);
            } else {
                eventRecyclerView.setAdapter(new HomeFragment.EventAdapter(activity, events));
            }
        } else {
            Common.showToast(activity, R.string.msg_NoNetwork);
        }
    }
}
