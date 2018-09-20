package com.example.hsinhwang.shrimpshell.Classes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.WatchService;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class Common {
    public static final int REQ_EXTERNAL_STORAGE = 0;
    private static final String TAG = "Common";
    public static final String SERVER_URI =
            "ws://10.0.2.2:8080/ShellService/WsServer/"; //socket
    //    public static String URL = "http://192.168.50.46:8080/ShellService/"; // 手機用
    public static final String URL = "http://10.0.2.2:8080/ShellService"; // 模擬機用
    private static SharedPreferences preferences;
    public static ChatWebSocketClient chatwebSocketClient;

    public static boolean networkConnected(Activity activity) {
        ConnectivityManager conManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager != null ? conManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }

    public static Bitmap downSize(Bitmap srcBitmap, int newSize) {
        if (newSize <= 20) {
            // 如果欲縮小的尺寸過小，就直接定為128
            newSize = 512;
        }
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        String text = "source image size = " + srcWidth + "x" + srcHeight;
        Log.d(TAG, text);
        int longer = Math.max(srcWidth, srcHeight);

        if (longer > newSize) {
            double scale = longer / (double) newSize;
            int dstWidth = (int) (srcWidth / scale);
            int dstHeight = (int) (srcHeight / scale);
            srcBitmap = Bitmap.createScaledBitmap(srcBitmap, dstWidth, dstHeight, false);
            System.gc();
            text = "\nscale = " + scale + "\nscaled image size = " +
                    srcBitmap.getWidth() + "x" + srcBitmap.getHeight();
            Log.d(TAG, text);
        }
        return srcBitmap;
    }

    // New Permission see Appendix A
    public static void askPermissions(Activity activity, String[] permissions, int requestCode) {
        Set<String> permissionsRequest = new HashSet<>();
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(activity, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionsRequest.add(permission);
            }
        }

        if (!permissionsRequest.isEmpty()) {
            ActivityCompat.requestPermissions(activity,
                    permissionsRequest.toArray(new String[permissionsRequest.size()]),
                    requestCode);
        }
    }

    public static void showToast(Context context, int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    //儲存客戶和商家點擊之即時資訊
    public static void setInstantMessage(Context context, String state, String type) {
        preferences = context.getSharedPreferences("state", MODE_PRIVATE);
        preferences.edit().putString(state, type).apply();


    }

    //讀取客戶和商家點擊之即時資訊
    public static String getInstantMessage(Context context, String state) {
        preferences = context.getSharedPreferences("state", MODE_PRIVATE);
        state = preferences.getString(state, "");

        return state;
    }


    // 建立WebSocket連線
    public static void connectServer(Context context, String userId, String groupId) {
        URI uri = null;
        try {
            uri = new URI(SERVER_URI + userId + "/" + groupId);

        } catch (URISyntaxException e) {
            Log.e(TAG, e.toString());
        }
        if (chatwebSocketClient == null) {
            chatwebSocketClient = new ChatWebSocketClient(uri, context);
            chatwebSocketClient.connect();
        }
    }

    // 中斷WebSocket連線
    public static void disconnectServer() {
        if (chatwebSocketClient != null) {
            chatwebSocketClient.close();
            chatwebSocketClient = null;
        }
    }
}