package com.example.hsinhwang.shrimpshell.ManagerPanel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.hsinhwang.shrimpshell.R;

public class AddEventActivity extends AppCompatActivity {
    private EditText etAddEventName, etAddEventDescription;
    private DatePicker etAddEventStartTime, etAddEventEndTime;
    private Button btnAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        initialization();
    }

    public void initialization() {
        etAddEventName = findViewById(R.id.etAddEventName);
        etAddEventDescription = findViewById(R.id.etAddEventDescription);
        etAddEventStartTime = findViewById(R.id.etAddEventStartTime);
        etAddEventEndTime = findViewById(R.id.etAddEventEndTime);
        btnAddEvent = findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 存資料庫
                finish();
            }
        });
    }
}
