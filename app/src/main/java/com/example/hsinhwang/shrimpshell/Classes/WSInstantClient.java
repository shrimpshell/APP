package com.example.hsinhwang.shrimpshell.Classes;



import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Locale;

public class WSInstantClient extends WebSocketClient {
    private static final String TAG = "WSInstantClient";
    private LocalBroadcastManager broadcastManager;
    private Gson gson;

    WSInstantClient(URI serverUri, Context context) {
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
        JsonObject jsonObject = gson.fromJson(message,JsonObject.class);
        String type = jsonObject.get("type").getAsString();
        sendMessageBroadcast(type, message);
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

    private void sendMessageBroadcast(String messageType, String message) {
        Intent intent = new Intent(messageType);
        intent.putExtra("message", message);
        broadcastManager.sendBroadcast(intent);
    }
}
