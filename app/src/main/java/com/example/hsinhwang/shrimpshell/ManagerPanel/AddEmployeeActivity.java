package com.example.hsinhwang.shrimpshell.ManagerPanel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsinhwang.shrimpshell.R;

import java.util.UUID;

public class AddEmployeeActivity extends AppCompatActivity {
    private TextView employeeAddCode;
    private RadioGroup employeeAddGenderGroup;
    private EditText etEmployeeAddName, etEmployeeAddEmail, etEmployeeAddPass, etEmployeeAddPhone, etEmployeeAddAddress;
    private Button btnEmployeeAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        initialization();
    }

    @Override
    protected void onStart() {
        super.onStart();
        assignEmployeeCode();
    }

    private void initialization() {
        employeeAddCode = findViewById(R.id.employeeAddCode);
        employeeAddGenderGroup = findViewById(R.id.employeeAddGenderGroup);
        etEmployeeAddName = findViewById(R.id.etEmployeeAddName);
        etEmployeeAddEmail = findViewById(R.id.etEmployeeAddEmail);
        etEmployeeAddPass = findViewById(R.id.etEmployeeAddPass);
        etEmployeeAddPhone = findViewById(R.id.etEmployeeAddPhone);
        etEmployeeAddAddress = findViewById(R.id.etEmployeeAddAddress);
        btnEmployeeAdd = findViewById(R.id.btnEmployeeAdd);
        btnEmployeeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int gender = employeeAddGenderGroup.getCheckedRadioButtonId();

                switch (gender) {
                    case R.id.Male:
                        Toast.makeText(AddEmployeeActivity.this, "男", Toast.LENGTH_SHORT);
                    case R.id.Female:
                        Toast.makeText(AddEmployeeActivity.this, "女", Toast.LENGTH_SHORT);
                    default:
                        Toast.makeText(AddEmployeeActivity.this, "請選擇性別", Toast.LENGTH_SHORT);
                }

                // 存資料庫
                finish();

            }
        });
    }

    private void assignEmployeeCode() {
        String codeTitle = (String) getText(R.string.employeeCode);
        employeeAddCode.setText(codeTitle + "：" + UUID.randomUUID());
        etEmployeeAddPass.setText("" + UUID.randomUUID());
    }

}
