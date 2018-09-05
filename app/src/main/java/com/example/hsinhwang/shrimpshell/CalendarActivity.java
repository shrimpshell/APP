package com.example.hsinhwang.shrimpshell;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.ReservationDate;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    private TextView tvFirstDay, tvLastDay;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        handleViews();
        showResults();
    }

    private void handleViews() {
        tvFirstDay = findViewById(R.id.tvFirstDay);
        tvLastDay = findViewById(R.id.tvLastDay);
        calendarView = findViewById(R.id.calendarView);
    }

    private void showResults() {
        Bundle bundle = getIntent().getExtras();
        String textFistDay = "Data Error", textLastDay = "Data Error";
        if (bundle != null) {
            ReservationDate reservationDate = (ReservationDate) bundle.getSerializable("reservationDate");
            if (reservationDate != null) {
                textFistDay = reservationDate.getYear().toString() + reservationDate.getMonth().toString() +
                        reservationDate.getDay().toString()+"日";
                textLastDay=reservationDate.getLastYear().toString()+reservationDate.getLastMonth().toString()+
                        reservationDate.getLastDay().toString()+"日";
            }
        }
        tvFirstDay.setText(textFistDay);
        tvLastDay.setText(textLastDay);
    }
}
