package com.example.hsinhwang.shrimpshell.ReservationPanel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.hsinhwang.shrimpshell.BookingFragment;
import com.example.hsinhwang.shrimpshell.Classes.ReservationDate;
import com.example.hsinhwang.shrimpshell.R;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {
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
    }

    private void handleViews() {
        calendarPickerView = findViewById(R.id.calendar_view);
        Date today = calendar.getTime();
        Calendar calendar1 = calendar;
        calendar1.add(Calendar.DAY_OF_MONTH, 180);
        Date maxDay = calendar1.getTime();
        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
        calendarPickerView.deactivateDates(list);
        calendarPickerView.init(today, maxDay, new SimpleDateFormat("MMMM, YYYY", Locale.getDefault())) //
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(new Date());
//                .withDeactivateDates(list);
        Log.d("list", calendarPickerView.getSelectedDates().toString());
        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                List<Date> selectedDates = calendarPickerView.getSelectedDates();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-E");
                first = selectedDates.get(0);
                firstDay = sdf.format(first);
                Log.d("Day", firstDay);
                last = selectedDates.get(selectedDates.size()-1);
                lastDay = sdf.format(last);
                Log.d("Day", lastDay);
            }

            @Override
            public void onDateUnselected(Date date) {
            }
        });

        fabBackBooking = findViewById(R.id.fabBackBooking);
        fabBackBooking.setOnClickListener(CalendarActivityChange_Listener);
    }

    FloatingActionButton.OnClickListener CalendarActivityChange_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CalendarActivity.this, BookingFragment.class);
            Bundle bundle = new Bundle();
            bundle.putString("firstday", firstDay);
            bundle.putString("lastday", lastDay);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };
}
