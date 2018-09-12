package com.example.hsinhwang.shrimpshell.InstantCustomerPanel;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;


import com.example.hsinhwang.shrimpshell.Classes.DinlingServiceMsg;
import com.example.hsinhwang.shrimpshell.R;

import java.util.ArrayList;
import java.util.List;


public class DinlingServiceFragment extends Fragment {
    private RecyclerView rvDinlingService;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dinling_service_fab,
                container, false);

        handleViews(view);


        return view;
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
                                            R.drawable.icon_dinling_a, 1 ,0));
                            dinlingServiceMsgs.add(new DinlingServiceMsg
                                    ("商品：B餐", "價格：250 元",
                                            R.drawable.icon_dinling_b, 2,0));
                            dinlingServiceMsgs.add(new DinlingServiceMsg
                                    ("商品：C餐", "價格：200 元",
                                            R.drawable.icon_dinling_c, 3,0));

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



                    String UserEnter = myViewHolder.etDinling.getText().toString();

                    switch (dinlingServiceMsg.getNumber()) {

                        case 1:
                            if (UserEnter.equals("")) {

                                Toast.makeText(context, "請點選您需要的數量",
                                        Toast.LENGTH_SHORT).show();


                            } else {

                                Intent intent = new Intent(getActivity(),StatusServiceActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("123",dinlingServiceMsg.getTvDinling1());
                                intent.putExtras(bundle);
                                startActivity(intent);


                                Toast.makeText(context, "你點的 A餐 數量為" + UserEnter + "份",
                                        Toast.LENGTH_SHORT).show();

                                
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
                        R.drawable.icon_dinling, 0, 0));


        return dinlingServiceMsgs;
    }



}