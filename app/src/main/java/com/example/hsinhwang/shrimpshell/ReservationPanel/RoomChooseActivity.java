package com.example.hsinhwang.shrimpshell.ReservationPanel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.ReservationDate;
import com.example.hsinhwang.shrimpshell.Classes.RoomType;
import com.example.hsinhwang.shrimpshell.R;

import java.util.ArrayList;
import java.util.List;

public class RoomChooseActivity extends AppCompatActivity {
    private FloatingActionButton fabRoomChoose;
    private RecyclerView rvRoomChoose;
    private List<RoomType> roomTypeList;
    private Window window;
    private Button btRoomCheck;
    private RoomType roomType;
    private String TAG = "no data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_choose);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.setStatusBarColor(Color.parseColor("#01728B"));
        }
        handleViews();
    }

    private void handleViews() {
        Bundle bundle = this.getIntent().getExtras();
        int adultQuantity, childQuantity;
        if (bundle != null) {
            adultQuantity = bundle.getInt("AdultQuantity");
            childQuantity = bundle.getInt("ChildQuantity");
            Log.i(TAG, String.valueOf(adultQuantity));
            Log.i(TAG, String.valueOf(childQuantity));
        }
        rvRoomChoose = findViewById(R.id.rvRoomChoose);
        rvRoomChoose.setLayoutManager(
                new StaggeredGridLayoutManager(1,
                        StaggeredGridLayoutManager.HORIZONTAL));
        roomTypeList = getRoomTypeList();

        rvRoomChoose.setAdapter(new RoomTypeAdapter(this, roomTypeList));

        /* 不處理捲動事件所以監聽器設為null */
        rvRoomChoose.setOnFlingListener(null);
        /* 如果希望一次滑動一頁資料，要加上PagerSnapHelper物件 */
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rvRoomChoose);
        btRoomCheck = findViewById(R.id.btRoomCheck);
        btRoomCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomChooseActivity.this,
                        RoomCheckFragment.class);
                startActivity(intent);
            }
        });
        fabRoomChoose = findViewById(R.id.fabRoomChoose);
        fabRoomChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int roomTypeLastQuantity;
                roomTypeLastQuantity = Integer.valueOf(roomType.getRoomTypeLastQuantity());
                if (roomTypeLastQuantity > 0) {
                    roomType.setRoomTypeLastQuantity(String.valueOf(roomTypeLastQuantity - 1));
                    Log.i(TAG, String.valueOf(roomType.getRoomTypeLastQuantity()));
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
            Button btRoomCheck;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                ivRoomType = itemView.findViewById(R.id.ivRoomType);
                tvRoomTypeName = itemView.findViewById(R.id.tvRoomTypeName);
                tvRoomTypeSize = itemView.findViewById(R.id.tvRoomTypeSize);
                tvRoomTypeBed = itemView.findViewById(R.id.tvRoomTypeBed);
                tvRoomTypeAdult = itemView.findViewById(R.id.tvRoomTypePeopleAdult);
                tvRoomTypeLastQuantity = itemView.findViewById(R.id.tvRoomTypeLastQuantity);
                tvRoomTypePrice = itemView.findViewById(R.id.tvRoomTypePrice);
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
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            final RoomType roomType = roomTypeList.get(i);
            myViewHolder.ivRoomType.setImageResource(roomType.getRoomTypeImageId());
            myViewHolder.tvRoomTypeName.setText(roomType.getRoomTypeName());
            myViewHolder.tvRoomTypeSize.setText(roomType.getRoomTypeSize());
            myViewHolder.tvRoomTypeBed.setText(roomType.getRoomTypeBed());
            myViewHolder.tvRoomTypeAdult.setText(roomType.getRoomTypeAdult());
            myViewHolder.tvRoomTypeLastQuantity.setText(roomType.getRoomTypeLastQuantity());
            myViewHolder.tvRoomTypePrice.setText(roomType.getRoomTypePrice());
        }
    }

    public List<RoomType> getRoomTypeList() {
        List<RoomType> roomTypeList = new ArrayList<>();
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "3",
                "4100", "海景標準雙人房",
                "35平方公尺", "1張雙人床",
                "3位大人"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "2",
                "3800", "山景標準雙人房",
                "35平方公尺", "1張雙人床",
                "3位大人"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "1",
                "5300", "海景標準四人房",
                "45平方公尺", "2張雙人床",
                "4位大人"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "3",
                "4900", "山景標準四人房",
                "45平方公尺", "1張雙人床",
                "4位大人"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "1",
                "5800", "海景精緻雙人房",
                "42平方公尺", "1張雙人床",
                "3位大人"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "2",
                "5400", "山景精緻雙人房",
                "42平方公尺", "1張雙人床",
                "3位大人"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "1",
                "7000", "海景精緻四人房",
                "52平方公尺", "1張雙人床",
                "5位大人"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "1",
                "6800", "山景精緻四人房",
                "52平方公尺", "1張雙人床",
                "5位大人"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "1",
                "8000", "海景豪華雙人房",
                "60平方公尺", "1張雙人床",
                "3位大人"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                "1",
                "7600", "山景豪華雙人房",
                "60平方公尺", "1張雙人床",
                "3位大人"));
        roomTypeList.add(new RoomType(R.drawable.pic_roomtype_2seaview,
                String.valueOf(R.string.roomTypeLastQuantity),
                String.valueOf(R.string.roomTypePrice), String.valueOf(R.string.roomTypeName),
                String.valueOf(R.string.roomTypeSize), String.valueOf(R.string.roomTypeBed),
                String.valueOf(R.string.roomTypePeopleAdult)));
        return roomTypeList;
    }
}
