package com.example.hsinhwang.shrimpshell;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class ProfileCommentFragment extends Fragment {
    private Button btCommentCancel, btCommentOK;
    private EditText etCommentText;
    private RatingBar ratingBar;
    private Context context;



    public ProfileCommentFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_comment, parent, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btCommentCancel = (Button) getActivity().findViewById(R.id.btCommentCancel);
        btCommentOK = (Button) getActivity().findViewById(R.id.btCommentOK);
        etCommentText = (EditText) getActivity().findViewById(R.id.etCommentText);
        ratingBar = (RatingBar) getActivity().findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(ratingBarOnRatingBarChange);

        btCommentCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                ratingBar.setRating(0);
                etCommentText.setText("");
                Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
            }

        });

        btCommentOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){
                if (ratingBar.getRating() == 0)
                    new AlertDialog.Builder(getContext())
                            .setTitle("SS Hotel")
                            .setMessage("請給予分數")
                            .setPositiveButton("確定", null)
                            .show();
                else
                    Toast.makeText(getActivity(), "評論已送出，感謝您的支持。", Toast.LENGTH_SHORT).show();

            }
        });





    }
    public RatingBar.OnRatingBarChangeListener ratingBarOnRatingBarChange
            = new RatingBar.OnRatingBarChangeListener()  {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            Toast.makeText(getActivity(), "rating: " + rating, Toast.LENGTH_LONG).show();
        }

    };

}

