package com.example.hsinhwang.shrimpshell;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.ReservationDate;
import com.example.hsinhwang.shrimpshell.ReservationPanel.CalendarActivity;
import com.example.hsinhwang.shrimpshell.ReservationPanel.RoomChooseFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import static android.content.Context.MODE_PRIVATE;

public class BookingFragment extends Fragment {
    private TextView tvFirstYearSelected, tvFirstDaySelected, tvFirstMonSelected, tvFirstWeekSelected,
            tvLastYearSelected, tvLastDaySelected, tvLastMonSelected, tvLastWeekSelected,
            tvAdultQuantity, tvChildQuantity;
    private ImageButton ibtAdultMinus, ibtAdultplus, ibtChildMinus, ibtChildplus;
    private String firstday, lastday, weekName, fYear, fMonth, fDay, fWeek, lYear, lMonth, lDay, lWeek;
    private String[] fd, ld;
    private Calendar calendar = Calendar.getInstance();
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private RelativeLayout rlFirstDate, rlLastDate;
    private int year, month, day, week, lastYear, lastMonth, lastDay, lastWeek;
    private static final String TAG = "Debug";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        //如果CalendarActivity有帶值回來，就把日期更新
        if (requestCode == 4 && resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();
            firstday = bundle.getString("firstday");
            lastday = bundle.getString("lastday");
            Log.d("Day", firstday + " " + lastday);
            fd = firstday.split("-");
            fYear = fd[0];
            tvFirstYearSelected.setText(fYear);
            fMonth = fd[1];
            tvFirstMonSelected.setText(" " + fMonth + "月");
            fDay = fd[2];
            tvFirstDaySelected.setText(fDay);
            fWeek = fd[3];
            tvFirstWeekSelected.setText(fWeek);
            ld = lastday.split("-");
            lYear = ld[0];
            tvLastYearSelected.setText(lYear);
            lMonth = ld[1];
            tvLastMonSelected.setText(" " + lMonth + "月");
            lDay = ld[2];
            tvLastDaySelected.setText(lDay);
            lWeek = ld[3];
            tvLastWeekSelected.setText(lWeek);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        handleViews(view);
        return view;
    }

    private void handleViews(View view) {
        rlFirstDate = view.findViewById(R.id.rlFirstDate);
        rlLastDate = view.findViewById(R.id.rlLastDate);
        FloatingActionButton fabBooking = view.findViewById(R.id.fabBooking);
        fabBooking.setOnClickListener(BookingFragmentChange_Listener);
        tvFirstYearSelected = view.findViewById(R.id.tvFirstYearSelected);
        tvFirstDaySelected = view.findViewById(R.id.tvFirstDaySelected);
        tvFirstMonSelected = view.findViewById(R.id.tvFirstMonSelected);
        tvFirstWeekSelected = view.findViewById(R.id.tvFirstWeekSelected);
        tvLastYearSelected = view.findViewById(R.id.tvLastYearSelected);
        tvLastDaySelected = view.findViewById(R.id.tvLastDaySelected);
        tvLastMonSelected = view.findViewById(R.id.tvLastMonSelected);
        tvLastWeekSelected = view.findViewById(R.id.tvLastWeekSelected);
        tvAdultQuantity = view.findViewById(R.id.tvAdultQuantity);
        tvChildQuantity = view.findViewById(R.id.tvChildQuantity);
        ibtAdultMinus = view.findViewById(R.id.ibtAdultMinus);
        ibtAdultplus = view.findViewById(R.id.ibtAdultPlus);
        ibtChildMinus = view.findViewById(R.id.ibtChildMinus);
        ibtChildplus = view.findViewById(R.id.ibtChildPlus);

        //將入住日期和退房日期預設為今天和明天
        showFirstDate();
        showLastDate();

        //點擊進入選擇住房日期
        rlFirstDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                ReservationDate date = new ReservationDate(tvFirstYearSelected.getText().toString(), tvFirstMonSelected.getText().toString(),
                        tvFirstDaySelected.getText().toString(), tvFirstWeekSelected.getText().toString(), tvLastYearSelected.getText().toString(), tvLastMonSelected.getText().toString(),
                        tvLastDaySelected.getText().toString(), tvLastWeekSelected.getText().toString(), tvAdultQuantity.getText().toString(), tvChildQuantity.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putSerializable("reservationDate", date);
                intent.putExtras(bundle);
                startActivityForResult(intent, 4);
            }
        });

        //點擊進入選擇住房日期
        rlLastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                ReservationDate date = new ReservationDate(tvFirstYearSelected.getText().toString(), tvFirstMonSelected.getText().toString(),
                        tvFirstDaySelected.getText().toString(), tvFirstWeekSelected.getText().toString(), tvLastYearSelected.getText().toString(), tvLastMonSelected.getText().toString(),
                        tvLastDaySelected.getText().toString(), tvLastWeekSelected.getText().toString(), tvAdultQuantity.getText().toString(), tvChildQuantity.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putSerializable("reservationDate", date);
                intent.putExtras(bundle);
                startActivityForResult(intent, 4);
            }
        });

        //按下"-"的按鈕，大人的人數減1
        ibtAdultMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adultQuantity = Integer.parseInt(tvAdultQuantity.getText().toString());
                if (adultQuantity > 0) {
                    tvAdultQuantity.setText(String.valueOf(adultQuantity - 1));
                }
            }
        });

        //按下"+"的按鈕，大人的人數加1
        ibtAdultplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adultQuantity = Integer.parseInt(tvAdultQuantity.getText().toString());
                tvAdultQuantity.setText(String.valueOf(adultQuantity + 1));
            }
        });

        //按下"-"的按鈕，孩童的人數減1
        ibtChildMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childQuantity = Integer.parseInt(tvChildQuantity.getText().toString());
                if (childQuantity > 0) {
                    tvChildQuantity.setText(String.valueOf(childQuantity - 1));
                }
            }
        });

        //按下"+"的按鈕，孩童的人數加1
        ibtChildplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childQuantity = Integer.parseInt(tvChildQuantity.getText().toString());
                tvChildQuantity.setText(String.valueOf(childQuantity + 1));
            }
        });
    }

    //取得今天的日期
    private void showFirstDate() {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        week = calendar.get(Calendar.DAY_OF_WEEK);

        fYear = String.valueOf(year);
        fMonth = String.valueOf(month);
        fDay = String.valueOf(day);
        fWeek = String.valueOf(changeWeekName(week));

        //把今天的日期設定到入住日期
        tvFirstYearSelected.setText(String.valueOf(year) + " 年");
        tvFirstDaySelected.setText(String.valueOf(day));
        tvFirstMonSelected.setText(" " + String.valueOf(month) + "月");
        tvFirstWeekSelected.setText(changeWeekName(week));
    }

    //取得明天的日期
    private void showLastDate() {
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        lastYear = calendar.get(Calendar.YEAR);
        lastMonth = calendar.get(Calendar.MONTH) + 1;
        lastDay = calendar.get(Calendar.DAY_OF_MONTH);
        lastWeek = calendar.get(Calendar.DAY_OF_WEEK);

        lYear = String.valueOf(lastYear);
        lMonth = String.valueOf(lastMonth);
        lDay = String.valueOf(lastDay);
        lWeek = String.valueOf(changeWeekName(lastWeek));

        //把明天的日期設定到退房日期
        tvLastYearSelected.setText(String.valueOf(lastYear) + " 年");
        tvLastDaySelected.setText(String.valueOf(lastDay));
        tvLastMonSelected.setText(" " + String.valueOf(lastMonth) + "月");
        tvLastWeekSelected.setText(changeWeekName(lastWeek));
    }

    //把預設星期的格式轉換成"週日"的格式
    public String changeWeekName(int w) {
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

    //設定按下FloatingActionButton按鈕到指定的頁面
    FloatingActionButton.OnClickListener BookingFragmentChange_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //設定要到的頁面
            RoomChooseFragment roomChooseFragment = new RoomChooseFragment();
            Bundle bundle = new Bundle();
            manager = getActivity().getSupportFragmentManager();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.content, roomChooseFragment, "fragment");
            transaction.addToBackStack("fragment");

            //把日期和人數放到bundle帶到下一頁
            String checkInDate = fYear + "-" + fMonth + "-" + fDay;
            String checkOutDate = lYear + "-" + lMonth + "-" + lDay;
            SharedPreferences data = getActivity().getSharedPreferences(Common.RESERVATION_DATA, MODE_PRIVATE);
            data.edit().putString("checkInDate", checkInDate)
                    .putString("checkOutDate", checkOutDate)
                    .putString("adultQuantity", tvAdultQuantity.getText().toString())
                    .putString("childQuantity", tvChildQuantity.getText().toString());
            Log.d(TAG, data.getString("checkInDate", checkInDate));
            Log.d(TAG, data.getString("checkOutDate", checkOutDate));
            Log.d(TAG, data.getString("adultQuantity", tvAdultQuantity.getText().toString()));
            Log.d(TAG, data.getString("childQuantity", tvChildQuantity.getText().toString()));
            bundle.putString("checkInDate", checkInDate);
            bundle.putString("checkOutDate", checkOutDate);
            roomChooseFragment.setArguments(bundle);
            transaction.commit();
        }
    };
}