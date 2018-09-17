package com.example.hsinhwang.shrimpshell.CustomerPanel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.Order;
import com.example.hsinhwang.shrimpshell.Classes.OrderDetail;
import com.example.hsinhwang.shrimpshell.ProfileReceiptDetailResultActivity;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProfileReceiptListFragment extends Fragment {

    private static final String TAG = "PRListFragment";
    private static final int REQ_ORDERDETAIL = 1;
    private String order_id;
    private CommonTask memberTask;
    private FragmentActivity activity;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_receipt, container, false);
        handleViews(view);

        return view;
    }

    private void handleViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(new ProfileReceiptDetailAdapter(activity, getPayDetailById("1")));//設定Adapter
    }


    private class ProfileReceiptDetailAdapter extends RecyclerView.Adapter<ProfileReceiptDetailAdapter.MyViewHolder> {
        private Context context;
        private List<Order> orderList;

        ProfileReceiptDetailAdapter(Context context, List<Order> orderList) {//ProfileReceiptDetailAdapter 建構式
            this.context = context;
            this.orderList = orderList;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {//ProfileReceiptDetailAdapter內部類別MyViewHolder
            TextView date, orderNumber, roomQuantity, roomType, mealsQuantity, meals, result, discounted, totalPrice;


            MyViewHolder(View itemView) {//MyViewHolder 建構式
                super(itemView);
                date = itemView.findViewById(R.id.date);//訂房日期起－迄
                orderNumber = itemView.findViewById(R.id.orderNumber);//訂單編號
                roomQuantity = itemView.findViewById(R.id.roomQuantity);//房間數量
                roomType = itemView.findViewById(R.id.roomType);//房間型態
                mealsQuantity = itemView.findViewById(R.id.mealsQuantity);//餐點數量
                meals = itemView.findViewById(R.id.meals);//餐點
                discounted = itemView.findViewById(R.id.discounted);//折扣
                totalPrice = itemView.findViewById(R.id.totalPrice);//總價(需計算餐點加房間價格)
                result = itemView.findViewById(R.id.result);//付款狀態
            }
        }

        @Override
        public int getItemCount() {//ProfileReceiptDetailAdapter的方法getItemCount 取得itemView 數量 畫面近來呼叫一次
            return orderList.size();
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
            final Order order = orderList.get(position);
            List<OrderDetail> orderDetailList = order.getOrderDetailList();//每一筆訂單預定只會訂一間房
            OrderDetail orderDetail = orderDetailList.get(0);//訂單明細
            int roomoQuantity = orderDetailList.size();
            String dateText = order.getCheckInDate() + " - " + order.getCheckOuntDate();
            order_id = String.valueOf(order.getIdRoomReservation());
            viewHolder.date.setText(dateText);//顯示訂房日期區間
            viewHolder.orderNumber.setText("訂單編號:" + order_id);//訂單編號
            viewHolder.roomQuantity.setText(String.valueOf(roomoQuantity) + "間");//房間數量
            viewHolder.roomType.setText(orderDetail.getRoomType());//房型名稱
            viewHolder.mealsQuantity.setText(orderDetail.getQuantity() == null ? "" : orderDetail.getQuantity() + "份");//餐點數量
            viewHolder.meals.setText(orderDetail.getDiningTypeName() == null ? "" : orderDetail.getDiningTypeName());
            viewHolder.discounted.setText(orderDetail.getDiscount() == null ? "" : "優惠折扣" + " " + Common.getInt(Double.parseDouble(orderDetail.getDiscount()))+ "折");
            viewHolder.discounted.setVisibility(View.INVISIBLE);
            viewHolder.result.setText(getRoomReservationStatus(order.getRoomReservationStatus()));
            double roomPrice, dtPrice, quantity, discount, totalPrice, originalPrice;
            roomPrice = orderDetail.getPrice() == null ? 0 : Double.parseDouble(orderDetail.getPrice());
            dtPrice = orderDetail.getDtPrice() == null ? 0 : Double.parseDouble(orderDetail.getDtPrice());
            quantity = orderDetail.getQuantity() == null ? 0 : Double.parseDouble(orderDetail.getQuantity());
            discount = orderDetail.getDiscount() == null ? 1 : Double.parseDouble(orderDetail.getDiscount()) / 10;
            originalPrice = roomPrice + dtPrice * quantity;
            totalPrice = (originalPrice) * discount;
            viewHolder.totalPrice.setText("總額NT$" + Common.getInt(originalPrice));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {//每個itemView 設定監聽事件
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ProfileReceiptDetailResultActivity.class);
                    Bundle bundle = new Bundle();
                    //換頁前 準備Order資料等等用bundle將資料帶到下一頁
                    bundle.putSerializable("order", order);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQ_ORDERDETAIL);//ProfileReceiptListFragment >> ProfileReceiptDetailResultActivity換頁

                }
            });

        }
    }

    /*
     *透過前一頁傳來的idCustomer,連結web server取得客戶消費明細
     */
    private List<Order> getPayDetailById(String idCustomer) {

        List<Order> orderList = new ArrayList<>();

        String url = Common.URL + "/PayDetailServlet";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "findPayDetailById");
        jsonObject.addProperty("idCustomer", idCustomer);
        Log.d(TAG, "findPayDetailById jsonObject:" + jsonObject.toString());
        memberTask = new CommonTask(url, jsonObject.toString());
        try {
            String jsonIn = memberTask.execute().get();
            Log.d(TAG, "findPayDetailById orderList Result:" + jsonIn.toString());
            Type listType = new TypeToken<List<Order>>() {
            }.getType();
            orderList = new Gson().fromJson(jsonIn, listType);

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return orderList;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQ_ORDERDETAIL) && (resultCode == Activity.RESULT_OK)) {
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setAdapter(new ProfileReceiptDetailAdapter(activity, getPayDetailById("1")));//設定Adapter
        }


    }

    /*
     *檢查裝置是否連網(傳fragment當下所在的activity當參數)
     */
    private boolean networkConnected(Activity activity) {
        ConnectivityManager conManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager != null ? conManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }

    private String getRoomReservationStatus(String roomReservationStatus) {
        String status = "";
        switch (roomReservationStatus) {
            case "1":
                status = "待付款";
                break;
            case "2":
                status = "已付款";
                break;
            default:
                status = "未付款";//"0"
        }

        return status;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (memberTask != null) {
            memberTask.cancel(true);
        }
    }


}