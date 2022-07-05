package com.paytm.hpclpos.fragmentnoncardedtransaction.paybaack;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.fragmentnoncardedtransaction.paybaack.paybackburn.PaybackBurnMainFrag;
import com.paytm.hpclpos.fragmentnoncardedtransaction.paybaack.paybackearn.PayBackEarnFragment;
import com.paytm.hpclpos.fragmentnoncardedtransaction.paybaack.paybackvoid.PaybackPresenceCrdFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PaybackMainFragment extends Fragment {

    @BindView(R.id.rlVoid)
    RelativeLayout rlVoid;

    public PaybackMainFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_payback_main, container, false);
        ButterKnife.bind(this,rootView);
        rootView.setOnTouchListener((view, motionEvent) -> true);

        RelativeLayout rlEarn = rootView.findViewById(R.id.rlEarn);
        RelativeLayout rLBurn = rootView.findViewById(R.id.rLBurn);
        ImageView gotoBack = rootView.findViewById(R.id.gotoBack);

        rlEarn.setOnClickListener(v -> callPaybackEarnFrag());

        rLBurn.setOnClickListener(v -> callPaybackBurnMainFrag());

        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());

        rlVoid.setOnClickListener(v -> callPaybackVoid());

        return rootView;
    }

    private void callPaybackEarnFrag() {
        Fragment fragment = new PayBackEarnFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }


    private void callPaybackVoid() {
        Fragment fragment = new PaybackPresenceCrdFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

        private void callPaybackBurnMainFrag() {
        Fragment fragment = new PaybackBurnMainFrag();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }
}