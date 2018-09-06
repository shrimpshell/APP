package com.example.hsinhwang.shrimpshell;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

public class RoomChooseActivity extends AppCompatActivity {
    private FloatingActionButton fabRoomChoose;
    private RecyclerView rvRoomChoose;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_choose);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.setStatusBarColor(Color.parseColor("#01728B"));
        }
        handleViews();
    }

    private void handleViews() {
        rvRoomChoose = findViewById(R.id.rvRoomChoose);
        
        FloatingActionButton fabRoomChoose = (FloatingActionButton) findViewById(R.id.fabRoomChoose);
        fabRoomChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
