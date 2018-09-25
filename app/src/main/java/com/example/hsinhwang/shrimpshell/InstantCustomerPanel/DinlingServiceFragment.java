package com.example.hsinhwang.shrimpshell.InstantCustomerPanel;


import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;
import com.example.hsinhwang.shrimpshell.Classes.ChatMessage;
import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.DinlingServiceMsg;
import com.example.hsinhwang.shrimpshell.Classes.Instant;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import static com.example.hsinhwang.shrimpshell.Classes.Common.chatwebSocketClient;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;


public class DinlingServiceFragment extends Fragment {
    FragmentActivity activity;
    private RecyclerView rvDinlingService;
    SharedPreferences preferences,pref;
    private String customerName;
    String roomNumber;
    int idRoomStatus;


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

        SharedPreferences pref = getActivity().getSharedPreferences(Common.INSTANT_TEST, MODE_PRIVATE);
        if (customerName.equals("cc@gmail.com")) {
            roomNumber = pref.getString("roomNumber1","");
            idRoomStatus = pref.getInt("idRoomStatus1",0);

        } else {
            roomNumber = pref.getString("roomNumber2","");
            idRoomStatus = pref.getInt("idRoomStatus2",0);

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
                                    ("商品：B餐", "價格：250 元",
                                            R.drawable.icon_dinling_b, 2));
                            dinlingServiceMsgs.add(new DinlingServiceMsg
                                    ("商品：C餐", "價格：200 元",
                                            R.drawable.icon_dinling_c, 3));

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
                                int quantity = Integer.parseInt(UserEnter.trim());
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
                                        Log.e(TAG,"Josh :" + e.toString());
                                    }
                                    if (idInstantDetail != 0) {
                                        Common.showToast(activity, R.string.id_InstantSuccess);
                                    } else {
                                        Common.showToast(activity, R.string.id_InstantFail);
                                    }
                                } else {
                                    Common.showToast(activity, R.string.msg_NoNetwork);
                                }


                                chatMessage =
                                        new ChatMessage(customerName, "0", "0",
                                                "3",3, idInstantDetail);
                                chatMessageJson = new Gson().toJson(chatMessage);
                                chatwebSocketClient.send(chatMessageJson);
                                Log.d(TAG, "output: " + chatMessageJson);


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




                            }
                            break;

                        case 3:
                            if (UserEnter.equals("")) {

                                Toast.makeText(context, "請點選您需要的數量",
                                        Toast.LENGTH_SHORT).show();

                            } else {


                                Toast.makeText(context, "你點的 C餐 數量為" + UserEnter + "份",
                                        Toast.LENGTH_SHORT).show();






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

    @Override
    public void onStop() {
        super.onStop();
        Common.disconnectServer();
    }
}
