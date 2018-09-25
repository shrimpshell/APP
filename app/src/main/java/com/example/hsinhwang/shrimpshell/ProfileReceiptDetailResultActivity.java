package com.example.hsinhwang.shrimpshell;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.ProfileReceiptDetail;

public class ProfileReceiptDetailResultActivity extends Activity {
    private Window window;
    private Button btheckout;
    private TextView tvSp_line;
    private TextView orderNumber, date, roomType, roomQuantity, meals, mealsQuantity, total_price, discount, discounted_detail, discount2, price, last_price, last_prices;
    private ProfileReceiptDetail profileReceiptDetail;
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
        roomQuantity = findViewById(R.id.roomQuantity);
        meals = findViewById(R.id.meals);
        mealsQuantity = findViewById(R.id.mealsQuantity);
        total_price = findViewById(R.id.total_price);
        tvSp_line =  findViewById(R.id.tvSp_line);
        discount = findViewById(R.id.discount);
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
            profileReceiptDetail = (ProfileReceiptDetail) bundle.getSerializable("profileReceiptDetail");
            orderNumber.setText(profileReceiptDetail.getOrderNumber());
            roomType.setText(profileReceiptDetail.getRoomType());
            roomQuantity.setText(profileReceiptDetail.getRoomQuantity());
            meals.setText(profileReceiptDetail.getMeals());
            mealsQuantity.setText(profileReceiptDetail.getMealsQuantity());
            total_price.setText(profileReceiptDetail.getTotal_price());
            last_prices.setText(profileReceiptDetail.getTotal_price());
            price = findViewById(R.id.price);
            price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            price.setText("原價NT$" + profileReceiptDetail.getTotal_price().replace("總額NT$",""));
            if (profileReceiptDetail.getResult().equals("已付款")||profileReceiptDetail.getResult().equals("待付款")){
                btheckout.setVisibility(View.GONE);//若清單狀態為已付款,則不出現退房按鈕
            }
            if (profileReceiptDetail.getDiscounted() == null || profileReceiptDetail.getDiscounted().length() <= 0){//沒有折扣就不顯示相關內容
                discount.setVisibility(View.GONE);
                discounted_detail.setVisibility(View.GONE);
                discount2.setVisibility(View.GONE);
                last_price.setVisibility(View.GONE);
                discount.setVisibility(View.GONE);
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
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(ProfileReceiptDetailResultActivity.this, MainActivity.class);
//                        Bundle bundle = new Bundle();
//                        Date date = new Date();
//                        ProfileReceiptDetail profileReceiptDetailback = new ProfileReceiptDetail(profileReceiptDetail.getOrder_id(), profileReceiptDetail.getDate_start(), profileReceiptDetail.getDate_end(), profileReceiptDetail.getOrderNumber(), profileReceiptDetail.getRoomType(), profileReceiptDetail.getMealsQuantity(), profileReceiptDetail.getMeals(), profileReceiptDetail.getDiscounted(), profileReceiptDetail.getRoomQuantity(), profileReceiptDetail.getTotal_price(), "待付款");
//                        bundle.putSerializable("profileReceiptDetailback", profileReceiptDetailback);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
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


}
