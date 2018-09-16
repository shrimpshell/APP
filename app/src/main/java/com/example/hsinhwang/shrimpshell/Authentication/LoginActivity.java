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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.LogIn;
import com.example.hsinhwang.shrimpshell.Classes.MyTask;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        //判斷Customer是否已登入
        SharedPreferences prefC = getSharedPreferences(Common.PREF_CUSTOMER, MODE_PRIVATE);
        boolean login = prefC.getBoolean("login", false);
        if (login) {
            String user = prefC.getString("user", "");
            String password = prefC.getString("password", "");
            if (LogIn.isCustomerLogIn(LoginActivity.this, user, password)) {
                setResult(RESULT_OK);
                finish();
            }
        }

        //判斷Employee是否已登入
        SharedPreferences prefE = getSharedPreferences(Common.PREF_Employee, MODE_PRIVATE);
        boolean logIn = prefE.getBoolean("login", false);
        if (logIn) {
            String user = prefE.getString("employeeCode", "");
            String password = prefE.getString("password", "");
            if (LogIn.isEmployeeLogIn(LoginActivity.this, user, password)) {
                setResult(3);
                finish();
            }
        }

    }

    //LogIn Button
    private Button.OnClickListener btLogInListener = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {
            String user = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            //當帳號或密碼長度小於0，跳出警示訊息
            if (user.length() <= 0 || password.length() <= 0) {
                showMessage();
                return;
            }

            switch (rgLogin.getCheckedRadioButtonId()) {

                //選擇Customer登入時
                case R.id.rbCustomer:
                    if (LogIn.isCustomerLogIn(LoginActivity.this, user, password)) {
                        SharedPreferences pref = getSharedPreferences(Common.PREF_CUSTOMER,
                                MODE_PRIVATE);
                        pref.edit()
                                .putBoolean("login", true)
                                .putInt("IdCustomer",Integer.valueOf(IdCustomer))
                                .putString("user", user)
                                .putString("password", password)
//                        .putString("gender", gender)
//                        .putString("birthday", birthday)
//                        .putString("phoneNo", phoneNo)
//                        .putString("address", address)
                                .apply();
                        setResult(RESULT_OK);
                        Log.e(TAG, String.valueOf(IdCustomer));
                        finish();
                    } else {
                        showMessage();
                    }
                    break;

                //選擇Employee登入時
                case R.id.rbEmployee:
                    if (LogIn.isEmployeeLogIn(LoginActivity.this, user, password)) {
                        SharedPreferences pref = getSharedPreferences(Common.PREF_Employee,
                                MODE_PRIVATE);
                        pref.edit()
                                .putBoolean("login", true)
                                .putInt("idEmployee", Integer.parseInt(idEmployee))
                                .putString("employeeCode", user)
                                .putString("password", password)
//                        .putString("gender", gender)
//                        .putString("birthday", birthday)
//                        .putString("phoneNo", phoneNo)
//                        .putString("address", address)
                                .apply();
                        setResult(3);
                        finish();
                    } else {
                        showMessage();
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
                    Intent intentE = new Intent(context, AddEmployeeActivity.class);
                    startActivity(intentE);
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


    //以下無使用，method於Class資料夾中的LogIn

//    private boolean isLogIn (String user, String password){
//        boolean isLogin = false;
//        if (Common.networkConnected(LoginActivity.this)){
//        String url = Common.URL + "/CustomerServlet";
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("action", "customerLogIn");
//        jsonObject.addProperty("user", user);
//        jsonObject.addProperty("password", password);
//        String jsonOut = jsonObject.toString();
//        loginTask = new CommonTask(url, jsonOut);
//            try {
//                String result = loginTask.execute().get();
//                if(result == null) {
//                    Common.showToast(LoginActivity.this, R.string.msg_NoProfileFound);
//                } else {
//                    isLogin = true;
//                    idCustomer = result;
//                }
//            } catch (Exception e) {
//                Log.e(TAG, e.toString());
//                isLogin = false;
//            }
//        } else {
//            Common.showToast(this, R.string.msg_NoNetwork);
//        }
//        return isLogin;
//
//    }
}

