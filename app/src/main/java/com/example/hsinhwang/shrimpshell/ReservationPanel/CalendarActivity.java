package com.example.hsinhwang.shrimpshell.ReservationPanel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.example.hsinhwang.shrimpshell.BookingFragment;
import com.example.hsinhwang.shrimpshell.Classes.ReservationDate;
import com.example.hsinhwang.shrimpshell.R;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {
    private TextView tvFirstDay, tvLastDay;
    private Calendar calendar = Calendar.getInstance();
    private CalendarPickerView calendarPickerView;
    private Date first, last;
    private String firstDay, lastDay;
    private FloatingActionButton fabBackBooking;
    ReservationDate reservationDate;
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
//        calendarView = findViewById(R.id.calendarView);

//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int week = calendar.get(Calendar.DAY_OF_WEEK);
//        calendar.add(Calendar.DAY_OF_MONTH, 180);
//        int maxYear = calendar.get(Calendar.YEAR);
//        int maxMonth = calendar.get(Calendar.MONTH);
//        final int maxDay = calendar.get(Calendar.DAY_OF_MONTH);
//        int maxWeek = calendar.get(Calendar.DAY_OF_WEEK);
//        calendar.set(year, month, day);
//        calendarView.setMinDate(calendar.getTimeInMillis());
//        calendar.set(maxYear, maxMonth, maxDay);
//        calendarView.setMaxDate(calendar.getTimeInMillis());
//
//        // perform setOnDateChangeListener event on CalendarView
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                // display the selected date by using a toast
//                calendar.set(year, month, dayOfMonth);
//                calendarView.setDate(calendar.getTimeInMillis());
//                tvLastDay.setText(String.valueOf(year) + "年" + String.valueOf(month + 1) + "月" + String.valueOf(dayOfMonth) + "日");
//                Toast.makeText(getApplicationContext(), year + "-" + (month + 1) + "-" + dayOfMonth, Toast.LENGTH_LONG).show();
//            }
//        });
//        long selectedDate = calendarView.getDate();
        calendarPickerView = findViewById(R.id.calendar_view);
        Date today = calendar.getTime();
        Calendar calendar1 = calendar;
        calendar1.add(Calendar.DAY_OF_MONTH, 180);
        Date maxDay = calendar1.getTime();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        calendarPickerView.deactivateDates(list);
        calendarPickerView.init(today, maxDay, new SimpleDateFormat("MMMM, YYYY", Locale.getDefault())) //
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(new Date())
                .withDeactivateDates(list);
        Log.d("list", calendarPickerView.getSelectedDates().toString());
        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                List<Date> selectedDates = calendarPickerView.getSelectedDates();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-E");
                if (selectedDates.size() < 2) {
                    first = selectedDates.get(0);
                    firstDay = sdf.format(first);
                    Log.d("Day", firstDay);
                } else {
                    first = selectedDates.get(0);
                    firstDay = sdf.format(first);
                    Log.d("Day", firstDay);
                    last = selectedDates.get(1);
                    lastDay = sdf.format(last);
                    Log.d("Day", lastDay);
                }
            }

            @Override
            public void onDateUnselected(Date date) {
            }
        });

        fabBackBooking = findViewById(R.id.fabBackBooking);
        fabBackBooking.setOnClickListener(CalendarActivityChange_Listener);
    }

    private void showResults() {
        Bundle bundle = getIntent().getExtras();
        String textFistDay = "Data Error", textLastDay = "Data Error";
        if (bundle != null) {
            reservationDate = (ReservationDate) bundle.getSerializable("reservationDate");
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
            Bundle bundle = new Bundle();
            bundle.putString("firstday",firstDay);
            bundle.putString("lastday",lastDay);
            intent.putExtras(bundle);
            finish();
        }
    };
}
