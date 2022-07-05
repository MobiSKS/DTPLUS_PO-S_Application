package com.paytm.hpclpos.fragmentnoncardedtransaction.ccmsrecharge.cheque;

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

import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard;
import com.paytm.hpclpos.constants.Validation;

import butterknife.ButterKnife;

public class ChequeEnterCardPinFrag extends Fragment implements View.OnClickListener {

    EditText cardpinEdittext;
    LinearLayout gotoBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("oncreate",":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_cheque_enter_card_pin, container, false);
        rootView.setOnTouchListener((view, motionEvent) -> true);

        cardpinEdittext = rootView.findViewById(R.id.cardPin_editText);
        gotoBack = rootView.findViewById(R.id.gotoBack);

        ButterKnife.bind(this,rootView);

        EnterMobileNoKeyboard mobNokeyboard = rootView.findViewById(R.id.mobileNokeyboard);

        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);

        layoutDone.setOnClickListener(this);
        gotoBack.setOnClickListener(this);

        cardpinEdittext.setOnTouchListener((v, event) -> {
            int inType = cardpinEdittext.getInputType(); // backup the input type
            cardpinEdittext.setInputType(InputType.TYPE_NULL); // disable soft input
            cardpinEdittext.onTouchEvent(event); // call native handler
            cardpinEdittext.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        cardpinEdittext.setRawInputType(InputType.TYPE_CLASS_TEXT);
        cardpinEdittext.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = cardpinEdittext.onCreateInputConnection(new EditorInfo());
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
                callEnterTerminalPInFrag();
                break;

            default:
                break;
        }
    }

    private void callEnterTerminalPInFrag() {

        String cardPin = cardpinEdittext.getText().toString();

        if (cardPin.trim().length() < 1) {
            Toast.makeText(getActivity(), "Please Enter Card Pin.", Toast.LENGTH_SHORT).show();

        } else if (!Validation.isvalidCardPin(cardPin.trim())) {
            Toast.makeText(getActivity(), "Please enter a valid Card Pin.", Toast.LENGTH_SHORT).show();

        } else {

            Fragment fragment = new ChequeEnterTerminalPinFrag();
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            cardpinEdittext.setText("");
        }
    }
}