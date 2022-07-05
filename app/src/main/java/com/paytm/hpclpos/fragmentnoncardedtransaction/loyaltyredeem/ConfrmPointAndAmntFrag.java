package com.paytm.hpclpos.fragmentnoncardedtransaction.loyaltyredeem;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.Constants;

public class ConfrmPointAndAmntFrag extends Fragment implements View.OnClickListener {

    private String rcvAmount;

    public ConfrmPointAndAmntFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("oncreate",":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_confrm_point_and_amnt, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Constants.MYPREF, MODE_PRIVATE);
        String rcvCotrolCardNo = sharedPreferences.getString(Constants.CONTROLCARDNO, "");

        LinearLayout lLOkconfirm =rootView.findViewById(R.id.lLOkconfirm);
        TextView redeemPointTextView = rootView.findViewById(R.id.redeemPoint_TextView);
        TextView cardNotextView = rootView.findViewById(R.id.cardNotextView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            rcvAmount = bundle.getString(Constants.REDEEMPOINT, "0");
        }
        redeemPointTextView.setText(rcvAmount);
        cardNotextView.setText(rcvCotrolCardNo);
        lLOkconfirm.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lLOkconfirm) {
            callEnterControlPinFrag();
        }
    }

    private void callEnterControlPinFrag() {
        Fragment fragment = new LoyltyEnterControlPinFrag();
        requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }
}