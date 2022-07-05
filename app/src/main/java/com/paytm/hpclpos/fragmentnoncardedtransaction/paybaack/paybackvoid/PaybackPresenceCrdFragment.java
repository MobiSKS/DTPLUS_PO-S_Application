package com.paytm.hpclpos.fragmentnoncardedtransaction.paybaack.paybackvoid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.paytm.hpclpos.R;


public class PaybackPresenceCrdFragment extends Fragment {

    @BindView(R.id.lLcardlesstransaction)
    LinearLayout lLcardlesstransaction;

    @BindView(R.id.cardSwipeTransaction)
    LinearLayout cardSwipeTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("oncreate",":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payback_presence_crd, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewFinds(view);
    }

    private void viewFinds(View view) {
        view.setOnTouchListener((view1, motionEvent) -> true);
        cardSwipeTransaction.setOnClickListener(v -> callCardbyActivity());

        lLcardlesstransaction.setOnClickListener(v -> callEnterMobNoActivity());
    }

    private void callEnterMobNoActivity() {
        Fragment fragment = new PaybackMobileNoFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

    }

    private void callCardbyActivity() {
        Fragment fragment = new PaybackRocNoFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

    }
}