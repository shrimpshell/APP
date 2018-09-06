package com.example.hsinhwang.shrimpshell;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.ReservationDate;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    private TextView tvFirstDay, tvLastDay;
    private CalendarView calendarView;
    private FloatingActionButton fabBackBooking;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.setStatusBarColor(Color.parseColor("#01728B"));
        }
        handleViews();
        showResults();
    }

    private void handleViews() {
        tvFirstDay = findViewById(R.id.tvFirstDay);
        tvLastDay = findViewById(R.id.tvLastDay);
        calendarView = findViewById(R.id.calendarView);
        fabBackBooking = findViewById(R.id.fabBackBooking);
        fabBackBooking.setOnClickListener(CalendarActivityChange_Listener);
    }

    private void showResults() {
        Bundle bundle = getIntent().getExtras();
        String textFistDay = "Data Error", textLastDay = "Data Error";
        if (bundle != null) {
            ReservationDate reservationDate = (ReservationDate) bundle.getSerializable("reservationDate");
            if (reservationDate != null) {
                textFistDay = reservationDate.getYear().toString() + reservationDate.getMonth().toString() +
                        reservationDate.getDay().toString() + "日";
                textLastDay = reservationDate.getLastYear().toString() + reservationDate.getLastMonth().toString() +
                        reservationDate.getLastDay().toString() + "日";
            }
        }
        tvFirstDay.setText(textFistDay);
        tvLastDay.setText(textLastDay);
    }

    FloatingActionButton.OnClickListener CalendarActivityChange_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CalendarActivity.this, BookingFragment.class);
            finish();
        }
    };
}
