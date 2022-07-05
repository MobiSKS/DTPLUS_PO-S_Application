package com.paytm.hpclpos.fragmentnoncardedtransaction.paybaack.paybackearn;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;

public class PayBackEarnFragment extends Fragment {

    public PayBackEarnFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_pay_back_earn, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        LinearLayout lLcardlesstransaction = rootView.findViewById(R.id.lLcardlesstransaction);
        LinearLayout cardSwipeTransaction = rootView.findViewById(R.id.cardSwipeTransaction);
        LinearLayout llChipCardRead = rootView.findViewById(R.id.llChipCardRead);

        cardSwipeTransaction.setOnClickListener(v -> {

           // DO nothing
        });

        llChipCardRead.setOnClickListener(v -> {
            // DO nothing
        });

        lLcardlesstransaction.setOnClickListener(v -> callEnterMobNoFrag());

        return rootView;
    }


    private void callEnterMobNoFrag() {
        Fragment fragment = new PayBackEnterMobNoFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }
}