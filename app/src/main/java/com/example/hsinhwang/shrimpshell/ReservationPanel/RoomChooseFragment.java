package com.example.hsinhwang.shrimpshell.ReservationPanel;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsinhwang.shrimpshell.Classes.RoomType;
import com.example.hsinhwang.shrimpshell.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RoomChooseFragment extends Fragment {
    private HashMap<String, Integer> reservationMap;
    private RecyclerView rvRoomChoose;
    private List<RoomType> roomTypeList;
    private Button btRoomCheck;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private int adultQuantity, childQuantity;
    private String checkInDate, checkOutDate;
    private RoomType roomType;
    private String TAG = "Debug";

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
                    Toast.makeText(getActivity(), "您尚未選取房間！", Toast.LENGTH_SHORT).show();
                } else {
                    bundle.putString("checkInDate", checkInDate);
                    bundle.putString("checkOutDate", checkOutDate);
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

//    public class SerializableMap implements Serializable {
//
//        private HashMap<String, Integer> para;
//
//        public SerializableMap(HashMap para) {
//            this.para = para;
//        }
//
//        public HashMap<String, Integer> getMap() {
//            return para;
//        }
//
//        public void setMap(HashMap<String, Integer> para) {
//            this.para = para;
//        }
//    }

//    public RoomChooseFragment newInstance(HashMap para) {
//        RoomChooseFragment fragment = new RoomChooseFragment();
//        SerializableMap serializableMap = new SerializableMap(para);
//        Bundle bundle = new Bundle();
//        bundle.putString("checkInDate", checkInDate);
//        bundle.putString("checkOutDate", checkOutDate);
//        bundle.putSerializable("paramap", serializableMap);
//        fragment.setArguments(bundle);
//        return fragment;
//    }

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
            FloatingActionButton fabRoomChoose;
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
                fabRoomChoose = itemView.findViewById(R.id.fabRoomChoose);
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
            myViewHolder.ivRoomType.setImageResource(roomType.getRoomTypeImageId());
            myViewHolder.tvRoomTypeName.setText(roomType.getRoomTypeName());
            myViewHolder.tvRoomTypeSize.setText(roomType.getRoomTypeSize());
            myViewHolder.tvRoomTypeBed.setText(roomType.getRoomTypeBed());
            myViewHolder.tvRoomTypeAdult.setText(roomType.getRoomTypeAdult());
            myViewHolder.tvRoomTypeLastQuantity.setText(roomType.getRoomTypeLastQuantity());
            myViewHolder.tvRoomTypePrice.setText(roomType.getRoomTypePrice());
            myViewHolder.fabRoomChoose.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(getActivity(), "已將房間加入訂單", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "您選的房間已被訂完", Toast.LENGTH_SHORT).show();
                        ;
                    }
                }
            });
        }

    }

    public List<RoomType> getRoomTypeList() {
        List<RoomType> roomTypeList = new ArrayList<>();
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "3",
                "4100", "海景標準雙人房",
                "35平方公尺", "1張雙人床",
                "2"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "2",
                "3800", "山景標準雙人房",
                "35平方公尺", "1張雙人床",
                "2"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "1",
                "5300", "海景標準四人房",
                "45平方公尺", "2張雙人床",
                "4"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "3",
                "4900", "山景標準四人房",
                "45平方公尺", "1張雙人床",
                "4"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "1",
                "5800", "海景精緻雙人房",
                "42平方公尺", "1張雙人床",
                "2"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "2",
                "5400", "山景精緻雙人房",
                "42平方公尺", "1張雙人床",
                "2"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "1",
                "7000", "海景精緻四人房",
                "52平方公尺", "1張雙人床",
                "4"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "1",
                "6800", "山景精緻四人房",
                "52平方公尺", "1張雙人床",
                "4"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "1",
                "8000", "海景豪華雙人房",
                "60平方公尺", "1張雙人床",
                "2"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "1",
                "7600", "山景豪華雙人房",
                "60平方公尺", "1張雙人床",
                "2"));
//        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
//                String.valueOf(R.string.roomTypeLastQuantity),
//                String.valueOf(R.string.roomTypePrice), String.valueOf(R.string.roomTypeName),
//                String.valueOf(R.string.roomTypeSize), String.valueOf(R.string.roomTypeBed),
//                String.valueOf(R.string.roomTypePeopleAdult)));
        return roomTypeList;
    }
}
