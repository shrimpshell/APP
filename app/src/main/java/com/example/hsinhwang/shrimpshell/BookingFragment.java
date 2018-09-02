package com.example.hsinhwang.shrimpshell;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.ReservationDate;

import java.util.Calendar;

public class BookingFragment extends Fragment {
    private TextView tvFirstYearSelected, tvFirstDaySelected, tvFirstMonSelected, tvFirstWeekSelected,
            tvLastYearSelected, tvLastDaySelected, tvLastMonSelected, tvLastWeekSelected,
            tvAdultQuantity, tvChildQuantity;
    private ImageButton btAdultMinus, btAdultplus, btChildMinus, btChildplus;
    private Spinner spChildAge;
    private String weekName;
    private LinearLayout loAge;
    private static final String TAG = "Debug";

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
        FloatingActionButton fabBooking = getView().findViewById(R.id.fabBooking);
        fabBooking.setOnClickListener(BookingFragmentChange_Listener);
        tvFirstYearSelected = getView().findViewById(R.id.tvFirstYearSelected);
        tvFirstDaySelected = getView().findViewById(R.id.tvFirstDaySelected);
        tvFirstMonSelected = getView().findViewById(R.id.tvFirstMonSelected);
        tvFirstWeekSelected = getView().findViewById(R.id.tvFirstWeekSelected);
        tvLastYearSelected = getView().findViewById(R.id.tvLastYearSelected);
        tvLastDaySelected = getView().findViewById(R.id.tvLastDaySelected);
        tvLastMonSelected = getView().findViewById(R.id.tvLastMonSelected);
        tvLastWeekSelected = getView().findViewById(R.id.tvLastWeekSelected);
        tvAdultQuantity = getView().findViewById(R.id.tvAdultQuantity);
        tvChildQuantity = getView().findViewById(R.id.tvChildQuantity);
        btAdultMinus = getView().findViewById(R.id.btAdultMinus);
        btAdultplus = getView().findViewById(R.id.btAdultPlus);
        btChildMinus = getView().findViewById(R.id.btChildMinus);
        btChildplus = getView().findViewById(R.id.btChildPlus);
        spChildAge = getView().findViewById(R.id.spChildAge);
        loAge = getView().findViewById(R.id.loAge);

        int childQuantity = Integer.parseInt(tvChildQuantity.getText().toString());
        if (childQuantity == 0) {
            loAge.setVisibility(View.GONE);
        } else {
            loAge.setVisibility(View.VISIBLE);
        }

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
        btAdultplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adultQuantity = Integer.parseInt(tvAdultQuantity.getText().toString());
                tvAdultQuantity.setText(String.valueOf(adultQuantity + 1));
            }
        });

        btChildMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childQuantity = Integer.parseInt(tvChildQuantity.getText().toString());
                if (childQuantity > 0) {
                    tvChildQuantity.setText(String.valueOf(childQuantity - 1));
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
        tvChildQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int childQuantity = Integer.parseInt(tvChildQuantity.getText().toString());
                if (childQuantity > 0) {
                    loAge.setVisibility(View.VISIBLE);
                    Log.d(TAG, "show");
                } else {
                    loAge.setVisibility(View.GONE);
                    Log.d(TAG, "hide");
                }
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
            Intent intent = new Intent(getActivity(), CalendarActivity.class);
            ReservationDate date = new ReservationDate(tvFirstYearSelected, tvFirstDaySelected,
                    tvFirstMonSelected, tvFirstWeekSelected, tvLastYearSelected, tvLastDaySelected,
                    tvLastMonSelected, tvLastWeekSelected);
            Bundle bundle = new Bundle();
            bundle.putSerializable("reservationDate",date);
            startActivity(intent);
        }
    };
}
