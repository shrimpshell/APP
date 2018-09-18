package com.example.hsinhwang.shrimpshell.Authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.LogIn;
import com.example.hsinhwang.shrimpshell.MainActivity;
import com.example.hsinhwang.shrimpshell.ManagerPanel.AddEmployeeActivity;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginDialogActivity";
    private Window window;
    private EditText etEmail;
    private EditText etPassword;
    private Context context;
    private Button btLogIn, btJoin;
    private CommonTask loginTask;
    private RadioGroup rgLogin;
    private RadioButton rbCustomer, rbEmployee;
    int IdCustomer = 0;
    String idEmployee = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        setResult(RESULT_CANCELED);
        initialization();
        btLogIn.setOnClickListener(btLogInListener);
        btJoin.setOnClickListener(btJoinListener);
        rbCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btJoin.setEnabled(true);
            }
        });
        rbEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btJoin.setEnabled(false);
            }
        });
    }

    //LogIn Button
    private Button.OnClickListener btLogInListener = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {
            String uid = etEmail.getText().toString();
            String pw = etPassword.getText().toString();
            int selectedRole = rgLogin.getCheckedRadioButtonId();
            switch (selectedRole) {
                case R.id.rbCustomer:
                    if (LogIn.CustomerLogIn(LoginActivity.this, uid, pw)) {
                        SharedPreferences preferences = getSharedPreferences(
                                Common.LOGIN, MODE_PRIVATE);
                        preferences.edit().putBoolean("login", true)
                                .putString("email", uid)
                                .putString("password", pw).apply();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("SS Hotel")
                                .setMessage("登入失敗")
                                .setPositiveButton("OK", null)
                                .show();
                    }
                    break;
                case R.id.rbEmployee:
                    if (LogIn.EmployeeLogIn(LoginActivity.this, uid, pw)) {
                        SharedPreferences preferences = getSharedPreferences(
                                Common.EMPLOYEE_LOGIN, MODE_PRIVATE);
                        preferences.edit().putBoolean("login", true)
                                .putString("email", uid)
                                .putString("password", pw).apply();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("SS Hotel")
                                .setMessage("登入失敗")
                                .setPositiveButton("OK", null)
                                .show();
                    }
                    break;
            }

        }
    };

    // Join Button
    private Button.OnClickListener btJoinListener = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {

            switch (rgLogin.getCheckedRadioButtonId()) {
                //轉換頁面至Customer註冊頁面
                case R.id.rbCustomer:
                    Intent intentC = new Intent(context, JoinActivity.class);
                    startActivity(intentC);
                    break;

                //轉換頁面至Employee註冊頁面
                case R.id.rbEmployee:
                    break;
            }

        }
    };

    private void initialization() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.setStatusBarColor(Color.parseColor("#01728B"));
        }
    }

    private void findViews() {
        rbCustomer = findViewById(R.id.rbCustomer);
        rbEmployee = findViewById(R.id.rbEmployee);
        btLogIn = (Button) findViewById(R.id.btLogIn);
        btJoin = (Button) findViewById(R.id.btJoin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogIn.setOnClickListener(btLogInListener);
        btJoin.setOnClickListener(btJoinListener);
        rgLogin = (RadioGroup) findViewById(R.id.rgLogin);
        context = LoginActivity.this;
    }


    @Override
    public void onStop() {
        super.onStop();
        if (loginTask != null) {
            loginTask.cancel(true);
        }
    }

    //登入失敗警示訊息
    private void showMessage() {
        new AlertDialog.Builder(context)
                .setTitle("SS Hotel")
                .setMessage("登入失敗")
                .setPositiveButton("OK", null)
                .show();
    }

}

