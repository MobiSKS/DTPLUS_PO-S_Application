package com.paytm.hpclpos.fragmentscardedandmobtrans.tracking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackingFragment extends Fragment implements View.OnClickListener {

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
        View view = inflater.inflate(R.layout.fragment_tracking, container, false);
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
        cardSwipeTransaction.setOnClickListener(this);
        lLcardlesstransaction.setOnClickListener(this);

    }


    private void callEnterMobNoFragment() {
        Fragment fragment = new TrackingEnterMobileNoFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

    }

    private void callCardbyFragment() {
        Fragment fragment = new TrackingCardPinFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardSwipeTransaction:
                callCardbyFragment();
                break;
            case R.id.lLcardlesstransaction:
                callEnterMobNoFragment();
                break;
            default:
                break;
        }
    }
}