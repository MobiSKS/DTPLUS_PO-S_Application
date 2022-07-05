package com.paytm.hpclpos.fragmentscardedandmobtrans.otcdriverredeem;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.paytm.hpclpos.R;
import com.paytm.hpclpos.activities.mainsaleactivities.MainActivity;

import butterknife.ButterKnife;


public class ConfirmPointNdAmntFragment extends Fragment implements View.OnClickListener {

    private Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", ":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_point_nd_amnt, container, false);
        ButterKnife.bind(this, view);

        LinearLayout lLOkconfirm=view.findViewById(R.id.lLOkconfirm);
        LinearLayout lLCancl=view.findViewById(R.id.lLCancl);

        lLOkconfirm.setOnClickListener(this);
        lLCancl.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewFinds(view);
    }

    private void viewFinds(View view) {
        view.setOnTouchListener((view1, motionEvent) -> true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lLOkconfirm:

                callTransactionSlip();
                break;

            case R.id.lLCancl:
                getActivity().onBackPressed();
                break;

            default:
                break;

        }
    }

    private void callTransactionSlip(){

        showGifDialog();

        new Handler().postDelayed(() -> {

            dialog.dismiss();

            Intent myIntent = new Intent(getActivity(), MainActivity.class);
            getActivity().startActivity(myIntent);
            getActivity().finish();
        }, 3000);
    }

    private void showGifDialog() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_print_gif_layout);
        ImageView imageView = dialog.findViewById(R.id.gifImageview);
        Glide.with(this).load(R.drawable.printergif).into(imageView);
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.show();
    }
}