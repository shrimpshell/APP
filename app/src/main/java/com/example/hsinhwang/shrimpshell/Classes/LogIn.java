package com.example.hsinhwang.shrimpshell.Classes;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.JsonObject;

public class LogIn {
    private static final String TAG = "LogIn";
    private static CommonTask loginTask;
    static String idCustomer = null;

    public static boolean CustomerLogIn(SharedPreferences pref){
        boolean login = pref.getBoolean("login", false);
        return login;
    }

    public static boolean EmployeeLogIn(SharedPreferences pref) {
        boolean login = pref.getBoolean("login", false);
        return login;
    }

    public static boolean isLogIn (Activity activity,String user, String password){
        boolean isLogin = false;
        if (Common.networkConnected(activity)){
            String url = Common.URL + "/CustomerServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "customerLogIn");
            jsonObject.addProperty("user", user);
            jsonObject.addProperty("password", password);
            String jsonOut = jsonObject.toString();
            loginTask = new CommonTask(url, jsonOut);
            try {
                String result = loginTask.execute().get();
                if(result == null) {
                    Common.showToast(activity, R.string.msg_NoProfileFound);
                } else {
                    isLogin = true;
                    idCustomer = result;
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


}
