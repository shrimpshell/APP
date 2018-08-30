package com.example.hsinhwang.shrimpshell;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class BookingFragment extends Fragment {
    private TextView txFirstDaySelected, txFirstMonSelected, txFirstWeekSelected, txLastDaySelected, txLastMonSelected, txLastWeekSelected, txAdultQuantity, txChildQuatity;
    private Button btAdultMinus, btAdultplus, btChildMinus, btChildplus;
    private Spinner spChildAge;
    private String weekName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton fabBooking = (FloatingActionButton) getView().findViewById(R.id.fabBooking);
        fabBooking.setOnClickListener(BookingFragmentChange_Listener);
        txFirstDaySelected = (TextView) getView().findViewById(R.id.txFirstDaySelected);
        txFirstMonSelected = (TextView) getView().findViewById(R.id.txFirstMonSelected);
        txFirstWeekSelected = (TextView) getView().findViewById(R.id.txFirstWeekSelected);
        txLastDaySelected = (TextView) getView().findViewById(R.id.txLastDaySelected);
        txLastMonSelected = (TextView) getView().findViewById(R.id.txLastMonSelected);
        txLastWeekSelected = (TextView) getView().findViewById(R.id.txLastWeekSelected);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        int lasDay = calendar.get(Calendar.DAY_OF_MONTH) + 1;
        txFirstDaySelected.setText(String.valueOf(day));
        txFirstMonSelected.setText(" " + String.valueOf(month) + " 月");
        txFirstWeekSelected.setText(changerWeekName(week));
        txLastDaySelected.setText(String.valueOf(lasDay));
        txLastMonSelected.setText(" " + String.valueOf(month) + " 月");
        txLastWeekSelected.setText(changerWeekName(week + 1));
    }

    public String changerWeekName(int w) {
        switch (w) {
            case Calendar.SUNDAY:
                weekName = "週日";
                break;
            case Calendar.MONDAY:
                weekName = "週一";
                break;
            case Calendar.TUESDAY:
                weekName = "週二";
                break;
            case Calendar.WEDNESDAY:
                weekName = "週三";
                break;
            case Calendar.THURSDAY:
                weekName = "週四";
                break;
            case Calendar.FRIDAY:
                weekName = "週五";
                break;
            case Calendar.SATURDAY:
                weekName = "週六";
        }
        return weekName;
    }

    FloatingActionButton.OnClickListener BookingFragmentChange_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), EventActivity.class);
            startActivity(intent);
        }
    };


}
