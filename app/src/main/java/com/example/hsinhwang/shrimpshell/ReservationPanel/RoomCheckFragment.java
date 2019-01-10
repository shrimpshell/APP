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
import com.example.hsinhwang.shrimpshell.Classes.ShoppingCar;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class RoomCheckFragment extends Fragment {
    private RecyclerView rvCheckReservation;
    private List<ShoppingCar> shoppingCarList = new ArrayList<>();
    //    private HashMap<String, Integer> reservationRoom;
    private String checkInDate, checkOutDate;
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
        checkInDate = bundle.getString("checkInDate");
        checkOutDate = bundle.getString("checkOutDate");
        shoppingCarList = (List<ShoppingCar>) bundle.getSerializable("reservationList");
        Log.d(TAG, checkInDate);
        Log.d(TAG, checkOutDate);
        Log.d(TAG, String.valueOf(shoppingCarList.size()));
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
                            if (Common.networkConnected(getActivity())) {
                                String url = Common.URL + "/ReservationServlet";
                                JsonObject jsonObject = new JsonObject();
                                jsonObject.addProperty("action", "insertReservation");
                                int count = 0;
                                try {
                                    String result = new CommonTask(url, jsonObject.toString()).execute().get();
                                    count = Integer.valueOf(result);
                                } catch (Exception e) {
                                    Log.e(TAG, e.toString());
                                }
                                if (count == 0) {
                                    Common.showToast(getActivity(), R.string.msg_InsertFail);
                                } else {
                                    Common.showToast(getActivity(), R.string.msg_InsertSuccess);
                                }
                            } else {
                                Common.showToast(getActivity(), R.string.msg_NoNetwork);
                            }
                            BookingFragment bookingFragment = new BookingFragment();
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.replace(R.id.content, bookingFragment, "RoomCheckFragment");
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

        rvCheckReservation.setAdapter(new
                ReservationListAdapter(getActivity(), shoppingCarList));
    }


    private class ReservationListAdapter extends
            RecyclerView.Adapter<ReservationListAdapter.MyViewHolder> {
        private Context context;
        private List<ShoppingCar> shoppingCars;

        ReservationListAdapter(Context context, List<ShoppingCar> shoppingCarList) {
            this.context = context;
            this.shoppingCars = shoppingCarList;
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
                tvRoomPrice = itemView.findViewById(R.id.tvRoomPrice);
                btDeletRoom = itemView.findViewById(R.id.btDeletRoom);
                cbAddBed = itemView.findViewById(R.id.cbAddBed);
            }
        }

        @Override
        public int getItemCount() {
            if (shoppingCarList.size() == 0) {
                getFragmentManager().popBackStack("roomChooseFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            return shoppingCars.size();
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
            final ShoppingCar shoppingCar = shoppingCars.get(i);
            Log.d(TAG, shoppingCars.get(i).getRoomTypeName() + shoppingCars.get(i).getCheckInDate() + shoppingCars.get(i).getCheckOutDate() + shoppingCars.get(i).getQuantity() + shoppingCars.get(i).getPrice());
            final int roomQuantity = shoppingCar.getQuantity();
            final String[] total = new String[roomQuantity];
            myViewHolder.tvRoomTypeName.setText(shoppingCar.getRoomTypeName());
            myViewHolder.tvCheckInDate.setText(shoppingCar.getCheckInDate());
            myViewHolder.tvCheckOutDate.setText(shoppingCar.getCheckOutDate());
            myViewHolder.tvRoomPrice.setText(String.valueOf(shoppingCar.getPrice() * shoppingCar.getQuantity()));
            myViewHolder.tvRoomQuantity.setText(String.valueOf(shoppingCar.getQuantity()));
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
                                int price = shoppingCar.getPrice() * Integer.valueOf(total[which]);
                                myViewHolder.tvRoomPrice.setText(String.valueOf(price));
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
                    shoppingCars.remove(shoppingCar);
                    ReservationListAdapter.this.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "請重新選擇房間", Toast.LENGTH_SHORT).show();
                }
            });
            myViewHolder.cbAddBed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int allPrice = shoppingCar.getPrice() * shoppingCar.getQuantity();
                    int quantity = Integer.valueOf((String) myViewHolder.tvRoomQuantity.getText());
                    if (myViewHolder.cbAddBed.isChecked()) {
                        myViewHolder.tvRoomPrice.setText(String.valueOf(allPrice + 1000 * quantity));
                    } else {
                        myViewHolder.tvRoomPrice.setText(String.valueOf(allPrice));
                    }
                }
            });
        }
    }
}
