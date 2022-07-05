package com.paytm.hpclpos.fragmentnoncardedtransaction.creditsalecomplete;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.paytm.hpclpos.R;
import com.paytm.hpclpos.activities.mainsaleactivities.MainActivity;
import com.paytm.hpclpos.constants.Constants;

import static android.content.Context.MODE_PRIVATE;


public class TransactionCnfrmFragment extends Fragment {

    private String rcvAmount;

    private Dialog dialog;

    public TransactionCnfrmFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", ":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_transaction_cnfrm, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Constants.MYPREF, MODE_PRIVATE);
        String rcvCotrolCardNo = sharedPreferences.getString(Constants.CONTROLCARDNO, "");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            rcvAmount = bundle.getString(Constants.ENTERAMOUNT, "0");
        }

        LinearLayout lLOkconfirm = rootView.findViewById(R.id.lLOkconfirm);
        LinearLayout lLCancl = rootView.findViewById(R.id.lLCancl);

        TextView rcvAmntText = rootView.findViewById(R.id.rcvAmntText);
        TextView cntrlCardNoTextView = rootView.findViewById(R.id.cntrlCard_NoTextView);

        rcvAmntText.setText(rcvAmount);
        cntrlCardNoTextView.setText(rcvCotrolCardNo);

        lLOkconfirm.setOnClickListener(v -> callTransactionSlip());

        lLCancl.setOnClickListener(v -> callMainActivity());

        return rootView;
    }

    private void callTransactionSlip() {

        showGifDialog();

        new Handler().postDelayed(() -> {

            dialog.dismiss();

            Intent myIntent = new Intent(getActivity(), MainActivity.class);
            requireActivity().startActivity(myIntent);
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

    private void callMainActivity() {

        Intent myIntent = new Intent(getActivity(), MainActivity.class);
        requireActivity().startActivity(myIntent);
    }
}