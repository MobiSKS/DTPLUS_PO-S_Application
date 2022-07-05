package com.paytm.hpclpos.fragmentnoncardedtransaction.creditsalecomplete;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;


public class CSCnfrmAmntFragment extends Fragment {



    public CSCnfrmAmntFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_c_s_cnfrm_amnt, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        return rootView;
    }
}