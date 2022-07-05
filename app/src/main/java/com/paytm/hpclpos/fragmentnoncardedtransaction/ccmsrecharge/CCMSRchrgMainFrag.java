package com.paytm.hpclpos.fragmentnoncardedtransaction.ccmsrecharge;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.fragmentnoncardedtransaction.ccmsrecharge.cheque.ChequeMainFragment;
import com.paytm.hpclpos.ui.NeftMainFragment.NEFTMainFragment;


public class CCMSRchrgMainFrag extends Fragment {


    public CCMSRchrgMainFrag() {
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
        final View rootView = inflater.inflate(R.layout.fragment_c_c_m_s_rchrg_main, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        RelativeLayout rlSelectCash = rootView.findViewById(R.id.rlSelectCash);
        RelativeLayout rLSelectCheque = rootView.findViewById(R.id.rLSelectCheque);
        RelativeLayout rLSelectNEFT = rootView.findViewById(R.id.rLSelectNEFT);

        ImageView gotoBack = rootView.findViewById(R.id.gotoBack);

        rlSelectCash.setOnClickListener(v -> callEnterCntRLCardNoFrag());

        rLSelectCheque.setOnClickListener(v -> callChequeMainFrag());

        rLSelectNEFT.setOnClickListener(v -> callNEFTMainFrag());

        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());

        return rootView;

    }

    private void callEnterCntRLCardNoFrag() {
        // DO nothing
    }

    private void callChequeMainFrag() {
        Fragment fragment = new ChequeMainFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

    private void callNEFTMainFrag() {
        Fragment fragment = new NEFTMainFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }
}