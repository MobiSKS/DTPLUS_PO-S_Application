package com.paytm.hpclpos.fragmentnoncardedtransaction.paybaack.paybackburn;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;


public class PaybackBurnMainFrag extends Fragment {

    public PaybackBurnMainFrag() {
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
        final View rootView = inflater.inflate(R.layout.fragment_payback_burn_main, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        LinearLayout lLcardlesstransaction = rootView.findViewById(R.id.lLcardlesstransaction);
        LinearLayout cardSwipeTransaction = rootView.findViewById(R.id.cardSwipeTransaction);

        cardSwipeTransaction.setOnClickListener(v -> callCardbyFrag());

        lLcardlesstransaction.setOnClickListener(v -> callEnterMobNoFrag());


        return rootView;
    }

    private void callCardbyFrag() {
        Fragment fragment = new DisplayPointAndAmountFrag();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

    private void callEnterMobNoFrag() {
        Fragment fragment = new PBEnterMobNoFrag();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }
}