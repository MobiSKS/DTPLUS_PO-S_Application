package com.paytm.hpclpos.fragmentnoncardedtransaction.loyaltyredeem;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard;
import com.paytm.hpclpos.constants.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoyltyEnterControlPinFrag extends Fragment implements  View.OnClickListener {

    @BindView(R.id.cntRLPin_editText)
    EditText cntRLPinEditText;
    @BindView(R.id.gotoBack)
    ImageView gotoBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", ":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_loylty_enter_control_pin, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        ButterKnife.bind(this,rootView);

        EnterMobileNoKeyboard mobNokeyboard = rootView.findViewById(R.id.mobileNokeyboard);


        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);

        layoutDone.setOnClickListener(this);
        gotoBack.setOnClickListener(this);


        cntRLPinEditText.setOnTouchListener((v, event) -> {
            int inType = cntRLPinEditText.getInputType(); // backup the input type
            cntRLPinEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            cntRLPinEditText.onTouchEvent(event); // call native handler
            cntRLPinEditText.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        cntRLPinEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        cntRLPinEditText.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = cntRLPinEditText.onCreateInputConnection(new EditorInfo());
        mobNokeyboard.setInputConnection(ic);

         return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gotoBack:
                getActivity().onBackPressed();
                break;

            case R.id.llayout_done:
                callTransactionSlip();
                break;

            default:
                break;
        }
    }

    private void callTransactionSlip() {

        String ctRLPin = cntRLPinEditText.getText().toString();

        if(ctRLPin.trim().length()<1){
            Toast.makeText(getContext(),"Please Enter Control Pin.",Toast.LENGTH_SHORT).show();


        }else if (!Validation.isvalidControlPin(ctRLPin.trim())) {
            Toast.makeText(getContext(),"Please enter a valid Control Pin.",Toast.LENGTH_SHORT).show();


        } else {

            Toast.makeText(getContext(), "Transaction Slip Woulb be Printed..", Toast.LENGTH_SHORT).show();
            cntRLPinEditText.setText("");
        }

    }
}