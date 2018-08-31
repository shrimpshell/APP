package com.example.hsinhwang.shrimpshell.ManagerPanel;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.hsinhwang.shrimpshell.Classes.Events;
import com.example.hsinhwang.shrimpshell.Classes.Rooms;
import com.example.hsinhwang.shrimpshell.R;

public class ManagerEditActivity extends AppCompatActivity {
    private EditText etName, etDescription, etStartTime, etEndTime;
    private LinearLayout eventElement;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_edit);
        initialization();
        loadData();
    }

    private void initialization() {
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etStartTime = findViewById(R.id.etStartTime);
        etEndTime = findViewById(R.id.etEndTime);
        eventElement = findViewById(R.id.eventElement);
        btnSubmit = findViewById(R.id.btnSubmit);
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
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 儲存到資料庫Event
                        finish();
                    }
                });
            } else {
                Rooms obj = (Rooms) room;
                etName.setText(obj.getRoomName());
                etDescription.setText(obj.getRoomiDetail());
                eventElement.setVisibility(View.GONE);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 儲存到資料庫Room
                        finish();
                    }
                });
            }
        }
    }
}
