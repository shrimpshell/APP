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
import com.example.hsinhwang.shrimpshell.Classes.Events;
import com.example.hsinhwang.shrimpshell.Classes.ImageTask;
import com.example.hsinhwang.shrimpshell.R;

import java.io.ByteArrayOutputStream;

public class EventActivity extends AppCompatActivity {
    private final static String TAG = "EventActivity";
    private Window window;
    private TextView textView;
    private ImageView imageView;
    // 活動頁面修改 改好玩的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        initialization();
        handleBundle();
    }

    private void initialization() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.setStatusBarColor(Color.parseColor("#01728B"));
        }
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.eventPageTextView);
    }

    private void handleBundle() {
        Bundle bundle = getIntent().getExtras();
        String text = "Data error!!";
        int id = 0;
        if (bundle != null) {
            Object score = bundle.getSerializable("event");
            Events event = (Events) score;
            if (score != null) {
                id = event.getEventId();
                text = event.toString();
            }
        }
        loadEventImage(id);
        textView.setText(text);
    }

    private void loadEventImage(int id) {
        String url = Common.URL + "/EventServlet";
        int imageSize = getResources().getDisplayMetrics().widthPixels / 3;
        Bitmap bitmap = null;

        try {
            bitmap = new ImageTask(url, id, imageSize).execute().get();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.events);
        }
    }
}
