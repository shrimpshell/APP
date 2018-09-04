package com.example.hsinhwang.shrimpshell;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class RoomDetailActivity extends AppCompatActivity {
    private Window window;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        initialization();
        handleBundle();
    }

    private void initialization() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.setStatusBarColor(Color.parseColor("#01728B"));
        }
        textView = findViewById(R.id.roomPageTextView);
    }

    private void handleBundle() {
        Bundle bundle = getIntent().getExtras();
        String text = "Data error!!";
        if (bundle != null) {
            Object score = bundle.getSerializable("room");
            if (score != null) {
                text = score.toString();
            }
        }
        textView.setText(text);
    }
}
