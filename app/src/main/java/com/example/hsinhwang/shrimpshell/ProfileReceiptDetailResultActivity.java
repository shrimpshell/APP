package com.example.hsinhwang.shrimpshell;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.Order;
import com.example.hsinhwang.shrimpshell.Classes.OrderDetail;
import com.example.hsinhwang.shrimpshell.CustomerPanel.CommonTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProfileReceiptDetailResultActivity extends Activity {
    private static final String TAG = "PRDResultActivity";
    private Window window;
    private Button btheckout;
    private TextView tvSp_line;
    private TextView orderNumber, roomType, tvRoomQuantity, meals, mealsQuantity, total_price, tvDiscount, discounted_detail, discount2, price, last_price, last_prices;
    private Order order;
    private List<OrderDetail> orderDetailList;
    private OrderDetail orderDetail;
    String order_id;
    private CommonTask memberTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_receipt_detail_result);
        initialization();
        handleView();
    }

    private SpannableStringBuilder changeWordColor(String text, int color) {
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(text);
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        return ssBuilder;
    }

    /* 將結果顯示出來 */
    private void handleView() {

        orderNumber = findViewById(R.id.orderNumber);
        roomType = findViewById(R.id.roomType);
        tvRoomQuantity = findViewById(R.id.roomQuantity);
        meals = findViewById(R.id.meals);
        mealsQuantity = findViewById(R.id.mealsQuantity);
        total_price = findViewById(R.id.total_price);
        tvSp_line = findViewById(R.id.tvSp_line);
        tvDiscount = findViewById(R.id.discount);
        discounted_detail = findViewById(R.id.discounted_detail);
        discount2 = findViewById(R.id.discount2);
        price = findViewById(R.id.price);
        last_price = findViewById(R.id.last_price);
        last_prices = findViewById(R.id.last_prices);
        btheckout = findViewById(R.id.btheckout);

        /* 呼叫getIntent()取得Intent物件，再呼叫getExtras()取得Bundle物件 */
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            /* Bundle物件呼叫getSerializable()可以取得前頁儲存的Serializable物件 */
            order = (Order) bundle.getSerializable("order");//每一筆訂單預定只會訂一間房
            orderDetailList = order.getOrderDetailList();
            orderDetail = orderDetailList.get(0);//訂單明細
            int roomoQuantity = orderDetailList.size();
            order_id = String.valueOf(order.getIdRoomReservation());
            orderNumber.setText("訂單編號:" + order_id);
            roomType.setText(orderDetail.getRoomType());
            tvRoomQuantity.setText(String.valueOf(roomoQuantity) + "間");
            meals.setText(orderDetail.getDiningTypeName() == null ? "" : orderDetail.getDiningTypeName());
            mealsQuantity.setText(orderDetail.getQuantity()== null ? "" : orderDetail.getQuantity() + "份");
            double roomPrice, dtPrice, quantity, discount, totalPrice, originalPrice;
            roomPrice = orderDetail.getPrice() == null ? 0 : Double.parseDouble(orderDetail.getPrice());
            dtPrice = orderDetail.getDtPrice() == null ? 0 : Double.parseDouble(orderDetail.getDtPrice());
            quantity = orderDetail.getQuantity() == null ? 0 : Double.parseDouble(orderDetail.getQuantity());
            discount = orderDetail.getDiscount() == null ? 1 : Double.parseDouble(orderDetail.getDiscount()) / 10;
            originalPrice = roomPrice + dtPrice * quantity;
            totalPrice = (originalPrice) * discount;
            total_price.setText("總額NT$" + Common.getInt(totalPrice));
            last_prices.setText(String.valueOf(Common.getInt(totalPrice)));
            price = findViewById(R.id.price);
            price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            price.setText("原價NT$" + Common.getInt(originalPrice));
            if (order.getRoomReservationStatus().equals("1") || order.getRoomReservationStatus().equals("2")) {
                btheckout.setVisibility(View.GONE);//若清單狀態為已付款,則不出現退房按鈕
            }
            if (String.valueOf(discount).equals("1")) {//沒有折扣就不顯示相關內容
                tvDiscount.setVisibility(View.GONE);
                discounted_detail.setVisibility(View.GONE);
                discount2.setVisibility(View.GONE);
                last_price.setVisibility(View.GONE);
                last_prices.setVisibility(View.GONE);
                price.setVisibility(View.GONE);
                tvSp_line.setVisibility(View.GONE);
            }
        }

        btheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileReceiptDetailResultActivity.this);
                SpannableStringBuilder ssBuilder = changeWordColor("辦理中", Color.WHITE);
                SpannableStringBuilder ssBuilder2 = changeWordColor("請至櫃檯完成付款手續" + "\n" + "謝謝", Color.WHITE);

                builder.setTitle(ssBuilder);

                builder.setMessage(ssBuilder2);
                //設定AlertDiaolog視窗按鈕監聽器
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (networkConnected(ProfileReceiptDetailResultActivity.this)) {
                            updateRoomReservationStatusById("1", order_id);//1待付款
                        } else {
                            Common.showToast(ProfileReceiptDetailResultActivity.this, "無網路服務,請檢查網路狀態");
                        }
                        setResult(Activity.RESULT_OK);
                        finish();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(66, 66, 66)));
            }
        });
    }

    private void initialization() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.setStatusBarColor(Color.parseColor("#01728B"));
        }
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
    }

    private void updateRoomReservationStatusById(String roomReservationStatus, String idRoomReservation) {
        int count = 0;//成功更新筆數
        String url = Common.URL + "/PayDetailServlet";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "updateRoomReservationStatusById");
        jsonObject.addProperty("roomReservationStatus", roomReservationStatus);
        jsonObject.addProperty("idRoomReservation", idRoomReservation);
        Log.d(TAG, "updateRoomReservationStatusById jsonObject:" + jsonObject.toString());
        memberTask = new CommonTask(url, jsonObject.toString());
        try {
            String result = memberTask.execute().get();
            count = Integer.valueOf(result);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        if (count == 0) {
            Common.showToast(ProfileReceiptDetailResultActivity.this, "退房失敗,請洽櫃檯協助處理");
        } else {
            Common.showToast(ProfileReceiptDetailResultActivity.this, "退房成功,請至櫃檯付款");
        }

    }



    //檢查裝置是否連網
    private boolean networkConnected(Activity activity) {
        ConnectivityManager conManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager != null ? conManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (memberTask != null) {
            memberTask.cancel(true);
            memberTask = null;
        }

    }

}
