package com.example.hsinhwang.shrimpshell.EmployeePanel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hsinhwang.shrimpshell.ManagerPanel.ManagerHomeActivity;
import com.example.hsinhwang.shrimpshell.R;

public class EmployeeHomeActivity extends AppCompatActivity {
    private Window window;
    private LinearLayout employHomeBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        initialization();
        insertDepartmentButton();
    }

    private boolean isLoggedIn() {
        // 以後判斷登入用
        return true;
    }

    private void initialization() {
        employHomeBottom = findViewById(R.id.employHomeBottom);
        employHomeBottom.setPadding(5, 5, 5,5);
    }

    /**
     * 部門功能
     */
    private void insertDepartmentButton() {
        // 這裡要判斷部門的種類 用switch case

        if (true) { // 黃信：如果是主管，執行這段程式碼 之後需要用switch case判斷員工編號
            // 設定按鈕尺寸/Margin
            LinearLayoutCompat.LayoutParams param = new LinearLayoutCompat.LayoutParams(240, 240);
            param.leftMargin = 20;
            // 動態生成按鈕
            Button btn = new Button(this);
            btn.setText("工作進度");
            btn.setBackgroundColor(Color.parseColor("#F7DF96"));
            btn.setLayoutParams(param);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(EmployeeHomeActivity.this, ManagerHomeActivity.class);
                    startActivity(intent);
                }
            });
            employHomeBottom.addView(btn);
        }


    }
}
