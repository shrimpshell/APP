package com.example.hsinhwang.shrimpshell.ReservationPanel;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
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

import com.example.hsinhwang.shrimpshell.Classes.RoomType;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomChooseFragment extends Fragment {
    private HashMap<String, Integer> reservationMap;
    private RecyclerView rvRoomChoose;
    private List<RoomType> roomTypeList;
    private Button btRoomCheck;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private int adultQuantity, childQuantity;
    private String checkInDate, checkOutDate;
    private String TAG = "Debug";
    private ReservationPanelTask roomTypeGetAllTask;
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
        final Bundle bundle = getArguments();
        if (bundle != null) {
            adultQuantity = bundle.getInt("AdultQuantity");
            childQuantity = bundle.getInt("ChildQuantity");
            checkInDate = bundle.getString("checkInDate");
            checkOutDate = bundle.getString("checkOutDate");
            Log.d(TAG, String.valueOf(adultQuantity));
            Log.d(TAG, String.valueOf(childQuantity));
        }
        rvRoomChoose = view.findViewById(R.id.rvRoomChoose);
        rvRoomChoose.setLayoutManager(
                new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.HORIZONTAL, false));

//        if (networkConnected(getActivity())) {
//            String url = URL + "/RoomTypeServlet";
//            roomTypeList = null;
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("action", "getAll");
//            String jsonOut = jsonObject.toString();
//            roomTypeGetAllTask = new ReservationPanelTask(url, jsonOut);
//            try {
//                String jsonIn = roomTypeGetAllTask.execute().get();
//                Type listType = new TypeToken<List<RoomType>>() {
//                }.getType();
//                roomTypeList = new Gson().fromJson(jsonIn, listType);
//            } catch (Exception e) {
//                Log.e(TAG, e.toString());
//            }
//            if (roomTypeList == null || roomTypeList.isEmpty()) {
//                showToast(getActivity(),"沒有找到房間");
//            } else {
//                rvRoomChoose.setAdapter(new RoomTypeAdapter(getActivity(), roomTypeList));
//            }
//        } else {
//            showToast(getActivity(), R.string.msg_NoNetwork);
//        }
        roomTypeList = getRoomTypeList();
        rvRoomChoose.setAdapter(new RoomTypeAdapter(getActivity(), roomTypeList));
        /* 不處理捲動事件所以監聽器設為null */
        rvRoomChoose.setOnFlingListener(null);
        /* 如果希望一次滑動一頁資料，要加上PagerSnapHelper物件 */
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rvRoomChoose);
        reservationMap = new HashMap<>();
        btRoomCheck = view.findViewById(R.id.btRoomCheck);
        btRoomCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomCheckFragment roomCheckFragment = new RoomCheckFragment();

                if (reservationMap.size() == 0) {
                    showToast(getActivity(),"您尚未選取房間！");
                } else {
                    bundle.putString("checkInDate", checkInDate);
                    bundle.putString("checkOutDate", checkOutDate);
                    Log.d(TAG,String.valueOf(reservationMap.size()));
                    bundle.putSerializable("reservationMap", reservationMap);
                    roomCheckFragment.setArguments(bundle);
                    manager = getActivity().getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.content, roomCheckFragment, "fragment");
                    transaction.addToBackStack("fragment");
                    transaction.commit();
                }
            }
        });
    }

    private class RoomTypeAdapter extends
            RecyclerView.Adapter<RoomTypeAdapter.MyViewHolder> {
        private Context context;
        private List<RoomType> roomTypeList;

        RoomTypeAdapter(Context context, List<RoomType> roomTypeList) {
            this.context = context;
            this.roomTypeList = roomTypeList;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivRoomType;
            TextView tvRoomTypeName, tvRoomTypeSize, tvRoomTypeBed, tvRoomTypeAdult,
                    tvRoomTypeLastQuantity, tvRoomTypePrice;
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
            String url = URL + "/RoomServlet";
            myViewHolder.ivRoomType.setImageResource(R.drawable.pic_roomtype_2seaview);
            myViewHolder.tvRoomTypeName.setText(roomType.getRoomTypeName());
            myViewHolder.tvRoomTypeSize.setText(roomType.getRoomTypeSize());
            myViewHolder.tvRoomTypeBed.setText(roomType.getRoomTypeBed());
            myViewHolder.tvRoomTypeAdult.setText(roomType.getRoomTypeAdult());
            myViewHolder.tvRoomTypeLastQuantity.setText(roomType.getRoomTypeLastQuantity());
            myViewHolder.tvRoomTypePrice.setText(roomType.getRoomTypePrice());
            myViewHolder.ibAddRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int roomQuantity = Integer.valueOf(myViewHolder.tvRoomTypeLastQuantity.getText().toString());
                    if (roomQuantity > 0) {
                        roomQuantity = roomQuantity - 1;
                        myViewHolder.tvRoomTypeLastQuantity.setText(String.valueOf(roomQuantity));
                        String roomName = myViewHolder.tvRoomTypeName.getText().toString();
                        if (reservationMap.containsKey(roomName) == false) {
                            reservationMap.put(roomName, 1);
                        } else {
                            int quantity = reservationMap.get(roomName);
                            quantity = quantity + 1;
                            reservationMap.put(roomName, quantity);
                            Log.d(TAG, roomName + String.valueOf(quantity));
                        }
                        showToast(getActivity(),"已將房間加入訂單");
                    } else {
                        showToast(getActivity(),"您選的房間已被訂完");
                    }
                }
            });
        }
    }

    

    public List<RoomType> getRoomTypeList() {
        List<RoomType> roomTypeList = new ArrayList<>();
        roomTypeList.add(new RoomType("3",
                "4100", "海景標準雙人房",
                "35平方公尺", "1張雙人床",
                "2"));
        roomTypeList.add(new RoomType("2",
                "3800", "山景標準雙人房",
                "35平方公尺", "1張雙人床",
                "2"));
        roomTypeList.add(new RoomType("1",
                "5300", "海景標準四人房",
                "45平方公尺", "2張雙人床",
                "4"));
        roomTypeList.add(new RoomType("3",
                "4900", "山景標準四人房",
                "45平方公尺", "1張雙人床",
                "4"));
        roomTypeList.add(new RoomType("1",
                "5800", "海景精緻雙人房",
                "42平方公尺", "1張雙人床",
                "2"));
        roomTypeList.add(new RoomType("2",
                "5400", "山景精緻雙人房",
                "42平方公尺", "1張雙人床",
                "2"));
        roomTypeList.add(new RoomType("1",
                "7000", "海景精緻四人房",
                "52平方公尺", "1張雙人床",
                "4"));
        roomTypeList.add(new RoomType("1",
                "6800", "山景精緻四人房",
                "52平方公尺", "1張雙人床",
                "4"));
        roomTypeList.add(new RoomType("1",
                "8000", "海景豪華雙人房",
                "60平方公尺", "1張雙人床",
                "2"));
        roomTypeList.add(new RoomType("1",
                "7600", "山景豪華雙人房",
                "60平方公尺", "1張雙人床",
                "2"));
        return roomTypeList;
    }
}
