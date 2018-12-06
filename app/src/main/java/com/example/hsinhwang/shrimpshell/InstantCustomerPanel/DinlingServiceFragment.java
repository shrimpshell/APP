package com.example.hsinhwang.shrimpshell.InstantCustomerPanel;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsinhwang.shrimpshell.Classes.ChatMessage;
import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.DinlingServiceMsg;
import com.example.hsinhwang.shrimpshell.Classes.Instant;
import com.example.hsinhwang.shrimpshell.Classes.OrderRoomDetail;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;
import static com.example.hsinhwang.shrimpshell.Classes.Common.chatwebSocketClient;


public class DinlingServiceFragment extends Fragment {
    private String TAG = "Debug";
    FragmentActivity activity;
    private RecyclerView rvDinlingService;
    SharedPreferences preferences;
    private String customerName;
    String roomNumber;
    int idRoomStatus;
    private CommonTask userRoomNumber;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dinling_service_fab,
                container, false);

        activity = getActivity();

        preferences = getActivity().getSharedPreferences(Common.LOGIN, MODE_PRIVATE);
        customerName = preferences.getString("email", "");


        handleViews(view);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        List<OrderRoomDetail> orderRoomDetails = null;

        if (chatwebSocketClient == null) {
            Common.connectServer(activity, customerName, "0");
        }

        int idCustomer = preferences.getInt("IdCustomer", 0);
        String id = String.valueOf(idCustomer);
        if (idCustomer == 0){
            Common.showToast(activity, R.string.msg_NoProfileFound);
        }
        
        if (Common.networkConnected(activity)) {
            String url = Common.URL + "/PayDetailServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getUserRoomNumber");
            jsonObject.addProperty("idCustomer", id);
            String jsonOut = jsonObject.toString();
            userRoomNumber = new CommonTask(url, jsonOut);
            try {
                String jsonIn = userRoomNumber.execute().get();
                Type listType = new TypeToken<List<OrderRoomDetail>>() {
                }.getType();
                orderRoomDetails = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Common.showToast(activity, R.string.msg_NoNetwork);
        }
        for (OrderRoomDetail detail : orderRoomDetails) {
            if (detail.getRoomReservationStatus().equals("1")) {
                if (roomNumber == null || roomNumber.isEmpty()) {
                    roomNumber = "0";
                }
                roomNumber = detail.getRoomNumber();
                idRoomStatus = detail.getIdRoomStatus();
                Log.d(TAG,roomNumber);

            }
        }
    }

    private void handleViews(View view) {
        rvDinlingService = view.findViewById(R.id.rvDinlingService);
        rvDinlingService.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<DinlingServiceMsg> dinlingServiceMsgs = getDinlingServicesMsgs();
        rvDinlingService.setAdapter(new DinlingServiceMsgAdapter
                (getActivity(), dinlingServiceMsgs));


    }


    private class DinlingServiceMsgAdapter extends
            RecyclerView.Adapter<DinlingServiceMsgAdapter.MyViewHolder> {
        private Context context;
        private List<DinlingServiceMsg> dinlingServiceMsgs;


        private DinlingServiceMsgAdapter(Context context,
                                         List<DinlingServiceMsg> dinlingServiceMsgs) {
            this.context = context;
            this.dinlingServiceMsgs = dinlingServiceMsgs;


        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            LinearLayout layout_dinling;
            TextView tvDinling1, tvDinling2;
            EditText etDinling;
            Button btDinling;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.ivDinling);
                layout_dinling = itemView.findViewById(R.id.layout_dinling);
                tvDinling1 = itemView.findViewById(R.id.tvDinling1);
                tvDinling2 = itemView.findViewById(R.id.tvDinling2);
                etDinling = itemView.findViewById(R.id.etDinling);
                btDinling = itemView.findViewById(R.id.btDinling);


            }
        }


        @Override
        public int getItemCount() {
            return dinlingServiceMsgs.size();

        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(context).
                    inflate(R.layout.item_dinling_service_fab, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
            final DinlingServiceMsg dinlingServiceMsg = dinlingServiceMsgs.get(position);

            myViewHolder.imageView.setImageResource(dinlingServiceMsg.getImage());
            myViewHolder.tvDinling1.setText(dinlingServiceMsg.getTvDinling1());
            myViewHolder.tvDinling2.setText(dinlingServiceMsg.getTvDinglin2());
            myViewHolder.etDinling.setVisibility(View.GONE);
            myViewHolder.btDinling.setVisibility(View.GONE);


            myViewHolder.layout_dinling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (dinlingServiceMsg.getNumber()) {
                        case 0:
                            dinlingServiceMsgs.add(new DinlingServiceMsg
                                    ("商品：A餐", "價格：300 元",
                                            R.drawable.icon_dinling_a, 1));
                            dinlingServiceMsgs.add(new DinlingServiceMsg
                                    ("商品：B餐", "價格：200 元",
                                            R.drawable.icon_dinling_b, 2));
                            dinlingServiceMsgs.add(new DinlingServiceMsg
                                    ("商品：C餐", "價格：100 元",
                                            R.drawable.icon_dinling_c, 3));

                            myViewHolder.layout_dinling.setOnClickListener(null);

                            notifyItemInserted(dinlingServiceMsgs.size());

                            break;

                        case 1:


                            if (myViewHolder.btDinling.getVisibility() != View.VISIBLE ||
                                    myViewHolder.etDinling.getVisibility() != View.VISIBLE) {

                                myViewHolder.etDinling.setVisibility(View.VISIBLE);
                                myViewHolder.btDinling.setVisibility(View.VISIBLE);

                            } else if (myViewHolder.btDinling.getVisibility() != View.GONE ||
                                    myViewHolder.etDinling.getVisibility() != View.GONE) {

                                myViewHolder.etDinling.setVisibility(View.GONE);
                                myViewHolder.btDinling.setVisibility(View.GONE);

                            }
                            break;

                        case 2:


                            if (myViewHolder.btDinling.getVisibility() != View.VISIBLE ||
                                    myViewHolder.etDinling.getVisibility() != View.VISIBLE) {

                                myViewHolder.etDinling.setVisibility(View.VISIBLE);
                                myViewHolder.btDinling.setVisibility(View.VISIBLE);

                            } else if (myViewHolder.btDinling.getVisibility() != View.GONE ||
                                    myViewHolder.etDinling.getVisibility() != View.GONE) {

                                myViewHolder.etDinling.setVisibility(View.GONE);
                                myViewHolder.btDinling.setVisibility(View.GONE);

                            }
                            break;

                        case 3:


                            if (myViewHolder.btDinling.getVisibility() != View.VISIBLE ||
                                    myViewHolder.etDinling.getVisibility() != View.VISIBLE) {

                                myViewHolder.etDinling.setVisibility(View.VISIBLE);
                                myViewHolder.btDinling.setVisibility(View.VISIBLE);

                            } else if (myViewHolder.btDinling.getVisibility() != View.GONE ||
                                    myViewHolder.etDinling.getVisibility() != View.GONE) {

                                myViewHolder.etDinling.setVisibility(View.GONE);
                                myViewHolder.btDinling.setVisibility(View.GONE);

                            }
                            break;


                        default:

                            break;

                    }

                    rvDinlingService.scrollToPosition(dinlingServiceMsgs.size() - 1);
                }


            });

            myViewHolder.btDinling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatMessage chatMessage;
                    String chatMessageJson;
                    String UserEnter = myViewHolder.etDinling.getText().toString();


                    switch (dinlingServiceMsg.getNumber()) {

                        case 1:
                            if (UserEnter.equals("")) {

                                Toast.makeText(context, "請點選您需要的數量",
                                        Toast.LENGTH_SHORT).show();


                            } else {


                                Toast.makeText(context, "你點的 A餐 數量為" + UserEnter + "份",
                                        Toast.LENGTH_SHORT).show();


                                int idInstantService = 3;
                                int status = 1;
                                int quantity = Integer.parseInt(UserEnter);
                                int idInstantType = 1;
                                int idInstantDetail = 0;
                                if (Common.networkConnected(activity)) {
                                    String url = Common.URL + "/InstantServlet";
                                    Instant instant = new Instant(idInstantDetail, idInstantService, roomNumber, status,
                                            quantity, idInstantType, idRoomStatus);
                                    JsonObject jsonObject = new JsonObject();
                                    jsonObject.addProperty("action", "insertInstant");
                                    jsonObject.addProperty("instant", new Gson().toJson(instant));
                                    try {
                                        String result = new CommonTask(url, jsonObject.toString()).execute().get();
                                        idInstantDetail = Integer.valueOf(result);
                                    } catch (Exception e) {
                                        Log.e(TAG, "Josh :" + e.toString());
                                    }
                                    if (idInstantDetail != 0) {
                                        Common.showToast(activity, R.string.id_InstantSuccess);
                                    } else {
                                        Common.showToast(activity, R.string.id_InstantFail);
                                        myViewHolder.layout_dinling.setEnabled(false);
                                    }
                                } else {
                                    Common.showToast(activity, R.string.msg_NoNetwork);
                                }


                                chatMessage =
                                        new ChatMessage(customerName, "0", "0",
                                                "3", 3, idInstantDetail);
                                chatMessageJson = new Gson().toJson(chatMessage);
                                chatwebSocketClient.send(chatMessageJson);
                                Log.d(TAG, "output: " + chatMessageJson);

                                myViewHolder.layout_dinling.setEnabled(true);

                                myViewHolder.etDinling.setText("");

                            }

                            break;

                        case 2:
                            if (UserEnter.equals("")) {

                                Toast.makeText(context, "請點選您需要的數量",
                                        Toast.LENGTH_SHORT).show();

                            } else {


                                Toast.makeText(context, "你點的 B餐 數量為" + UserEnter + "份",
                                        Toast.LENGTH_SHORT).show();


                                int idInstantService = 3;
                                int status = 1;
                                int quantity = Integer.parseInt(UserEnter);
                                int idInstantType = 2;
                                int idInstantDetail = 0;
                                if (Common.networkConnected(activity)) {
                                    String url = Common.URL + "/InstantServlet";
                                    Instant instant = new Instant(idInstantDetail, idInstantService, roomNumber, status,
                                            quantity, idInstantType, idRoomStatus);
                                    JsonObject jsonObject = new JsonObject();
                                    jsonObject.addProperty("action", "insertInstant");
                                    jsonObject.addProperty("instant", new Gson().toJson(instant));
                                    try {
                                        String result = new CommonTask(url, jsonObject.toString()).execute().get();
                                        idInstantDetail = Integer.valueOf(result);
                                    } catch (Exception e) {
                                        Log.e(TAG, "Josh :" + e.toString());
                                    }
                                    if (idInstantDetail != 0) {
                                        Common.showToast(activity, R.string.id_InstantSuccess);
                                    } else {
                                        Common.showToast(activity, R.string.id_InstantFail);
                                        myViewHolder.layout_dinling.setEnabled(false);
                                    }
                                } else {
                                    Common.showToast(activity, R.string.msg_NoNetwork);
                                }


                                chatMessage =
                                        new ChatMessage(customerName, "0", "0",
                                                "3", 3, idInstantDetail);
                                chatMessageJson = new Gson().toJson(chatMessage);
                                chatwebSocketClient.send(chatMessageJson);
                                Log.d(TAG, "output: " + chatMessageJson);

                                myViewHolder.layout_dinling.setEnabled(true);

                                myViewHolder.etDinling.setText("");


                            }
                            break;

                        case 3:
                            if (UserEnter.equals("")) {

                                Toast.makeText(context, "請點選您需要的數量",
                                        Toast.LENGTH_SHORT).show();

                            } else {


                                Toast.makeText(context, "你點的 C餐 數量為" + UserEnter + "份",
                                        Toast.LENGTH_SHORT).show();

                                int idInstantService = 3;
                                int status = 1;
                                int quantity = Integer.parseInt(UserEnter);
                                int idInstantType = 3;
                                int idInstantDetail = 0;
                                if (Common.networkConnected(activity)) {
                                    String url = Common.URL + "/InstantServlet";
                                    Instant instant = new Instant(idInstantDetail, idInstantService, roomNumber, status,
                                            quantity, idInstantType, idRoomStatus);
                                    JsonObject jsonObject = new JsonObject();
                                    jsonObject.addProperty("action", "insertInstant");
                                    jsonObject.addProperty("instant", new Gson().toJson(instant));
                                    try {
                                        String result = new CommonTask(url, jsonObject.toString()).execute().get();
                                        idInstantDetail = Integer.valueOf(result);
                                    } catch (Exception e) {
                                        Log.e(TAG,"Josh :" + e.toString());
                                    }
                                    if (idInstantDetail != 0) {
                                        Common.showToast(activity, R.string.id_InstantSuccess);
                                    } else {
                                        Common.showToast(activity, R.string.id_InstantFail);
                                        myViewHolder.layout_dinling.setEnabled(false);
                                    }
                                } else {
                                    Common.showToast(activity, R.string.msg_NoNetwork);
                                }


                                chatMessage =
                                        new ChatMessage(customerName, "0", "0",
                                                "3", 3, idInstantDetail);
                                chatMessageJson = new Gson().toJson(chatMessage);
                                chatwebSocketClient.send(chatMessageJson);
                                Log.d(TAG, "output: " + chatMessageJson);

                                myViewHolder.layout_dinling.setEnabled(true);

                                myViewHolder.etDinling.setText("");


                            }

                            break;

                        default:
                            break;


                    }

                }

            });

        }
    }


    public List<DinlingServiceMsg> getDinlingServicesMsgs() {
        List<DinlingServiceMsg> dinlingServiceMsgs = new ArrayList<>();
        dinlingServiceMsgs.add(new DinlingServiceMsg
                ("您好!需要什麼餐點?", "點我菜單",
                        R.drawable.icon_dinling, 0));


        return dinlingServiceMsgs;
    }


}
