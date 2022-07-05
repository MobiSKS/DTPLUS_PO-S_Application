package com.paytm.hpclpos.fragmentnoncardedtransaction.paybaack.paybackburn;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;


public class DisplayPointAndAmountFrag extends Fragment {


    public DisplayPointAndAmountFrag() {
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
        final View rootView = inflater.inflate(R.layout.fragment_display_point_and_amount, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        LinearLayout lLContinue = rootView.findViewById(R.id.lLContinue);
        LinearLayout lLCancl = rootView.findViewById(R.id.lLCancl);

        lLContinue.setOnClickListener(v -> callEnterAmountFrag());

        lLCancl.setOnClickListener(v -> getActivity().onBackPressed());


        return rootView;
    }

    public void callEnterAmountFrag(){
        Fragment fragment = new PBEnterAmntFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }
}