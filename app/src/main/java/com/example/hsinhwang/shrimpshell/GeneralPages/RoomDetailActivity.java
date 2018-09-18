package com.example.hsinhwang.shrimpshell.GeneralPages;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.ImageTask;
import com.example.hsinhwang.shrimpshell.Classes.Rooms;
import com.example.hsinhwang.shrimpshell.R;

import java.io.ByteArrayOutputStream;

public class RoomDetailActivity extends AppCompatActivity {
    private final static String TAG = "RoomDetailActivity";
    private Window window;
    private TextView textView;
    private ImageView roomImageView;

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
        roomImageView = findViewById(R.id.roomImageView);
        textView = findViewById(R.id.roomPageTextView);
    }

    private void handleBundle() {
        Bundle bundle = getIntent().getExtras();
        String text = "Data error!!";
        int id = 0;
        if (bundle != null) {
            Object score = bundle.getSerializable("room");
            Rooms room = (Rooms) score;
            if (score != null) {
                id = room.getId();
                text = room.getDetail();
            }
        }
        loadRoomImage(id);
        textView.setText(text);
    }

    private void loadRoomImage(int id) {
        String url = Common.URL + "/RoomServlet";
        int imageSize = getResources().getDisplayMetrics().widthPixels / 3;
        Bitmap bitmap = null;

        try {
            bitmap = new ImageTask(url, id, imageSize).execute().get();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        if (bitmap != null) {
            roomImageView.setImageBitmap(bitmap);
        } else {
            roomImageView.setImageResource(R.drawable.room_review);
        }
    }
}
