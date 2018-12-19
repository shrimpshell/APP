package com.example.hsinhwang.shrimpshell.ReservationPanel;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.Events;
import com.example.hsinhwang.shrimpshell.Classes.Reservation;
import com.example.hsinhwang.shrimpshell.Classes.RoomType;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RoomChooseFragment extends Fragment {
    //    private HashMap<String, Integer> reservationMap;
    private RecyclerView rvRoomChoose;
    private List<RoomType> roomTypeList, roomTypeReservationList;
    private List<Reservation> reservationList = new ArrayList<>();
    List<Events> events;
    private CommonTask eventGetAllTask;
    private Button btRoomCheck;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private int id, price, roomQuantity, adultQuantity, childQuantity;
    private String name, roomSize, bed;
    private float discount;
    private String checkInDate, checkOutDate;
    private String TAG = "Debug";
    private CommonTask roomTypeGetAllTask;
    public static String URL = "http://10.0.2.2:8080/ShellService";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_choose, container, false);
        handleViews(view);
        return view;
    }

    // check if the device connect to the network
    public static boolean networkConnected(Activity activity) {
        ConnectivityManager conManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager != null ? conManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void showToast(Context context, int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void handleViews(View view) {
        //取得上一頁bundle過來的資料
        final Bundle bundle = getArguments();
        if (bundle != null) {
            checkInDate = bundle.getString("checkInDate");
            checkOutDate = bundle.getString("checkOutDate");
            Log.d(TAG, "checkInDate " + checkInDate);
            Log.d(TAG, "checkOutDate " + checkOutDate);
        }
        rvRoomChoose = view.findViewById(R.id.rvRoomChoose);
        //設定recyclerview的排列方式為水平排列
        rvRoomChoose.setLayoutManager(
                new LinearLayoutManager(getActivity(),
                        GridLayoutManager.HORIZONTAL, false));

        //連上DB抓取房型資料
        if (networkConnected(getActivity())) {
            String roomTypeUrl = URL + "/RoomTypeServlet";
            roomTypeList.clear();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getAll");
            String jsonOut = jsonObject.toString();
            roomTypeGetAllTask = new CommonTask(roomTypeUrl, jsonOut);
            try {
                String jsonIn = roomTypeGetAllTask.execute().get();
                Type listType = new TypeToken<List<RoomType>>() {
                }.getType();
                roomTypeList = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (roomTypeList.isEmpty()) {
                showToast(getActivity(), "房間載入失敗");
            } else {
                for (RoomType rt : roomTypeList) {
                    Log.d(TAG, "RoomType " + rt.getId());
                    Log.d(TAG, "RoomType " + rt.getName());
                    Log.d(TAG, "RoomType " + rt.getRoomSize());
                    Log.d(TAG, "RoomType " + rt.getBed());
                    Log.d(TAG, "RoomType " + rt.getAdultQuantity());
                    Log.d(TAG, "RoomType " + rt.getRoomQuantity());
                    Log.d(TAG, "RoomType " + rt.getPrice());
                }

                getDiscount();

                if (networkConnected(getActivity())) {
                    String roomTypeReservationUrl = URL + "/RoomTypeServlet";
                    roomTypeReservationList = null;
                    JsonObject reservationJsonObject = new JsonObject();
                    reservationJsonObject.addProperty("action", "getReservation");
                    reservationJsonObject.addProperty("checkInDate", checkInDate);
                    reservationJsonObject.addProperty("checkOutDate", checkOutDate);
                    String roomTypeReservationJsonOut = reservationJsonObject.toString();
                    roomTypeGetAllTask = new CommonTask(roomTypeReservationUrl, roomTypeReservationJsonOut);
                    try {
                        String jsonIn = roomTypeGetAllTask.execute().get();
                        Type roomTypeReservationListType = new TypeToken<List<RoomType>>() {
                        }.getType();
                        roomTypeReservationList = new Gson().fromJson(jsonIn, roomTypeReservationListType);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                    if (roomTypeReservationList.isEmpty()) {
                        Log.d(TAG, "roomTypeReservationList: " + String.valueOf(roomTypeReservationList));
                        roomTypeReservationList = roomTypeList;
                    } else {
                        for (RoomType rt : roomTypeList) {
                            if (rt.getId() > 0) {
                                id = rt.getId();
                                name = rt.getName();
                                roomSize = rt.getRoomSize();
                                bed = rt.getBed();
                                adultQuantity = rt.getAdultQuantity();
                                childQuantity = rt.getChildQuantity();
                                roomQuantity = rt.getRoomQuantity();
                                price = rt.getPrice();
                                for (RoomType rtrn : roomTypeReservationList) {
                                    if (id == rtrn.getId()) {
                                        roomQuantity -= rtrn.getRoomQuantity();
                                        Log.d(TAG, "roomQuantity: " + String.valueOf(roomQuantity));
                                        rt.setRoomQuantity(roomQuantity);
                                        if (roomQuantity == 0) {
                                            roomTypeList.remove(rt);
                                        }
                                    }
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                } else {
                    showToast(getActivity(), R.string.msg_NoNetwork);
                }
                rvRoomChoose.setAdapter(new RoomTypeAdapter(getActivity(), roomTypeList));
            }
        } else {
            showToast(getActivity(), R.string.msg_NoNetwork);
        }


        /* 不處理捲動事件所以監聽器設為null */
        rvRoomChoose.setOnFlingListener(null);
        /* 如果希望一次滑動一頁資料，要加上PagerSnapHelper物件 */
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rvRoomChoose);
//        reservationMap = new HashMap<>();
        btRoomCheck = view.findViewById(R.id.btRoomCheck);
        //按下按鈕帶選取的房間到下一頁
        btRoomCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomCheckFragment roomCheckFragment = new RoomCheckFragment();

                //判斷是否有抓到房間，有的話就bundle到指定頁面
                Log.d(TAG, String.valueOf(reservationList.size()));
                if (reservationList.isEmpty()) {
                    showToast(getActivity(), "您尚未選取房間！");
                } else {
                    Log.d(TAG, "reservationList.size: " + String.valueOf(reservationList.size()));
                    bundle.putSerializable("reservationList", (Serializable) reservationList);
                    roomCheckFragment.setArguments(bundle);
                    manager = getActivity().getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.content, roomCheckFragment, "roomChooseFragment");
                    transaction.addToBackStack("roomChooseFragment");
                    transaction.commit();
                }
            }
        });
    }

    public float getDiscount() {
        if (networkConnected(getActivity())) {
            String eventurl = URL + "/EventServlet";
            JsonObject eventJsonObject = new JsonObject();
            eventJsonObject.addProperty("action", "getDiscount");
            eventJsonObject.addProperty("firstday", checkInDate);
            String jsonOut = eventJsonObject.toString();
            eventGetAllTask = new CommonTask(eventurl, jsonOut);
            try {
                String jsonIn = eventGetAllTask.execute().get();
                Type listType = new TypeToken<List<Events>>() {
                }.getType();
                events = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        Log.d(TAG, "discount " + String.valueOf(events.get(0).getDiscount()));
        if (events.get(0).getDiscount() == 0.0) {
            discount = 1;
        } else {
            discount = events.get(0).getDiscount();
        }
        Log.d(TAG, "disconu " + discount);
        return discount;
    }

    private class RoomTypeAdapter extends
            RecyclerView.Adapter<RoomTypeAdapter.MyViewHolder> {
        private Context context;
        private List<RoomType> roomTypeList;
        private int eventPrice;
        private Float dis;

        RoomTypeAdapter(Context context, List<RoomType> roomTypeList) {
            this.context = context;
            this.roomTypeList = roomTypeList;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivRoomType;
            TextView tvRoomTypeName, tvRoomTypeSize, tvRoomTypeBed, tvRoomTypeAdult,
                    tvRoomTypeLastQuantity, tvRoomTypePrice, tvRoomTypeEvent;
            ImageButton ibAddRoom;
            CardView cvRoomTypeChoose;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                ivRoomType = itemView.findViewById(R.id.ivRoomType);
                tvRoomTypeName = itemView.findViewById(R.id.tvRoomTypeName);
                tvRoomTypeSize = itemView.findViewById(R.id.tvRoomTypeSize);
                tvRoomTypeBed = itemView.findViewById(R.id.tvRoomTypeBed);
                tvRoomTypeAdult = itemView.findViewById(R.id.tvRoomTypePeopleAdult);
                tvRoomTypeLastQuantity = itemView.findViewById(R.id.tvRoomTypeLastQuantity);
                tvRoomTypePrice = itemView.findViewById(R.id.tvRoomTypePrice);
                tvRoomTypeEvent = itemView.findViewById(R.id.tvRoomTypeEvent);
                cvRoomTypeChoose = itemView.findViewById(R.id.cvRoomTypeChoose);
                ibAddRoom = itemView.findViewById(R.id.ibAddRoom);
            }
        }

        @Override
        public int getItemCount() {
            return roomTypeList.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(context).
                    inflate(R.layout.item_room_type, viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
            final RoomType roomType = roomTypeList.get(i);
            int price, quantity, lastQuantity;
            Log.d(TAG, "id" + String.valueOf(roomType.getId()));
            reservationList.clear();
            myViewHolder.ivRoomType.setImageResource(R.drawable.pic_roomtype_2seaview);
            myViewHolder.tvRoomTypeName.setText(roomType.getName());
            myViewHolder.tvRoomTypeSize.setText(roomType.getRoomSize());
            myViewHolder.tvRoomTypeBed.setText(roomType.getBed());
            myViewHolder.tvRoomTypeAdult.setText(String.valueOf(roomType.getAdultQuantity()));
            myViewHolder.tvRoomTypeLastQuantity.setText(String.valueOf(roomType.getRoomQuantity()));
            myViewHolder.tvRoomTypeEvent.setText(R.string.roomTypeEventString);
            if (discount == 1.0) {
                myViewHolder.tvRoomTypeEvent.setVisibility(View.GONE);
                myViewHolder.tvRoomTypePrice.setText(String.valueOf(roomType.getPrice()));
            } else {
                int id = roomType.getId();
                Log.d(TAG, "id " + String.valueOf(id));
                price = roomType.getPrice();
                dis = new Float((float) price * discount);
                eventPrice = dis.intValue();
                Log.d(TAG, "eventPrice " + String.valueOf(eventPrice));
                myViewHolder.tvRoomTypePrice.setText(String.valueOf(eventPrice));
                myViewHolder.tvRoomTypeEvent.setVisibility(View.VISIBLE);
                myViewHolder.tvRoomTypeEvent.setText(R.string.roomTypeEventString);
            }
            //按下按鈕選取要訂的房間
            myViewHolder.ibAddRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Reservation reservation;
                    int roomQuantity = Integer.valueOf(myViewHolder.tvRoomTypeLastQuantity.getText().toString());
                    Log.d(TAG, "roomQuantity " + String.valueOf(roomQuantity));
                    int index = getindex(myViewHolder.tvRoomTypeName.getText().toString(), reservationList);
                    Log.d(TAG, "index " + String.valueOf(index));
                    //如果還有房間就可以加入訂單
                    if (roomQuantity > 0) {
                        if (discount == 1.0) {
                            roomQuantity = roomQuantity - 1;
                            myViewHolder.tvRoomTypeLastQuantity.setText(String.valueOf(roomQuantity));
                            Log.d(TAG, roomType.getName() + roomType.getRoomSize() + roomType.getBed() + roomType.getAdultQuantity() + roomType.getRoomQuantity() + roomType.getPrice());
                            if (index == -1) {
                                reservation = new Reservation(roomType.getId(), myViewHolder.tvRoomTypeName.getText().toString(),
                                        checkInDate, checkOutDate, 1, roomType.getPrice());
                                reservationList.add(reservation);
                            } else {
                                Log.d(TAG, "index " + String.valueOf(index));
                                reservation = reservationList.get(index);
                                reservation.setQuantity(reservation.getQuantity() + 1);
                                Log.d(TAG, "quantity " + String.valueOf(reservation.getQuantity()));
                                reservationList.set(index, reservation);
                                Log.d(TAG, "reservationList quantity " + String.valueOf(reservationList.get(index).getQuantity() + "reservationList price " + reservationList.get(index).getPrice()));
                            }
                        } else {
                            roomQuantity = roomQuantity - 1;
                            myViewHolder.tvRoomTypeLastQuantity.setText(String.valueOf(roomQuantity));
                            Log.d(TAG, "roomType " + roomType.getName() + roomType.getRoomSize() + roomType.getBed() + roomType.getAdultQuantity() + roomType.getRoomQuantity() + eventPrice);
                            if (index == -1) {
                                reservation = new Reservation(roomType.getId(), myViewHolder.tvRoomTypeName.getText().toString(),
                                        checkInDate, checkOutDate, 1, eventPrice);
                                reservationList.add(reservation);
                            } else {
                                Log.d(TAG, "index " + String.valueOf(index));
                                reservation = reservationList.get(index);
                                reservation.setQuantity(reservation.getQuantity() + 1);
                                Log.d(TAG, String.valueOf(reservation.getQuantity()));
                                reservationList.set(index, reservation);
                                Log.d(TAG, "quantity " + String.valueOf(reservationList.get(index).getQuantity()) + "price " + String.valueOf(reservationList.get(index).getPrice()));
                            }
                        }
                        showToast(getActivity(), "已將房間加入訂單");
                    } else {
                        showToast(getActivity(), "您選的房間已被訂完");
                    }
                }
            });
        }

        private int getindex(String roomTypeName, List<Reservation> reservationList) {
            for (Reservation reservation : reservationList) {
                if (roomTypeName.equals(reservation.getRoomTypeName())) {
                    return reservationList.indexOf(reservation);
                }
            }
            return -1;
        }
    }
}




