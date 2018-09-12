package com.example.hsinhwang.shrimpshell.InstantPanel;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.EmployeeInstantCall;
import com.example.hsinhwang.shrimpshell.R;

import java.util.ArrayList;
import java.util.List;

public class EmployeeInstantCallActivity extends AppCompatActivity {
    private RecyclerView rvEmployeeInstantCall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_instant_service_call);

        handlerView();
    }

    private void handlerView() {
        rvEmployeeInstantCall = findViewById(R.id.rvEmployeeInstantCall);
        rvEmployeeInstantCall.setLayoutManager(new LinearLayoutManager(this));

        List<EmployeeInstantCall> employeeInstantCalls = getEmployeeInstantCall();

        rvEmployeeInstantCall.setAdapter
                (new EmployeeInstantCallAdapter(this,employeeInstantCalls));

    }











    private class EmployeeInstantCallAdapter extends
            RecyclerView.Adapter<EmployeeInstantCallAdapter.MyViewHolder> {
        Context context;
        List<EmployeeInstantCall> employeeInstantCalls;

        public EmployeeInstantCallAdapter
                (Context context, List<EmployeeInstantCall> employeeInstantCalls) {
            this.context = context;
            this.employeeInstantCalls = employeeInstantCalls;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            LinearLayout layout_call;
            TextView tvCall1,tvCall2;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.tvStatusCall);
                layout_call = itemView.findViewById(R.id.layout_call);
                tvCall1 = itemView.findViewById(R.id.tvRoomIdCall);
                tvCall2 = itemView.findViewById(R.id.tvStatusCall);

            }
        }
        @Override
        public int getItemCount() {
            return employeeInstantCalls.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(context).
                    inflate(R.layout.item_status_employee_instant_call, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
            final EmployeeInstantCall employeeInstantCall = employeeInstantCalls.get(position);

            myViewHolder.imageView.setImageResource(employeeInstantCall.getImage());
            myViewHolder.tvCall1.setText(employeeInstantCall.getTvRoomId());
            myViewHolder.tvCall2.setText(employeeInstantCall.getTvState());

            myViewHolder.layout_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });

        }




    }

    private List<EmployeeInstantCall> getEmployeeInstantCall() {
        List<EmployeeInstantCall> employeeInstantCalls = new ArrayList<>();

        return employeeInstantCalls;
    }

}
