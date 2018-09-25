package com.example.hsinhwang.shrimpshell.CustomerPanel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.ProfileReceiptDetail;
import com.example.hsinhwang.shrimpshell.Classes.Rating;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class ProfileAddRatingFragment extends Fragment{
    final static String TAG = "ProfileAddRatingFragment";
    private Activity activity;
    private Button btRatingCancel, btRatingOK;
    private EditText etOpinion;
    private RatingBar rbRating;
    private int IdRoomReservation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        activity = getActivity();

        return inflater.inflate(R.layout.fragment_rating_add, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findview();

        btRatingOK.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                //星星數未滿0.5顆，不給評論
                if (rbRating.getRating() == 0) {
                    new AlertDialog.Builder(activity)
                            .setTitle("SS Hotel")
                            .setMessage("請給予分數")
                            .setPositiveButton("確定", null)
                            .show();
                    return;
                }

                //獲得點擊確認後之時間
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                String time = dateFormat.format(date);

                Float ratingStar = rbRating.getRating();
                String opinion = etOpinion.getText().toString();

                if (Common.networkConnected(activity)) {
                    //由Bundle中獲得IdRoomReservation資訊
                    Bundle bundle = activity.getIntent().getExtras();
                    if (bundle != null) {
                        final Object object = bundle.getSerializable("profileReceiptDetail");
                        if (object != null) {
                            final ProfileReceiptDetail profileReceiptDetail = (ProfileReceiptDetail) object;
                            int idRoomReservation = Integer.valueOf(profileReceiptDetail.getOrderNumber());

                            //RatingInsert
                            String url = Common.URL + "/RatingServlet";
                            Rating comment = new Rating(0, ratingStar, time, opinion, "", idRoomReservation);
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("action", "ratingInsert");
                            jsonObject.addProperty("rating", new Gson().toJson(comment));
                            int count = 0;
                            try {
                                String result = new CommonTask(url, jsonObject.toString()).execute().get();
                                count = Integer.valueOf(result);
                            } catch (Exception e) {
                                Log.e(TAG, e.toString());
                            }
                            if (count == 0) {
                                Common.showToast(activity, R.string.msg_InsertFail);
                            } else {
                                Toast.makeText(activity, "評論已送出，感謝您的支持。", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Common.showToast(activity, R.string.msg_NoNetwork);
                        }
                        activity.finish();
                    }
                }
            }
        });

        //取消評論
        btRatingCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbRating.setRating(0);
                etOpinion.setText("");
                Toast.makeText(activity, "取消", Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        });
    }

    private void findview() {
        btRatingCancel = (Button) activity.findViewById(R.id.btRatingCancel);
        btRatingOK = (Button) activity.findViewById(R.id.btRatingOK);
        etOpinion = (EditText) activity.findViewById(R.id.etOpinion);
        rbRating = (RatingBar) activity.findViewById(R.id.rbRating);
    }
}
