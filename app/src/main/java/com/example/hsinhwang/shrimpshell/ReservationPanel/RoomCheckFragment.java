package com.example.hsinhwang.shrimpshell.ReservationPanel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsinhwang.shrimpshell.Authentication.LoginActivity;
import com.example.hsinhwang.shrimpshell.BookingFragment;
import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.Reservation;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;


public class RoomCheckFragment extends Fragment {
    private RecyclerView rvCheckReservation;
    private List<Reservation> reservationList = new ArrayList<>();
    //    private HashMap<String, Integer> reservationRoom;
    private String key, checkInDate, checkOutDate;
    private int quantity, price;
    private FloatingActionButton fabRoomCheck;
    private String TAG = "Debug";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_check, container, false);
        handleViews(view);
        return view;
    }

    private void handleViews(View view) {
        Bundle bundle = getArguments();
//        reservationRoom = new HashMap<>();
//        reservationRoom = (HashMap<String, Integer>) bundle.getSerializable("reservationMap");
//        Log.d(TAG, String.valueOf(reservationRoom.size()));
        checkInDate = bundle.getString("checkInDate");
        checkOutDate = bundle.getString("checkOutDate");
        reservationList = (List<Reservation>) bundle.getSerializable("reservationList");
        Log.d(TAG, checkInDate);
        Log.d(TAG, checkOutDate);
        Log.d(TAG, String.valueOf(reservationList.size()));
        fabRoomCheck = view.findViewById(R.id.fabRoomCheck);
        fabRoomCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("確認訂房");
                dialog.setMessage("確定要送出訂單嗎？");
                dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        SharedPreferences preferences = getActivity().getSharedPreferences(Common.LOGIN, MODE_PRIVATE);
                        boolean login = preferences.getBoolean("login", false);
                        if (!login) {
                            Toast.makeText(getActivity(), "請先登入會員", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
//                            if (Common.networkConnected(getActivity())) {
//                                String url = Common.URL + "/ReservationServlet";
//                                Reservation reservation = new Reservation(roomTypeName, checkInDate, checkOutDate, quantity, addBed, price);
//                                if (image != null)
//                                Log.e(TAG, new Gson().toJson(employee));
//                                JsonObject jsonObject = new JsonObject();
//                                jsonObject.addProperty("action", "insert");
//                                jsonObject.addProperty("employee", new Gson().toJson(employee));
//                                jsonObject.addProperty("imageBase64", imageBase64);
//                                int count = 0;
//                                try {
//                                    String result = new CommonTask(url, jsonObject.toString()).execute().get();
//                                    count = Integer.valueOf(result);
//                                } catch (Exception e) {
//                                    Log.e(TAG, e.toString());
//                                }
//                                if (count == 0) {
//                                    Common.showToast(this, R.string.msg_InsertFail);
//                                } else {
//                                    Common.showToast(this, R.string.msg_InsertSuccess);
//                                }
//                            } else {
//                                Common.showToast(this, R.string.msg_NoNetwork);
//                            }
                            BookingFragment bookingFragment=new BookingFragment();
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.replace(R.id.content, bookingFragment, "bookingFragment");
                            transaction.commit();
                            Toast.makeText(getActivity(), "已幫您訂房，期待您的入住！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });

        rvCheckReservation = view.findViewById(R.id.rvCheckReservation);
        rvCheckReservation.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

//        Set<String> setKey = reservationRoom.keySet();
//        Iterator<String> iterator = setKey.iterator();
//        // 從while迴圈中取key
//        while (iterator.hasNext()) {
//            key = iterator.next();
//            quantity = reservationRoom.get(key);
//
//            Log.d(TAG, "房間名稱 " + key + "數量 " + quantity);
//        reservationList.add(new Reservation(key, checkInDate, checkOutDate, quantity, price));
        rvCheckReservation.setAdapter(new
                ReservationListAdapter(getActivity(), reservationList));
    }


    private class ReservationListAdapter extends
            RecyclerView.Adapter<ReservationListAdapter.MyViewHolder> {
        private Context context;
        private List<Reservation> reservationList;

        ReservationListAdapter(Context context, List<Reservation> reservationList) {
            this.context = context;
            this.reservationList = reservationList;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvRoomTypeName, tvCheckInDate, tvCheckOutDate, tvRoomQuantity, tvRoomPrice;
            Button btChangeQuantity, btDeletRoom;
            CheckBox cbAddBed;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvRoomTypeName = itemView.findViewById(R.id.tvRoomTypeName);
                tvCheckInDate = itemView.findViewById(R.id.tvCheckInDate);
                tvCheckOutDate = itemView.findViewById(R.id.tvCheckOutDay);
                tvRoomQuantity = itemView.findViewById(R.id.tvRoomQuantity);
                btChangeQuantity = itemView.findViewById(R.id.btChangeQuantity);
                tvRoomPrice=itemView.findViewById(R.id.tvRoomPrice);
                btDeletRoom = itemView.findViewById(R.id.btDeletRoom);
                cbAddBed=itemView.findViewById(R.id.cbAddBed);
            }
        }

        @Override
        public int getItemCount() {
            if (reservationList.size() == 0) {
                getFragmentManager().popBackStack("roomChooseFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            return reservationList.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(context).
                    inflate(R.layout.item_room_check, viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
            final Reservation reservation = reservationList.get(i);
            Log.d(TAG,reservationList.get(i).getRoomTypeName()+reservationList.get(i).getCheckInDate()+reservationList.get(i).getCheckOutDate()+reservationList.get(i).getQuantity()+reservationList.get(i).getPrice());
            final int roomQuantity = reservation.getQuantity();
            final String[] total = new String[roomQuantity];
            myViewHolder.tvRoomTypeName.setText(reservation.getRoomTypeName());
            myViewHolder.tvCheckInDate.setText(reservation.getCheckInDate());
            myViewHolder.tvCheckOutDate.setText(reservation.getCheckOutDate());
            myViewHolder.tvRoomPrice.setText(String.valueOf(reservation.getPrice()));
            myViewHolder.tvRoomQuantity.setText(String.valueOf(reservation.getQuantity()));
            myViewHolder.btChangeQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (roomQuantity <= 1) {
                        Toast.makeText(getActivity(), "房間的數量不能少於一間喔！", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < roomQuantity; i++) {
                            total[i] = String.valueOf(i + 1);
                            Log.d(TAG, "數量 " + total[i]);
                        }
                        final AlertDialog.Builder dialog_list = new AlertDialog.Builder(getActivity());
                        dialog_list.setTitle("房間數量");
                        dialog_list.setItems(total, new DialogInterface.OnClickListener() {
                            //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myViewHolder.tvRoomQuantity.setText(total[which]);
                                Toast.makeText(getActivity(), "已更改數量", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog_list.show();
                    }
                }
            });
            myViewHolder.btDeletRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reservationList.remove(reservation);
                    ReservationListAdapter.this.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "請重新選擇房間", Toast.LENGTH_SHORT).show();
                }
            });
            myViewHolder.cbAddBed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myViewHolder.tvRoomPrice.setText(String.valueOf(reservation.getPrice()+1000));
                }
            });
        }
    }
}
