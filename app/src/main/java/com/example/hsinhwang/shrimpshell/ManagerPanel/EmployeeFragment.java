package com.example.hsinhwang.shrimpshell.ManagerPanel;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsinhwang.shrimpshell.Classes.Employees;
import com.example.hsinhwang.shrimpshell.Classes.Events;
import com.example.hsinhwang.shrimpshell.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EmployeeFragment extends Fragment {
    private RecyclerView employeeFragmentRecyclerView;
    private RelativeLayout employeeLayout;
    private ImageView employeeImageView;
    private TextView employeeName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee, container, false);
        employeeFragmentRecyclerView = view.findViewById(R.id.employeeFragmentRecyclerView);
        List<Employees> getEmployeeList = new ArrayList<>();
        getEmployeeList.add(new Employees("黃信",UUID.randomUUID() + "", "employee1@email.com", UUID.randomUUID() + "", "MALE", "02-21345678", "address1"));
        getEmployeeList.add(new Employees("李若歆",UUID.randomUUID() + "", "employee2@email.com", UUID.randomUUID() + "", "FEMALE", "02-27654321", "address2"));
        getEmployeeList.add(new Employees("張慧如",UUID.randomUUID() + "", "employee3@email.com", UUID.randomUUID() + "", "FEMALE", "02-25678134", "address3"));
        getEmployeeList.add(new Employees("謝發旭",UUID.randomUUID() + "", "employee4@email.com", UUID.randomUUID() + "", "MALE", "02-24316587", "address4"));
        getEmployeeList.add(new Employees("林弘勳",UUID.randomUUID() + "", "employee5@email.com", UUID.randomUUID() + "", "MALE", "02-23456178", "address5"));
        employeeFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        employeeFragmentRecyclerView.setAdapter(new EmployeeAdapter(inflater, getEmployeeList));
        return view;
    }

    private class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
        private LayoutInflater inflater;
        private List<Employees> innerEmployeeList;

        public EmployeeAdapter(LayoutInflater inflater, List<Employees> innerEmployeeList) {
            this.inflater = inflater;
            this.innerEmployeeList = innerEmployeeList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.item_employee, viewGroup, false);
            return new EmployeeAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final Employees employee = innerEmployeeList.get(i);
            employeeName.setText(employee.getName());
            employeeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ManagerEmployeeEditActivity.class);
                    Bundle bundle = new Bundle();
                    Employees innerEmployee = new Employees(employee.getName(), employee.getEmployeeCode(), employee.getEmail(), employee.getPassword(), employee.getGender(), employee.getPhone(), employee.getAddress());
                    bundle.putSerializable("employee", innerEmployee);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return innerEmployeeList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                employeeLayout = itemView.findViewById(R.id.employeeLayout);
                employeeImageView = itemView.findViewById(R.id.employeeImageView);
                employeeName = itemView.findViewById(R.id.employeeName);
            }
        }
    }

}

