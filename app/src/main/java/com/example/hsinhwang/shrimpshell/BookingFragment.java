package com.example.hsinhwang.shrimpshell;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class BookingFragment extends Fragment {
    private TextView tvFirstDaySelected, tvFirstMonSelected, tvFirstWeekSelected, tvLastDaySelected, tvLastMonSelected, tvLastWeekSelected, tvAdultQuantity, tvChildQuantity;
    private ImageButton btAdultMinus, btAdultplus, btChildMinus, btChildplus;
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
        tvFirstDaySelected = (TextView) getView().findViewById(R.id.tvFirstDaySelected);
        tvFirstMonSelected = (TextView) getView().findViewById(R.id.tvFirstMonSelected);
        tvFirstWeekSelected = (TextView) getView().findViewById(R.id.tvFirstWeekSelected);
        tvLastDaySelected = (TextView) getView().findViewById(R.id.tvLastDaySelected);
        tvLastMonSelected = (TextView) getView().findViewById(R.id.tvLastMonSelected);
        tvLastWeekSelected = (TextView) getView().findViewById(R.id.tvLastWeekSelected);
        tvAdultQuantity = (TextView) getView().findViewById(R.id.tvAdultQuantity);
        tvChildQuantity = (TextView) getView().findViewById(R.id.tvChildQuantity);
        btAdultMinus = (ImageButton) getActivity().findViewById(R.id.btAdultMinus);
        btAdultplus = (ImageButton) getActivity().findViewById(R.id.btAdultPlus);
        btChildMinus = (ImageButton) getActivity().findViewById(R.id.btChildMinus);
        btChildplus = (ImageButton) getActivity().findViewById(R.id.btChildPlus);
        spChildAge = (Spinner) getActivity().findViewById(R.id.spChildAge);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        int lastMonth = calendar.get(Calendar.MONTH) + 1;
        int lastDay = calendar.get(Calendar.DAY_OF_MONTH);
        int lastWeek = calendar.get(Calendar.DAY_OF_WEEK);

        tvFirstDaySelected.setText(String.valueOf(day));
        tvFirstMonSelected.setText(" " + String.valueOf(month) + " 月");
        tvFirstWeekSelected.setText(changerWeekName(week));
        tvLastDaySelected.setText(String.valueOf(lastDay));
        tvLastMonSelected.setText(" " + String.valueOf(lastMonth) + " 月");
        tvLastWeekSelected.setText(changerWeekName(lastWeek));
        btAdultMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adultQuantity = Integer.parseInt(tvAdultQuantity.getText().toString());
                if (adultQuantity != 0) {
                    tvAdultQuantity.setText(String.valueOf(adultQuantity - 1));
                }
            }
        });
        btChildplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childQuantity = Integer.parseInt(tvChildQuantity.getText().toString());
                tvChildQuantity.setText(String.valueOf(childQuantity + 1));
            }
        });
        btChildMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childQuantity = Integer.parseInt(tvChildQuantity.getText().toString());
                if (childQuantity != 0) {
                    tvChildQuantity.setText(String.valueOf(childQuantity - 1));
                } else {

                }
            }
        });
        btChildplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childQuantity = Integer.parseInt(tvChildQuantity.getText().toString());
                tvChildQuantity.setText(String.valueOf(childQuantity + 1));
            }
        });

        //使用Spinner
        ArrayAdapter spinnerAadapter = ArrayAdapter.createFromResource(getActivity().getApplication(),
                R.array.AgeArray, R.layout.spinner_style_booking);
        spinnerAadapter
                .setDropDownViewResource(R.layout.spinner_style_booking);
        spChildAge.setAdapter(spinnerAadapter);

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
