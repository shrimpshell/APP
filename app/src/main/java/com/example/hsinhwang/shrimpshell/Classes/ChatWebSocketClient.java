package com.example.hsinhwang.shrimpshell.Classes;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import static android.content.Context.NOTIFICATION_SERVICE;
import com.example.hsinhwang.shrimpshell.InstantCustomerPanel.StatusServiceFragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.util.Locale;



public class ChatWebSocketClient extends WebSocketClient {
    private static final String TAG = "ChatWebSocketClient";
    private LocalBroadcastManager broadcastManager;
    private Context context;
    private Gson gson;

    public ChatWebSocketClient(URI serverUri, Context context) {
        super(serverUri, new Draft_6455());
        broadcastManager = LocalBroadcastManager.getInstance(context);
        gson = new Gson();

    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        String text = String.format(Locale.getDefault(),
                "onOpen: Http status code = %d; status message = %s",
                handshakeData.getHttpStatus(),
                handshakeData.getHttpStatusMessage());
        Log.d(TAG, "onOpen: " + text);

    }

    @Override
    public void onMessage(String message) {
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        //serviceId  clean = 1, room = 2, dinling = 3
        String serviceId = jsonObject.get("serviceId").getAsString();
        sendMessageBroadcast(serviceId, message);
        Log.d(TAG, "onMessage: " + message);

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        String text = String.format(Locale.getDefault(),
                "code = %d, reason = %s, remote = %b",
                code, reason, remote);
        Log.d(TAG, "onClose: " + text);

    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG, "onError: exception = " + ex.toString());

    }

    private void sendMessageBroadcast(String serviceId, String message) {
        Intent intent = new Intent(serviceId);
        intent.putExtra("message", message);
        broadcastManager.sendBroadcast(intent);
    }

    private void showNotification(ChatMessage chatMessage) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // API 26開始要加上channel設定
        String channelId = "channel_id_01";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "channel_01";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Intent intent = new Intent(context, StatusServiceFragment.class);
        Bundle bundle = new Bundle();
        bundle.putString("SenderGroupId", chatMessage.getSenderId());
        bundle.putInt("ServiceId", chatMessage.getServiceId());
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(context,channelId)
                .setContentTitle("message from" + chatMessage.getSenderGroupId())
                .setContentText(String.valueOf(chatMessage.getServiceId()))
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        if (notificationManager != null) {
            notificationManager.notify(0, notification);
        }
    }


}
