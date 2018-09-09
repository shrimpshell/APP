package com.example.hsinhwang.shrimpshell.ManagerPanel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hsinhwang.shrimpshell.Classes.Rooms;
import com.google.gson.*;
import com.example.hsinhwang.shrimpshell.R;
import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;

public class AddRoomActivity extends AppCompatActivity {
    private final static String TAG = "AddRoomActivity";
    private EditText etAddRoomName, etAddRoomSize, etAddRoomBed, etAddRoomAdult, etAddRoomChild, etAddRoomQuantity;
    private Button btnAddRoom;
    private byte[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        initialization();
    }

    private void initialization() {
        etAddRoomName = findViewById(R.id.etAddRoomName);
        etAddRoomSize = findViewById(R.id.etAddRoomSize);
        etAddRoomBed = findViewById(R.id.etAddRoomBed);
        etAddRoomAdult = findViewById(R.id.etAddRoomAdult);
        etAddRoomChild = findViewById(R.id.etAddRoomChild);
        etAddRoomQuantity = findViewById(R.id.etAddRoomQuantity);

        btnAddRoom = findViewById(R.id.btnAddRoom);
        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 存資料庫
                insertAction();
                finish();
            }
        });
    }

    private void insertAction() {
        String name = etAddRoomName.getText().toString().trim(),
                roomSize = etAddRoomSize.getText().toString().trim(),
                bed = etAddRoomBed.getText().toString().trim();
        int adult = Integer.parseInt(etAddRoomAdult.getText().toString().trim()),
                child = Integer.parseInt(etAddRoomChild.getText().toString().trim()),
                quantity = Integer.parseInt(etAddRoomQuantity.getText().toString().trim());

        if (Common.networkConnected(this)) {
            String url = Common.URL + "/RoomServlet";
            Rooms room = new Rooms(0, name, roomSize, bed, adult, child, quantity);
//            String imageBase64 = Base64.encodeToString(image, Base64.DEFAULT);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "roomInsert");
            jsonObject.addProperty("room", new Gson().toJson(room));
//            jsonObject.addProperty("imageBase64", imageBase64);
            int count = 0;
            try {
                String result = new CommonTask(url, jsonObject.toString()).execute().get();
                count = Integer.valueOf(result);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (count == 0) {
                Common.showToast(this, R.string.msg_InsertFail);
            } else {
                Common.showToast(this, R.string.msg_InsertSuccess);
            }
        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
    }
}
