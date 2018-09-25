package com.example.hsinhwang.shrimpshell.CustomerPanel;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.Rating;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.lang.reflect.Type;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ProfileRatingFragment extends Fragment {
    private static final String TAG = "ProfileCommentFragment";
    private FragmentActivity activity;
    private CommonTask ratingGetAllTask, ratingDeleteTask;
    private SwipeMenuRecyclerView rvRating;
    private SwipeRefreshLayout swipeRefreshLayout;




    public ProfileRatingFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        showAllRatings();

    }

    private void showAllRatings() {
        SharedPreferences preferences = activity.getSharedPreferences
                (Common.LOGIN, MODE_PRIVATE);
        int idCustomer = preferences.getInt("IdCustomer", 0);
        if (Common.networkConnected(activity)) {
            String url = Common.URL + "/RatingServlet";
            List<Rating> ratings = null;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getAll");
            jsonObject.addProperty("IdCustomer", idCustomer);
            String jsonOut = jsonObject.toString();
            ratingGetAllTask = new CommonTask(url, jsonOut);
            try {
                String jsonIn = ratingGetAllTask.execute().get();
                Type listType = new TypeToken<List<Rating>>() {
                }.getType();
                ratings = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (ratings == null || ratings.isEmpty()) {
                Common.showToast(activity, R.string.msg_NoCommentsFound);
            } else {
                rvRating.setAdapter(new RatingRecyclerViewAdapter(activity, ratings));
            }
        } else {
            Common.showToast(activity, R.string.msg_NoNetwork);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_profile_comment, parent, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

//        下拉刷新頁面
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                showAllRatings();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        rvRating = view.findViewById(R.id.rvRatings);
        rvRating.setLayoutManager(new LinearLayoutManager(activity));

        return view;
    }

    private class RatingRecyclerViewAdapter extends SwipeMenuRecyclerView.Adapter<RatingRecyclerViewAdapter.MyViewHolder> {
        private LayoutInflater layoutInflater;
        private List<Rating> ratings;
        private RelativeLayout layout;
        private LinearLayout llReview;
        private int opened = -1;

        public RatingRecyclerViewAdapter(Context context, List<Rating> ratings) {
            this.layoutInflater = LayoutInflater.from(context);
            this.ratings = ratings;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View itemView = layoutInflater.inflate(R.layout.item_view_rating, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder myViewHolder, int position) {
            final Rating rating = ratings.get(position);
            final int IdRoomReservation = rating.getIdRoomReservation();

            myViewHolder.tvIdRoomReservation.setText(String.valueOf(IdRoomReservation));
            myViewHolder.rbCardStar.setRating(rating.getRatingStar());
            myViewHolder.tvRatingOpinion.setText(rating.getOpinion());
            myViewHolder.tvRatingReview.setText(rating.getReview());


            if (position == opened) {
                llReview.setVisibility(View.VISIBLE);
            } else {
                llReview.setVisibility(View.GONE);
            }

            //展開評論
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (opened == myViewHolder.getAdapterPosition()) {
                        opened = -1;
                        notifyItemChanged(myViewHolder.getAdapterPosition());

                    } else {
                        int oldOpened = opened;
                        opened = myViewHolder.getAdapterPosition();
                        notifyItemChanged(oldOpened);
                        notifyItemChanged(opened);
                    }
                }

            });

            layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(activity)
                            .setTitle("SS Hotel")
                            .setMessage("刪除留言")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.e(TAG, "ibDelete onClick");
                                    if (Common.networkConnected(activity)) {
                                        String url = Common.URL + "/RatingServlet";
                                        JsonObject jsonObject = new JsonObject();
                                        jsonObject.addProperty("action", "delete");
                                        jsonObject.addProperty("IdRoomReservation", IdRoomReservation);
                                        int count = 0;
                                        try {
                                            ratingDeleteTask = new CommonTask(url, jsonObject.toString());
                                            String result = ratingDeleteTask.execute().get();
                                            count = Integer.valueOf(result);
                                        } catch (Exception e) {
                                            Log.e(TAG, e.toString());
                                        }
                                        if (count == 0) {
                                            Common.showToast(activity, R.string.msg_DeleteFail);
                                        } else {
                                            ratings.remove(rating);
                                            RatingRecyclerViewAdapter.this.notifyDataSetChanged();
                                            Common.showToast(activity, R.string.msg_DeleteSuccess);
                                        }
                                    }
                                }
                            }).show();
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return ratings.size();
        }

        class MyViewHolder extends SwipeMenuRecyclerView.ViewHolder {
            TextView tvRatingOpinion, tvRatingReview, tvIdRoomReservation;
            RatingBar rbCardStar;
            SwipeMenuRecyclerView rvRatings;



            public MyViewHolder(View itemView) {
                super(itemView);
                tvRatingOpinion = itemView.findViewById(R.id.tvRatingOpinion);
                rbCardStar = itemView.findViewById(R.id.rbCardStar);
                tvRatingReview = itemView.findViewById(R.id.tvRatingReview);
                tvIdRoomReservation = itemView.findViewById(R.id.tvIdRoomReservation);
                layout = itemView.findViewById(R.id.layout);
                rvRatings = itemView.findViewById(R.id.rvRatings);
                llReview = itemView.findViewById(R.id.llReview);

            }
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (ratingGetAllTask != null) {
            ratingGetAllTask.cancel(true);
            ratingGetAllTask = null;
        }
    }
}

