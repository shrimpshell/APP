package com.example.hsinhwang.shrimpshell.ReservationPanel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hsinhwang.shrimpshell.R;

import java.util.List;


public class RoomCheckFragment extends Fragment {
    private RecyclerView rvChecekReservation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_check, container, false);
        handleViews();
        return view;
    }

    private void handleViews() {
    }
}
