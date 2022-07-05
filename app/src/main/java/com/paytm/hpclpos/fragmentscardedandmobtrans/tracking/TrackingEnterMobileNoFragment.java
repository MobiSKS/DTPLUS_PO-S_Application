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
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard;
import com.paytm.hpclpos.constants.SendOtpApiCallGlobaly;
import com.paytm.hpclpos.constants.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrackingEnterMobileNoFragment extends Fragment {

    @BindView(R.id.mobno_editText)
    EditText mobnoEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("oncreate",":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracking_enter_mobile_no, container, false);
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
        LinearLayout gotoBack = view.findViewById(R.id.gotoBack);
        EnterMobileNoKeyboard mobNokeyboard = view.findViewById(R.id.mobileNokeyboard);
        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());
        LinearLayout layoutDone = mobNokeyboard.findViewById(R.id.llayout_done);
        layoutDone.setOnClickListener(v -> enterMobOTPActivityCall());
        mobnoEditText.setOnTouchListener((v, event) -> {
            int inType = mobnoEditText.getInputType(); // backup the input type
            mobnoEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            mobnoEditText.onTouchEvent(event); // call native handler
            mobnoEditText.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        mobnoEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        mobnoEditText.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = mobnoEditText.onCreateInputConnection(new EditorInfo());
        mobNokeyboard.setInputConnection(ic);
    }

    private void enterMobOTPActivityCall() {
        if (Validation.isValidMobileNo(mobnoEditText.getText().toString())) {
            Fragment fragment = new TrackingEnterOtpFragment();
            SendOtpApiCallGlobaly sendOtpApiCallGlobaly = new SendOtpApiCallGlobaly(getActivity(), fragment);
            sendOtpApiCallGlobaly.sendOtpCallback();
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.entervalidmono), Toast.LENGTH_LONG).show();
        }
    }
}