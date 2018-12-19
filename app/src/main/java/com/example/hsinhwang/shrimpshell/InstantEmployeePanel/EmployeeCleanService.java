package com.example.hsinhwang.shrimpshell.InstantEmployeePanel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.ChatMessage;
import com.example.hsinhwang.shrimpshell.Classes.Common;

import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.EmployeeClean;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.hsinhwang.shrimpshell.Classes.Common.chatwebSocketClient;

public class EmployeeCleanService extends AppCompatActivity {
    private static final String TAG = "EmployeeClean";
    private LocalBroadcastManager broadcastManager;
    RecyclerView rvEmployeeClean;
    List<EmployeeClean> employeeCleans;
    SharedPreferences preferences;
    private CommonTask employeeStatus;
    private String employeeName;
    EmployeeCleanAdapter adapter;
    int idInstantDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_clean_service);
        broadcastManager = LocalBroadcastManager.getInstance(this);
        registerInstantReceiver();

        rvEmployeeClean = findViewById(R.id.rvEmployeeClean);
        rvEmployeeClean.setLayoutManager(new LinearLayoutManager(this));
        employeeCleans = getEmployeeCleanList();

        adapter = new EmployeeCleanAdapter(this, employeeCleans);
        rvEmployeeClean.setAdapter(adapter);

        preferences = getSharedPreferences(Common.EMPLOYEE_LOGIN, MODE_PRIVATE);
        employeeName = preferences.getString("email", "");

        Common.connectServer(this, employeeName, "1");

    }
    private List<EmployeeClean> getEmployeeCleanList() {
        List<EmployeeClean> employeeCleanList = new ArrayList<>();


        return employeeCleanList;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (chatwebSocketClient == null) {
            Common.connectServer(this,employeeName,"1");
        }

        if (Common.networkConnected(this)) {
            String url = Common.URL + "/InstantServlet";
            List<EmployeeClean> employeeCleans = null;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getEmployeeStatus");
            jsonObject.addProperty("idInstantService", 1);
            String jsonOut = jsonObject.toString();
            employeeStatus = new CommonTask(url, jsonOut);
            try {
                String jsonIn = employeeStatus.execute().get();
                Type listType = new TypeToken<List<EmployeeClean>>() {
                }.getType();
                employeeCleans = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (employeeCleans == null || employeeCleans.isEmpty()) {
                Common.showToast(this, R.string.msg_NoInstantFound);
            } else {
                rvEmployeeClean.setAdapter
                        (new EmployeeCleanAdapter(this, employeeCleans));
            }

        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
    }

    private void registerInstantReceiver() {
        IntentFilter cleanFilter = new IntentFilter("1");
        ChatReceiver chatReceiver = new ChatReceiver();
        broadcastManager.registerReceiver(chatReceiver, cleanFilter);

    }


    private class ChatReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
            idInstantDetail = chatMessage.getInstantNumber();
            if (idInstantDetail != 0 ) {

                if (Common.networkConnected(EmployeeCleanService.this)) {
                    String url = Common.URL + "/InstantServlet";
                    List<EmployeeClean> employeeCleans = null;
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("action", "getEmployeeStatus");
                    jsonObject.addProperty("idInstantService", 1);
                    String jsonOut = jsonObject.toString();
                    employeeStatus = new CommonTask(url, jsonOut);
                    try {
                        String jsonIn = employeeStatus.execute().get();
                        Type listType = new TypeToken<List<EmployeeClean>>() {
                        }.getType();
                        employeeCleans = new Gson().fromJson(jsonIn, listType);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                    if (employeeCleans == null || employeeCleans.isEmpty()) {
                        Common.showToast(EmployeeCleanService.this, R.string.msg_NoInstantFound);
                    } else {
                        rvEmployeeClean.setAdapter(null);
                        rvEmployeeClean.setAdapter
                                (new EmployeeCleanAdapter(EmployeeCleanService.this, employeeCleans));
                    }

                } else {
                    Common.showToast(EmployeeCleanService.this, R.string.msg_NoNetwork);
                }

                rvEmployeeClean.getAdapter().notifyDataSetChanged();

            }

        }


    }

    private class EmployeeCleanAdapter extends RecyclerView.Adapter<EmployeeCleanAdapter.MyViewHolder> {
        Context context;
        List<EmployeeClean> employeeCleanList;

        EmployeeCleanAdapter(Context context, List<EmployeeClean> employeeCleanList) {
            this.context = context;
            this.employeeCleanList = employeeCleanList;


        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView tvRoomId, tvType;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.ivEmployeeClean);
                tvRoomId = itemView.findViewById(R.id.tvEmployeeCleanRoomId);
                tvType = itemView.findViewById(R.id.tvEmployeeCleanType);

            }
        }

        @Override
        public int getItemCount() {
            return employeeCleanList.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(context).
                    inflate(R.layout.item_status_employee_instant_clean, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {
            final EmployeeClean employeeClean = employeeCleanList.get(position);

            switch (employeeClean.getStatus()) {
                case 1:
                    myViewHolder.imageView.setImageResource(R.drawable.icon_unfinish);
                    break;
                case 2:
                    myViewHolder.imageView.setImageResource(R.drawable.icon_playing);
                    break;
                case 3:
                    myViewHolder.imageView.setImageResource(R.drawable.icon_finish);
                    break;
                default:
                    break;
            }


            myViewHolder.tvType.setText(R.string.service_type_7);
            myViewHolder.tvRoomId.setText(employeeClean.getRoomNumber());

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int status = 0;
                    String roomNumber = employeeClean.getRoomNumber();
                    idInstantDetail = employeeClean.getIdInstantDetail();


                    if (employeeClean.getStatus() == 1) {

                        status = 2;

                    } else if (employeeClean.getStatus() == 2 || employeeClean.getStatus() == 3) {

                        status = 3;

                    }

                    if (Common.networkConnected(EmployeeCleanService.this)) {
                        String url = Common.URL + "/InstantServlet";
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "updateStatus");
                        jsonObject.addProperty("idInstantDetail", new Gson().toJson(idInstantDetail));
                        jsonObject.addProperty("status", new Gson().toJson(status));
                        int count = 0;
                        try {
                            String result = new CommonTask(url, jsonObject.toString()).execute().get();
                            count = Integer.valueOf(result);
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                        if (count == 0) {
                            Common.showToast(EmployeeCleanService.this, R.string.msg_UpdateFail);
                        } else {
                            Common.showToast(EmployeeCleanService.this, R.string.msg_UpdateSuccess);
                        }
                    } else {
                        Common.showToast(EmployeeCleanService.this, R.string.msg_NoNetwork);
                    }

                    if (Common.networkConnected(EmployeeCleanService.this)) {
                        String url = Common.URL + "/InstantServlet";
                        List<EmployeeClean> employeeCleans = null;
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "getEmployeeStatus");
                        jsonObject.addProperty("idInstantService", 1);
                        String jsonOut = jsonObject.toString();
                        employeeStatus = new CommonTask(url, jsonOut);
                        try {
                            String jsonIn = employeeStatus.execute().get();
                            Type listType = new TypeToken<List<EmployeeClean>>() {
                            }.getType();
                            employeeCleans = new Gson().fromJson(jsonIn, listType);
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                        if (employeeCleans == null || employeeCleans.isEmpty()) {
                            Common.showToast(EmployeeCleanService.this, R.string.msg_NoInstantFound);
                        } else {
                            rvEmployeeClean.setAdapter(null);
                            rvEmployeeClean.setAdapter
                                    (new EmployeeCleanAdapter(EmployeeCleanService.this, employeeCleans));

                            myViewHolder.itemView.setEnabled(false);

                        }

                    } else {
                        Common.showToast(EmployeeCleanService.this, R.string.msg_NoNetwork);
                    }



                    ChatMessage chatMessage =
                            new ChatMessage(employeeName, roomNumber, "1",
                                    "0", 1, idInstantDetail);
                    String chatMessageJson = new Gson().toJson(chatMessage);
                    Common.chatwebSocketClient.send(chatMessageJson);
                    Log.d(TAG, "output: " + chatMessageJson);

                    myViewHolder.itemView.setEnabled(true);

                    adapter.notifyDataSetChanged();




                }
            });


        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Common.disconnectServer();
    }
}
