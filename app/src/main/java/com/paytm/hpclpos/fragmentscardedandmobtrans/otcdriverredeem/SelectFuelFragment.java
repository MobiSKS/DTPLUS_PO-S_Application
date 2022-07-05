package com.paytm.hpclpos.fragmentscardedandmobtrans.otcdriverredeem;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SelectFuelFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.rLSelectFuel)
    RelativeLayout rLSelectFuel;
    @BindView(R.id.gotoBack)
    ImageView gotoBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("oncreate",":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_select_fuel, container, false);
        ButterKnife.bind(this,view);

        view.setOnTouchListener((view1, motionEvent) -> true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewFinds();
    }

    private void viewFinds() {
        rLSelectFuel.setOnClickListener(this);
        gotoBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rLSelectFuel:
                callEnterPointToRedeemFragment();
                break;
            case R.id.gotoBack:
                getActivity().onBackPressed();
                break;

            default:
                break;
        }
    }
    private void callEnterPointToRedeemFragment() {
        Fragment fragment = new EnterPointsToRedeemFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }
}