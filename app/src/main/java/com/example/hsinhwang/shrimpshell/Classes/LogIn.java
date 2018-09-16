package com.example.hsinhwang.shrimpshell.Classes;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.JsonObject;

public class LogIn {
    private static final String TAG = "LogIn";
    private static CommonTask loginTask;
    static String IdCustomer = null;
    static String idEmployee = null;

    public static boolean CustomerLogIn(SharedPreferences pref){
        boolean customerlogin = pref.getBoolean("login", false);
        
        return customerlogin;
    }

    public static boolean EmployeeLogIn(SharedPreferences pref) {
        boolean employeelogin = pref.getBoolean("login", false);
        return employeelogin;
    }

    public static boolean isCustomerLogIn (Activity activity, String user, String password){
        boolean isLogin = false;
            if (Common.networkConnected(activity)) {
                String url = Common.URL + "/CustomerServlet";
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "userValid");
                jsonObject.addProperty("customerID", user);
                jsonObject.addProperty("password", password);
                String jsonOut = jsonObject.toString();
                loginTask = new CommonTask(url, jsonOut);
                try {
                    String result = loginTask.execute().get();
                    if (result == null) {
                        Common.showToast(activity, R.string.msg_NoProfileFound);
                    } else {
                        isLogin = true;
                        LogIn.IdCustomer = result;
                        Log.e(TAG, result);
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                    isLogin = false;
                }
            } else {
                Common.showToast(activity, R.string.msg_NoNetwork);
            }
            return isLogin;
        }


    public static boolean isEmployeeLogIn(Activity activity,String employeeCode, String password){
        boolean isLogin = false;
        if (Common.networkConnected(activity)){
            String url = Common.URL + "/EmployeeServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "employeeValid");
            jsonObject.addProperty("employeeCode", employeeCode);
            jsonObject.addProperty("password", password);
            String jsonOut = jsonObject.toString();
            loginTask = new CommonTask(url, jsonOut);
            try {
                String result = loginTask.execute().get();
                if(result == null) {
                    Common.showToast(activity, R.string.msg_NoProfileFound);
                } else {
                    isLogin = true;
                    idEmployee = result;
                }
            } catch (Exception e) {
//                Log.e(TAG, e.toString());
                isLogin = false;
            }
        } else {
            Common.showToast(activity, R.string.msg_NoNetwork);
        }
        return isLogin;

    }



}
