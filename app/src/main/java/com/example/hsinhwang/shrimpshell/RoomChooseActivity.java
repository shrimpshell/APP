package com.example.hsinhwang.shrimpshell;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.RoomType;

import java.util.ArrayList;
import java.util.List;

public class RoomChooseActivity extends AppCompatActivity {
    private FloatingActionButton fabRoomChoose;
    private RecyclerView rvRoomChoose;
    private List<RoomType> roomTypeList;
    private Window window;

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

        FloatingActionButton fabRoomChoose = (FloatingActionButton) findViewById(R.id.fabRoomChoose);
        fabRoomChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
                    tvRoomTypeChild, tvRoomTypeLastQuantity, tvRoomTypeQuantity, tvRoomTypePrice;
            ImageButton ibtRoomQuantityMinus, ibtRoomQuantityPlus;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);
                ivRoomType = findViewById(R.id.ivRoomType);
                tvRoomTypeName = findViewById(R.id.tvRoomTypeName);
                tvRoomTypeSize = findViewById(R.id.tvRoomTypeSize);
                tvRoomTypeBed = findViewById(R.id.tvRoomTypeBed);
                tvRoomTypeAdult = findViewById(R.id.tvRoomTypePeopleAdult);
                tvRoomTypeChild = findViewById(R.id.tvRoomTypePeopleChild);
                tvRoomTypeLastQuantity = findViewById(R.id.tvRoomTypeLastQuantity);
                tvRoomTypeQuantity=findViewById(R.id.tvRoomTypeQuantity);
                tvRoomTypePrice = findViewById(R.id.tvRoomTypePrice);
                ibtRoomQuantityMinus = findViewById(R.id.ibtRoomQuantityMinus);
                ibtRoomQuantityPlus = findViewById(R.id.ibtRoomQuantityPlus);
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
            myViewHolder.ivRoomType.setImageResource(roomType.getIvRoomType());
            myViewHolder.tvRoomTypeName.setText(roomType.getTvRoomTypeName());
            myViewHolder.tvRoomTypeSize.setText(roomType.getTvRoomTypeSize());
            myViewHolder.tvRoomTypeBed.setText(roomType.getTvRoomTypeBed());
            myViewHolder.tvRoomTypeAdult.setText(roomType.getTvRoomTypeAdult());
            myViewHolder.tvRoomTypeChild.setText(roomType.getTvRoomTypeChild());
            myViewHolder.tvRoomTypeLastQuantity.setText(roomType.getTvRoomTypeLastQuantity());
            myViewHolder.tvRoomTypeQuantity.setText(roomType.getTvRoomTypeQuantity());
            myViewHolder.tvRoomTypePrice.setText(roomType.getTvRoomTypePrice());
            final int roomQuantity=Integer.valueOf(myViewHolder.tvRoomTypeQuantity.getText().toString());
            myViewHolder.ibtRoomQuantityMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(roomQuantity>0){

                    }
                }
            });
        }
    }

    public List<RoomType> getRoomTypeList() {
        List<RoomType> roomTypeList = new ArrayList<>();
//        roomTypeList.add(R.drawable.pic_roomtype_2seaview,Integer.valueOf(R.string.roomTypeLastQuantity),R.string.roomTypeName,
//                R.string.roomTypeSize,R.string.roomTypeBed,R.string.roomTypePeopleAdult,
//                R.string.roomTypePeopleChild,Integer.valueOf(),
//                Integer.valueOf(R.string.roomTypePrice));
        return roomTypeList;
    }
}
