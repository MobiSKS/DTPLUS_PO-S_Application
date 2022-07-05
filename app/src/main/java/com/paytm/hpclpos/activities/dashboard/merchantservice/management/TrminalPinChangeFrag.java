package com.paytm.hpclpos.activities.dashboard.merchantservice.management;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;

public class TrminalPinChangeFrag extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", ":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_trminal_pin_change, container, false);
        ImageView gotoBack= rootView.findViewById( R.id.gotoBack);

        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());

        return rootView;
    }
}