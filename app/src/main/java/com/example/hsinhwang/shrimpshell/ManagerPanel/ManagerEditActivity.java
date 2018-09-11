package com.example.hsinhwang.shrimpshell.ManagerPanel;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.Events;
import com.example.hsinhwang.shrimpshell.Classes.Rooms;
import com.example.hsinhwang.shrimpshell.R;
import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ManagerEditActivity extends AppCompatActivity {
    private final static String TAG = "EditActivity";
    private EditText etName, etDescription, etStartTime, etEndTime, etRoomSize, etBed, etAdult, etChild, etQuantity, etPrice;
    private LinearLayout eventElement, roomElement;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_edit);
        initialization();
        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initialization() {
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etStartTime = findViewById(R.id.etStartTime);
        etEndTime = findViewById(R.id.etEndTime);
        eventElement = findViewById(R.id.eventElement);
        btnSubmit = findViewById(R.id.btnSubmit);

        roomElement = findViewById(R.id.roomElement);
        etRoomSize = findViewById(R.id.etRoomSize);
        etBed = findViewById(R.id.etBed);
        etAdult = findViewById(R.id.etAdult);
        etChild = findViewById(R.id.etChild);
        etQuantity = findViewById(R.id.etQuantity);
        etPrice = findViewById(R.id.etPrice);
    }

    private void loadData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Object event = bundle.getSerializable("event");
            Object room = bundle.getSerializable("room");
            if (event != null) {
                final Events obj = (Events) event;
                int startYear = 1900 + obj.getStartDate().getYear();
                String startMonth = (obj.getStartDate().getMonth() + 1) > 10 ? "" + obj.getStartDate().getMonth() : "0" + obj.getStartDate().getMonth();
                String startDate = (obj.getStartDate().getDate()) > 10 ? "" + obj.getStartDate().getDate() : "0" + obj.getStartDate().getDate();
                int endYear = 1900 + obj.getEndDate().getYear();
                String endMonth = (obj.getEndDate().getMonth() + 1) > 10 ? "" + obj.getEndDate().getMonth() : "0" + obj.getEndDate().getMonth();
                String endDate = (obj.getEndDate().getDate()) > 10 ? "" + obj.getEndDate().getDate() : "0" + obj.getEndDate().getDate();

                etName.setText(obj.getName());
                etDescription.setText(obj.getDescription());
                etStartTime.setText(startYear + "-" + startMonth + "-" + startDate);
                etEndTime.setText(endYear + "-" + endMonth + "-" + endDate);
                eventElement.setVisibility(View.VISIBLE);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 儲存到資料庫Event
                        finish();
                    }
                });
            } else {
                roomElement.setVisibility(View.VISIBLE);
                final Rooms obj = (Rooms) room;
                etName.setText(obj.getName());
                etRoomSize.setText(obj.getRoomSize());
                etBed.setText(obj.getBed());
                etAdult.setText(String.valueOf(obj.getAdultQuantity()));
                etChild.setText(String.valueOf(obj.getChildQuantity()));
                etQuantity.setText(String.valueOf(obj.getRoomQuantity()));
                etPrice.setText(String.valueOf(obj.getPrice()));

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 儲存到資料庫Room
                        String name = etName.getText().toString();
                        if (name.length() <= 0) {
                            Common.showToast(ManagerEditActivity.this, R.string.msg_NameIsInvalid);
                            return;
                        }
                        String roomSize = etRoomSize.getText().toString(),
                                bed = etBed.getText().toString();
                        int adult = Integer.parseInt(etAdult.getText().toString()),
                                child = Integer.parseInt(etChild.getText().toString()),
                                quantity = Integer.parseInt(etQuantity.getText().toString()),
                                price = Integer.parseInt(etPrice.getText().toString());
                        if (Common.networkConnected(ManagerEditActivity.this)) {
                            String url = Common.URL + "/RoomServlet";
                            Rooms room = new Rooms(obj.getId(), name, roomSize, bed, adult, child, quantity, price);
                            // String imageBase64 = Base64.encodeToString(image, Base64.DEFAULT);
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("action", "roomUpdate");
                            jsonObject.addProperty("room", new Gson().toJson(room));
                            // jsonObject.addProperty("imageBase64", imageBase64);
                            int count = 0;
                            try {
                                String result = new CommonTask(url, jsonObject.toString()).execute().get();
                                count = Integer.valueOf(result);
                            } catch (Exception e) {
                                Log.e(TAG, e.toString());
                            }
                            if (count == 0) {
                                Common.showToast(ManagerEditActivity.this, R.string.msg_UpdateFail);
                            } else {
                                Common.showToast(ManagerEditActivity.this, R.string.msg_UpdateSuccess);
                            }
                        } else {
                            Common.showToast(ManagerEditActivity.this, R.string.msg_NoNetwork);
                        }
                        finish();
                    }
                });
            }
        }
    }
}
