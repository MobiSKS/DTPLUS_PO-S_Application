package com.paytm.hpclpos.fragmentscardedandmobtrans.tracking;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.AuthenticateApCallGlaobally;
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard;
import com.paytm.hpclpos.constants.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackingEnterOtpFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.cardPin_editText)
    EditText cardPinEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tracking_enter_otp, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewFinds(view);
    }

    private void viewFinds(View view) {
        view.setOnTouchListener((view1, motionEvent) -> true);

        LinearLayout gotoBack= view.findViewById( R.id.gotoBack);

        EnterMobileNoKeyboard mobNokeyboard = view.findViewById(R.id.mobileNokeyboard);

        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);


        layoutDone.setOnClickListener(v -> callOdometerReading());

        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());

        cardPinEditText.setOnTouchListener((v, event) -> {
            int inType = cardPinEditText.getInputType(); // backup the input type
            cardPinEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            cardPinEditText.onTouchEvent(event); // call native handler
            cardPinEditText.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        cardPinEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        cardPinEditText.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = cardPinEditText.onCreateInputConnection(new EditorInfo());
        mobNokeyboard.setInputConnection(ic);
    }

    private void callOdometerReading() {
        if (Validation.isValidOtp(cardPinEditText.getText().toString())) {
            Fragment fragment = new TrackingOdometerFragment();
            AuthenticateApCallGlaobally authenticateApCallGlaobally = new AuthenticateApCallGlaobally(getActivity(), fragment);
            authenticateApCallGlaobally.authOtpCallback();
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.entervalidotp), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        Log.d("OnClick :::","EnterOtp");
    }
}