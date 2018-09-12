package com.example.hsinhwang.shrimpshell.InstantCustomerPanel;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


import com.example.hsinhwang.shrimpshell.Classes.TrafficServiceMsg;
import com.example.hsinhwang.shrimpshell.R;

import java.util.ArrayList;
import java.util.List;

public class TrafficServiceFragment extends Fragment {
    RecyclerView rvTrafficService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traffic_service_fab,
                container, false);

        handlerView(view);
        return view;
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
            TimePicker tpTraffic;
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
                tpTraffic = itemView.findViewById(R.id.tpTraffic);

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
            myViewHolder.tpTraffic.setVisibility(View.GONE);

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
                                    myViewHolder.tpTraffic.getVisibility() != View.VISIBLE ||
                                    myViewHolder.tvTraffic2.getVisibility() != View.VISIBLE) {

                                myViewHolder.etTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.btTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.tpTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.tvTraffic2.setVisibility(View.VISIBLE);

                            } else if (myViewHolder.etTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.btTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.tpTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.tvTraffic2.getVisibility() != View.GONE) {

                                myViewHolder.etTraffic.setVisibility(View.GONE);
                                myViewHolder.btTraffic.setVisibility(View.GONE);
                                myViewHolder.tpTraffic.setVisibility(View.GONE);
                                myViewHolder.tvTraffic2.setVisibility(View.GONE);

                            }
                            break;

                        case 2:
                            if (myViewHolder.etTraffic.getVisibility() != View.VISIBLE ||
                                    myViewHolder.btTraffic.getVisibility() != View.VISIBLE ||
                                    myViewHolder.tpTraffic.getVisibility() != View.VISIBLE ||
                                    myViewHolder.tvTraffic2.getVisibility() != View.VISIBLE) {

                                myViewHolder.etTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.btTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.tpTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.tvTraffic2.setVisibility(View.VISIBLE);

                            } else if (myViewHolder.etTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.btTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.tpTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.tvTraffic2.getVisibility() != View.GONE) {

                                myViewHolder.etTraffic.setVisibility(View.GONE);
                                myViewHolder.btTraffic.setVisibility(View.GONE);
                                myViewHolder.tpTraffic.setVisibility(View.GONE);
                                myViewHolder.tvTraffic2.setVisibility(View.GONE);

                            }
                            break;


                        case 3:
                            if (myViewHolder.etTraffic.getVisibility() != View.VISIBLE ||
                                    myViewHolder.btTraffic.getVisibility() != View.VISIBLE ||
                                    myViewHolder.tpTraffic.getVisibility() != View.VISIBLE ||
                                    myViewHolder.tvTraffic2.getVisibility() != View.VISIBLE) {

                                myViewHolder.etTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.btTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.tpTraffic.setVisibility(View.VISIBLE);
                                myViewHolder.tvTraffic2.setVisibility(View.VISIBLE);

                            } else if (myViewHolder.etTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.btTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.tpTraffic.getVisibility() != View.GONE ||
                                    myViewHolder.tvTraffic2.getVisibility() != View.GONE) {

                                myViewHolder.etTraffic.setVisibility(View.GONE);
                                myViewHolder.btTraffic.setVisibility(View.GONE);
                                myViewHolder.tpTraffic.setVisibility(View.GONE);
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
                    int UserEnterHour = 0;
                    int UserEnterMin = 0;
                    String UserEnterPerson = myViewHolder.etTraffic.getText().toString();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        UserEnterHour = myViewHolder.tpTraffic.getHour();
                        UserEnterMin = myViewHolder.tpTraffic.getMinute();


                        if (UserEnterPerson.equals("")) {

                            Toast.makeText(context, "請輸入欲接送人數！", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(context, "接送人數為" + UserEnterPerson + "人" +
                                            "時間為" + UserEnterHour + "點" + UserEnterMin + "分",
                                    Toast.LENGTH_SHORT).show();

                        }


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
