package com.example.hsinhwang.shrimpshell.ManagerPanel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hsinhwang.shrimpshell.R;

public class AddRoomActivity extends AppCompatActivity {
    private EditText etAddRoomName, etAddRoomDescription;
    private Button btnAddRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        initialization();
    }

    private void initialization() {
        etAddRoomName = findViewById(R.id.etAddRoomName);
        etAddRoomDescription = findViewById(R.id.etAddRoomDescription);
        btnAddRoom = findViewById(R.id.btnAddRoom);
        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 存資料庫
                finish();
            }
        });
    }
}
