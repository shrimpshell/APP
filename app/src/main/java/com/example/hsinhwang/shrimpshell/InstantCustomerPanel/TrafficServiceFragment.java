package com.example.hsinhwang.shrimpshell.InstantCustomerPanel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.hsinhwang.shrimpshell.Classes.ChatMessage;
import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.Instant;
import com.example.hsinhwang.shrimpshell.Classes.OrderRoomDetail;
import com.example.hsinhwang.shrimpshell.Classes.TrafficServiceMsg;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;
import static com.example.hsinhwang.shrimpshell.Classes.Common.chatwebSocketClient;

public class TrafficServiceFragment extends Fragment {
    RecyclerView rvTrafficService;
    SharedPreferences preferences;
    private String customerName;
    String roomNumber;
    int idRoomStatus;
    FragmentActivity activity;
    private CommonTask userRoomNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traffic_service_fab,
                container, false);

        activity = getActivity();
        preferences = getActivity().getSharedPreferences(Common.LOGIN, MODE_PRIVATE);
        customerName = preferences.getString("email", "");



        handlerView(view);
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
                Log.d(TAG, roomNumber);

            }
        }
    }

    private void handlerView(View view) {
        rvTrafficService = view.findViewById(R.id.rvTrafficService);
        rvTrafficService.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<TrafficServiceMsg> trafficServiceMsgs = getTrafficServiceMsgs();
        rvTrafficService.setAdapter(new TrafficServiceAdpater
                (getActivity(), trafficServiceMsgs));

    }


    private class TrafficServiceAdpater extends
            RecyclerView.Adapter<TrafficServiceAdpater.MyViewHolder> {
        private Context context;
        private List<TrafficServiceMsg> trafficServiceMsgs;

        private TrafficServiceAdpater(Context context,
                                      List<TrafficServiceMsg> trafficServiceMsgs) {
            this.context = context;
            this.trafficServiceMsgs = trafficServiceMsgs;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            LinearLayout layout_traffic;
            TextView tvTraffic1, tvTraffic2;
            EditText etTraffic;
            Button btTraffic;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.ivTraffic);
                layout_traffic = itemView.findViewById(R.id.layout_traffic);
                tvTraffic1 = itemView.findViewById(R.id.tvTraffic1);
                tvTraffic2 = itemView.findViewById(R.id.tvTraffic2);
                etTraffic = itemView.findViewById(R.id.etTraffic);
                btTraffic = itemView.findViewById(R.id.btTraffic);


            }
        }

        @Override
        public int getItemCount() {
            return trafficServiceMsgs.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(context).
                    inflate(R.layout.item_traffic_service_fab, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {
            final TrafficServiceMsg trafficServiceMsg = trafficServiceMsgs.get(position);

            myViewHolder.imageView.setImageResource(trafficServiceMsg.getImage());
            myViewHolder.tvTraffic1.setText(trafficServiceMsg.getTvTraffic1());
            myViewHolder.tvTraffic2.setVisibility(View.GONE);
            myViewHolder.etTraffic.setVisibility(View.GONE);
            myViewHolder.btTraffic.setVisibility(View.GONE);


            myViewHolder.layout_traffic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (trafficServiceMsg.getNumber()) {
                        case 0:
                            trafficServiceMsgs.add(new TrafficServiceMsg
                                    ("高鐵站接駁", R.drawable.icon_traffic_higeway, 1));
                            trafficServiceMsgs.add(new TrafficServiceMsg
                                    ("火車站接駁", R.drawable.icon_traffic_train, 2));
                            trafficServiceMsgs.add(new TrafficServiceMsg
                                    ("機場接駁", R.drawable.icon_traffic_plant, 3));


                            notifyItemInserted(trafficServiceMsgs.size());

                            break;

                        case 1:
                            if (myViewHolder.etTraffic.getVisibility() != View.VISIBLE ||
                                    myViewHolder.btTraffic.getVisibility() != View.VISIBLE ||
                                    myViewHolder.tvTraffic2.getVisibility() != View.VISIBLE) {

                                myViewHolder.etTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.btTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.tvTraffic2.setVisibility(View.VISIBLE);

                            } else if (myViewHolder.etTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.btTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.tvTraffic2.getVisibility() != View.GONE) {

                                myViewHolder.etTraffic.setVisibility(View.GONE);
                                myViewHolder.btTraffic.setVisibility(View.GONE);
                                myViewHolder.tvTraffic2.setVisibility(View.GONE);

                            }
                            break;

                        case 2:
                            if (myViewHolder.etTraffic.getVisibility() != View.VISIBLE ||
                                    myViewHolder.btTraffic.getVisibility() != View.VISIBLE ||
                                    myViewHolder.tvTraffic2.getVisibility() != View.VISIBLE) {

                                myViewHolder.etTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.btTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.tvTraffic2.setVisibility(View.VISIBLE);

                            } else if (myViewHolder.etTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.btTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.tvTraffic2.getVisibility() != View.GONE) {

                                myViewHolder.etTraffic.setVisibility(View.GONE);
                                myViewHolder.btTraffic.setVisibility(View.GONE);
                                myViewHolder.tvTraffic2.setVisibility(View.GONE);

                            }
                            break;


                        case 3:
                            if (myViewHolder.etTraffic.getVisibility() != View.VISIBLE ||
                                    myViewHolder.btTraffic.getVisibility() != View.VISIBLE ||
                                    myViewHolder.tvTraffic2.getVisibility() != View.VISIBLE) {

                                myViewHolder.etTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.btTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.tvTraffic2.setVisibility(View.VISIBLE);

                            } else if (myViewHolder.etTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.btTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.tvTraffic2.getVisibility() != View.GONE) {

                                myViewHolder.etTraffic.setVisibility(View.GONE);
                                myViewHolder.btTraffic.setVisibility(View.GONE);
                                myViewHolder.tvTraffic2.setVisibility(View.GONE);

                            }
                            break;


                        default:

                            break;

                    }

                    rvTrafficService.scrollToPosition(trafficServiceMsgs.size() - 1);

                }
            });

            myViewHolder.btTraffic.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String UserEnterPerson = myViewHolder.etTraffic.getText().toString();
                    ChatMessage chatMessage;
                    String chatMessageJson;
                    switch (trafficServiceMsg.getNumber()) {

                        case 1:

                            if (UserEnterPerson.equals("")) {

                                Toast.makeText(context, "請輸入欲接送人數！", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(context, "接送人數為" + UserEnterPerson +
                                        "人，請稍候！", Toast.LENGTH_SHORT).show();

                                int idInstantService = 2;
                                int status = 1;
                                int quantity = Integer.parseInt(UserEnterPerson);
                                int idInstantType = 6;
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
                                        myViewHolder.layout_traffic.setEnabled(false);
                                    }
                                } else {
                                    Common.showToast(activity, R.string.msg_NoNetwork);
                                }


                                chatMessage =
                                        new ChatMessage(customerName, "0", "0",
                                                "2", 2, idInstantDetail);
                                chatMessageJson = new Gson().toJson(chatMessage);
                                chatwebSocketClient.send(chatMessageJson);
                                Log.d(TAG, "output: " + chatMessageJson);

                                myViewHolder.layout_traffic.setEnabled(true);

                                myViewHolder.etTraffic.setText("");


                            }


                            break;

                        case 2:


                            if (UserEnterPerson.equals("")) {

                                Toast.makeText(context, "請輸入欲接送人數！", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(context, "接送人數為" + UserEnterPerson +
                                        "人，請稍候！", Toast.LENGTH_SHORT).show();

                                int idInstantService = 2;
                                int status = 1;
                                int quantity = Integer.parseInt(UserEnterPerson);
                                int idInstantType = 5;
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
                                        myViewHolder.layout_traffic.setEnabled(false);
                                    }
                                } else {
                                    Common.showToast(activity, R.string.msg_NoNetwork);
                                }


                                chatMessage =
                                        new ChatMessage(customerName, "0", "0",
                                                "2", 2, idInstantDetail);
                                chatMessageJson = new Gson().toJson(chatMessage);
                                chatwebSocketClient.send(chatMessageJson);
                                Log.d(TAG, "output: " + chatMessageJson);

                                myViewHolder.layout_traffic.setEnabled(true);

                                myViewHolder.etTraffic.setText("");



                            }

                            break;

                        case 3:


                            if (UserEnterPerson.equals("")) {

                                Toast.makeText(context, "請輸入欲接送人數！", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(context, "接送人數為" + UserEnterPerson +
                                        "人，請稍候！", Toast.LENGTH_SHORT).show();


                                int idInstantService = 2;
                                int status = 1;
                                int quantity = Integer.parseInt(UserEnterPerson);
                                int idInstantType = 4;
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
                                        myViewHolder.layout_traffic.setEnabled(false);
                                    }
                                } else {
                                    Common.showToast(activity, R.string.msg_NoNetwork);
                                }


                                chatMessage =
                                        new ChatMessage(customerName, "0", "0",
                                                "2", 2, idInstantDetail);
                                chatMessageJson = new Gson().toJson(chatMessage);
                                chatwebSocketClient.send(chatMessageJson);
                                Log.d(TAG, "output: " + chatMessageJson);

                                myViewHolder.layout_traffic.setEnabled(true);

                                myViewHolder.etTraffic.setText("");



                            }

                            break;


                        default:
                            break;


                    }


                }


            });


        }


    }

    private List<TrafficServiceMsg> getTrafficServiceMsgs() {
        List<TrafficServiceMsg> trafficServiceMsgs = new ArrayList<>();
        trafficServiceMsgs.add(new TrafficServiceMsg
                ("您好!需要專車接送嗎?", R.drawable.icon_traffic, 0));

        return trafficServiceMsgs;
    }




}
