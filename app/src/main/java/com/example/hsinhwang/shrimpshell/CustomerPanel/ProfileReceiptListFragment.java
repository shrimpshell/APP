package com.example.hsinhwang.shrimpshell.CustomerPanel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.ProfileReceiptDetail;
import com.example.hsinhwang.shrimpshell.ProfileReceiptDetailResultActivity;
import com.example.hsinhwang.shrimpshell.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfileReceiptListFragment extends Fragment{

    private String order_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_profile_receipt, container, false);
        handleViews(view);

        /*
        List<ProfileReceiptDetail> receiptList = new ArrayList<>();
        receiptList.add(new ProfileReceiptDetail("Title 1", "Detail 1"));
        receiptList.add(new ProfileReceiptDetail("Title 2", "Detail 2"));
        receiptList.add(new ProfileReceiptDetail("Title 3", "Detail 3"));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ReceiptAdapter(inflater, receiptList));*/
        return view;
    }

    private void handleViews(View view) {
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<ProfileReceiptDetail> profileReceiptDetailList = getProfileReceiptDetailList();
        recyclerView.setAdapter(new ProfileReceiptDetailAdapter(getContext(), profileReceiptDetailList));//設定Adapter
    }



    private class ProfileReceiptDetailAdapter extends RecyclerView.Adapter<ProfileReceiptDetailAdapter.MyViewHolder> {
        private Context context;
        private List<ProfileReceiptDetail> profileReceiptDetailList;

        ProfileReceiptDetailAdapter(Context context, List<ProfileReceiptDetail> profileReceiptDetailList) {//ProfileReceiptDetailAdapter 建構式
            this.context = context;
            this.profileReceiptDetailList = profileReceiptDetailList;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {//ProfileReceiptDetailAdapter內部類別MyViewHolder
            TextView date, orderNumber, roomQuantity, roomType, mealsQuantity, meals, result, discounted, totalPrice;


            MyViewHolder(View itemView) {//MyViewHolder 建構式
                super(itemView);
                date = itemView.findViewById(R.id.date);
                orderNumber = itemView.findViewById(R.id.orderNumber);
                roomQuantity = itemView.findViewById(R.id.roomQuantity);
                roomType = itemView.findViewById(R.id.roomType);
                mealsQuantity = itemView.findViewById(R.id.mealsQuantity);
                meals = itemView.findViewById(R.id.meals);
                discounted = itemView.findViewById(R.id.discounted);
                totalPrice = itemView.findViewById(R.id.totalPrice);
                result = itemView.findViewById(R.id.result);
                itemView.setOnClickListener(new View.OnClickListener() {//每個itemView 設定監聽事件
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ProfileReceiptDetailResultActivity.class);
                        Bundle bundle = new Bundle();
                        Date date = new Date();
                        //換頁前 準備ProfileReceiptDetail資料等等用bundle將資料帶到下一頁
                        ProfileReceiptDetail profileReceiptDetail = new ProfileReceiptDetail(order_id, date, date, orderNumber.getText().toString(), roomType.getText().toString(), mealsQuantity.getText().toString(), meals.getText().toString(), discounted.getText().toString(), roomQuantity.getText().toString(), totalPrice.getText().toString(), result.getText().toString());
                        bundle.putSerializable("profileReceiptDetail", profileReceiptDetail);
                        intent.putExtras(bundle);
                        startActivity(intent);//ProfileReceiptListFragment >> ProfileReceiptDetailResultActivity換頁

                    }
                });
            }
        }

        @Override
        public int getItemCount() {//ProfileReceiptDetailAdapter的方法getItemCount 取得itemView 數量 畫面近來呼叫一次
            return profileReceiptDetailList.size();
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {//ProfileReceiptDetailAdapter的方法onCreateViewHolder 有幾個itemView 就呼叫幾次
            View itemView = LayoutInflater.from(context).
                    inflate(R.layout.receipt_item, viewGroup, false);//取得每一個receipt_item準備給產生MyViewHolder時初始化建構式用
            return new MyViewHolder(itemView);//將產生的MyViewHolder 傳到下一個方法onBindViewHolder使用 準備產生畫面

        }


        @Override
        public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {//ProfileReceiptDetailAdapter的方法onBindViewHolder 有幾個MyViewHolder 就呼叫幾次
            final ProfileReceiptDetail profileReceiptDetail = profileReceiptDetailList.get(position);
            String dateText = dateToSting(profileReceiptDetail.getDate_start()) + " - " +dateToSting(profileReceiptDetail.getDate_end());
            order_id = profileReceiptDetail.getOrder_id();
            viewHolder.date.setText(dateText);
            viewHolder.orderNumber.setText("訂單編號:" + profileReceiptDetail.getOrderNumber());
            viewHolder.roomQuantity.setText(profileReceiptDetail.getRoomQuantity()+ "間");
            viewHolder.roomType.setText(profileReceiptDetail.getRoomType());
            viewHolder.mealsQuantity.setText(profileReceiptDetail.getMealsQuantity().equals("") ? "" : profileReceiptDetail.getMealsQuantity()+ "份");
            viewHolder.meals.setText(profileReceiptDetail.getMeals().equals("") ? "" : profileReceiptDetail.getMeals());
            viewHolder.discounted.setText(profileReceiptDetail.getDiscounted().equals("") ? "" : profileReceiptDetail.getDiscounted());
            viewHolder.result.setText(profileReceiptDetail.getResult());
            viewHolder.totalPrice.setText("總額NT$" + profileReceiptDetail.getTotal_price());


            /*viewHolder.item_view_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView iv = new ImageView(context);
                    iv.setImageResource(ProfileReceiptDetail.getImage());
                    Toast toast = new Toast(context);
                    toast.setView(iv);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                }
            });*/
        }
    }

    private List<ProfileReceiptDetail> getProfileReceiptDetailList() {
        List<ProfileReceiptDetail> profileReceiptDetailList = new ArrayList<>();
        Date date = new Date();
        profileReceiptDetailList.add(new ProfileReceiptDetail("1", date, date, "123456", "海景標準雙人房", "2", "A餐", "享8折優惠", "1" ,"4240", "未付款"));
        profileReceiptDetailList.add(new ProfileReceiptDetail("2", date, date, "123457", "海景精緻雙人房", "", "", "", "1", "4900", "已付款"));
        profileReceiptDetailList.add(new ProfileReceiptDetail("3", date, date, "123458", "山景精緻雙人房", "", "", "", "1", "3400", "已付款"));

        return profileReceiptDetailList;
    }

    //日期轉字串
    public String dateToSting(Date date){
        //設定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        //進行轉換
        String dateString = sdf.format(date);

        return dateString;
    }
}