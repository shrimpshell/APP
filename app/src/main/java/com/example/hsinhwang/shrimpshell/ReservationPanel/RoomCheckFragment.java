package com.example.hsinhwang.shrimpshell.ReservationPanel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.Reservation;
import com.example.hsinhwang.shrimpshell.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class RoomCheckFragment extends Fragment {
    private RecyclerView rvCheckReservation;
    private List<Reservation> reservationList;
    private HashMap<String, Integer> reservationRoom;
    private String key, checkInDate, checkOutDate, quantity;
    private Button btSentReservation;
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
        reservationRoom = new HashMap<>();
        reservationRoom = (HashMap<String, Integer>) bundle.getSerializable("reservationMap");
        Log.d(TAG, String.valueOf(reservationRoom.size()));
        checkInDate = bundle.getString("checkInDate");
        checkOutDate = bundle.getString("checkOutDate");
        Log.d(TAG, checkInDate);
        Log.d(TAG, checkOutDate);
        btSentReservation = view.findViewById(R.id.btSentReservation);
        rvCheckReservation = view.findViewById(R.id.rvCheckReservation);
        rvCheckReservation.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        reservationList = new ArrayList<>();

        Set<String> setKey = reservationRoom.keySet();
        Iterator<String> iterator = setKey.iterator();
        // 從while迴圈中取key
        while (iterator.hasNext()) {
            key = iterator.next();
            quantity = String.valueOf(reservationRoom.get(key));
            Log.d(TAG, key + quantity);
            reservationList.add(new Reservation(key, checkInDate, checkOutDate, quantity));
        }
        rvCheckReservation.setAdapter(new ReservationListAdapter(getActivity(), reservationList));
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
            TextView tvRoomTypeName, tvCheckInDate, tvCheckOutDate, tvRoomQuantity;
            Button btChangeQuantity, btDeletRoom;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvRoomTypeName = itemView.findViewById(R.id.tvRoomTypeName);
                tvCheckInDate = itemView.findViewById(R.id.tvCheckInDate);
                tvCheckOutDate = itemView.findViewById(R.id.tvCheckOutDay);
                tvRoomQuantity = itemView.findViewById(R.id.tvRoomQuantity);
                btChangeQuantity = itemView.findViewById(R.id.btChangeQuantity);
                btDeletRoom = itemView.findViewById(R.id.btDeletRoom);
            }
        }

        @Override
        public int getItemCount() {
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
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            final Reservation reservation = reservationList.get(i);
            myViewHolder.tvRoomTypeName.setText(reservation.getRoomTypeName());
            myViewHolder.tvCheckInDate.setText(reservation.getCheckInDate());
            myViewHolder.tvCheckOutDate.setText(reservation.getCheckOutDate());
            myViewHolder.tvRoomQuantity.setText(reservation.getQuantity());
            myViewHolder.btChangeQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
