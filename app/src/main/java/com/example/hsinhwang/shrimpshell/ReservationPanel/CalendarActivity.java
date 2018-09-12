package com.example.hsinhwang.shrimpshell.ReservationPanel;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.example.hsinhwang.shrimpshell.BookingFragment;
import com.example.hsinhwang.shrimpshell.Classes.ReservationDate;
import com.example.hsinhwang.shrimpshell.R;

import java.time.Year;


public class CalendarActivity extends AppCompatActivity implements DatePickerController {
    private TextView tvFirstDay, tvLastDay;
    private CalendarView calendarView;
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
        calendarView = findViewById(R.id.calendarView);
        calendarView.setFocusedMonthDateColor(Color.RED); // set the red color for the dates of  focused month
        calendarView.setUnfocusedMonthDateColor(Color.BLUE); // set the yellow color for the dates of an unfocused month
        calendarView.setSelectedWeekBackgroundColor(Color.RED); // red color for the selected week's background
        calendarView.setWeekSeparatorLineColor(Color.GREEN); // green color for the week separator line
        long selectedDate = calendarView.getDate(); // get selected date in milliseconds
        calendarView.setFirstDayOfWeek(2); // set Monday as the first day of the week
        tvFirstDay.setText(calendarView.getFirstDayOfWeek()); // get first day of the week
        calendarView.setDate(1463918226920L); // set selected date 22 May 2016 in milliseconds
        calendarView.setMaxDate(1463918226920L); // set max date supported by this CalendarViewlong maxDate= simpleCalendarView.getMaxDate(); // get max date supported by this CalendarView
        calendarView.setMinDate(1463918226920L); // set min date supported by this CalendarView
        calendarView.setMinDate(1463918226920L); // set min date supported by this CalendarViewlong minDate= simpleCalendarView.getMinDate(); // get min date supported by this CalendarView
        calendarView.setShowWeekNumber(true); // set true value for showing the week numbers.
        calendarView.getShowWeekNumber();
        Drawable verticalBar=calendarView.getSelectedDateVerticalBar();
        calendarView.setSelectedDateVerticalBar(getResources().getDrawable(R.drawable.ic_launcher)); // set the drawable for the vertical bar
        // perform setOnDateChangeListener event on CalendarView
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                month = month + 1;
                Toast.makeText(getApplicationContext(), year + "/" + month + "/" + dayOfMonth, Toast.LENGTH_LONG).show();
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
            finish();
        }
    };

    @Override
    public int getMaxYear() {
        int year = Integer.valueOf(reservationDate.getYear());
        return year;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {

    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

    }
}
