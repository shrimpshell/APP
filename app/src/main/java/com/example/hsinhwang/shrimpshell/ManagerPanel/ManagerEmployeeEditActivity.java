package com.example.hsinhwang.shrimpshell.ManagerPanel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.Employees;
import com.example.hsinhwang.shrimpshell.R;

public class ManagerEmployeeEditActivity extends AppCompatActivity {
    private TextView employeeCode, employeeGender;
    private EditText etEmployeeName, etEmployeeEmail, etEmployeePass, etEmployeePhone, etEmployeeAddress;
    private Button btnEmployeeSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_employee_edit);
        initialization();
        loadData();
    }

    private void initialization() {
        employeeCode = findViewById(R.id.employeeCode);
        employeeGender = findViewById(R.id.employeeGender);

        etEmployeeName = findViewById(R.id.etEmployeeName);
        etEmployeeEmail = findViewById(R.id.etEmployeeEmail);
        etEmployeePass = findViewById(R.id.etEmployeePass);
        etEmployeePhone = findViewById(R.id.etEmployeePhone);
        etEmployeeAddress = findViewById(R.id.etEmployeeAddress);

        btnEmployeeSubmit = findViewById(R.id.btnEmployeeSubmit);
    }

    private void loadData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Object obj = bundle.getSerializable("employee");
            if (obj != null) {
                final Employees employee = (Employees) obj;

                employeeCode.setText("員工編號：" + employee.getEmployeeCode());
                employeeGender.setText("性別：" + employee.getGender());

                etEmployeeName.setText(employee.getName());
                etEmployeeEmail.setText(employee.getEmail());
                etEmployeePass.setText(employee.getPassword());
                etEmployeePhone.setText(employee.getPhone());
                etEmployeeAddress.setText(employee.getAddress());

                btnEmployeeSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 儲存到資料庫Employee
                        finish();
                    }
                });
            }
        }
    }
}
