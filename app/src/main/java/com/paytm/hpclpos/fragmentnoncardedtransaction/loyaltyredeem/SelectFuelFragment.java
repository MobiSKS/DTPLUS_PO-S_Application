package com.paytm.hpclpos.fragmentnoncardedtransaction.loyaltyredeem;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SelectFuelFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.gotoBack)
    ImageView gotoBack;

    @BindView(R.id.rLSelectFuel)
    RelativeLayout rLSelectFuel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", ":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_select_fuel2, container, false);

        ButterKnife.bind(this,rootView);

        viewFinds(rootView);
        return rootView;

    }

    private void viewFinds(View view) {
        view.setOnTouchListener((view1, motionEvent) -> true);

        //All click listner
        gotoBack.setOnClickListener(this);
        rLSelectFuel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gotoBack:
                getActivity().onBackPressed();
                break;

            case R.id.rLSelectFuel:
                goToPointRedeemFrag();
                break;

            default:
                break;

        }
    }

    private void goToPointRedeemFrag() {

        Fragment fragment = new EnterPointToRedeemFrag();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }
}