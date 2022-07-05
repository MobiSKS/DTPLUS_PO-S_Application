package com.paytm.hpclpos.fragmentnoncardedtransaction.controlpinchange;

import android.content.Intent;
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
import com.paytm.hpclpos.activities.dashboard.TransactionDashboardActivity;
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard;
import com.paytm.hpclpos.constants.Validation;

public class ChangeTerminalPinFrag extends Fragment {

    private EditText oldpinEdittext;
    private EditText newpinEdittext;
    private EditText reenterpinEdittext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", ":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_change_terminal_pin, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        oldpinEdittext = rootView.findViewById(R.id.oldPIN_EditText);
        newpinEdittext = rootView.findViewById(R.id.newPIN_EditText);
        reenterpinEdittext = rootView.findViewById(R.id.reEnterPin_EditText);

        LinearLayout lLNext = rootView.findViewById(R.id.lLNext);


        ImageView gotoBack=  rootView.findViewById( R.id.gotoBack);

        EnterMobileNoKeyboard mobNokeyboard =  rootView.findViewById(R.id.keyboard);

        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);


        layoutDone.setOnClickListener(v -> callControlPinChngStatus());

        lLNext.setOnClickListener(v -> callControlPinChngStatus());

        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());

        oldpinEdittext.setOnTouchListener((v, event) -> {
            int inType = oldpinEdittext.getInputType(); // backup the input type
            oldpinEdittext.setInputType(InputType.TYPE_NULL); // disable soft input
            oldpinEdittext.onTouchEvent(event); // call native handler
            oldpinEdittext.setInputType(inType); // restore input type

            // prevent system keyboard from appearing when EditText is tapped
            oldpinEdittext.setRawInputType(InputType.TYPE_CLASS_TEXT);
            oldpinEdittext.setTextIsSelectable(true);

            // pass the InputConnection from the EditText to the keyboard
            InputConnection ic = oldpinEdittext.onCreateInputConnection(new EditorInfo());
            mobNokeyboard.setInputConnection(ic);
            return true; // consume touch even
        });

        newpinEdittext.setOnTouchListener((v, event) -> {
            int inType = newpinEdittext.getInputType(); // backup the input type
            newpinEdittext.setInputType(InputType.TYPE_NULL); // disable soft input
            newpinEdittext.onTouchEvent(event); // call native handler
            newpinEdittext.setInputType(inType); // restore input type

            // prevent system keyboard from appearing when EditText is tapped
            newpinEdittext.setRawInputType(InputType.TYPE_CLASS_TEXT);
            newpinEdittext.setTextIsSelectable(true);

            // pass the InputConnection from the EditText to the keyboard
            InputConnection icNew = newpinEdittext.onCreateInputConnection(new EditorInfo());
            mobNokeyboard.setInputConnection(icNew);

            return true; // consume touch even
        });

        reenterpinEdittext.setOnTouchListener((v, event) -> {
            int inType = reenterpinEdittext.getInputType(); // backup the input type
            reenterpinEdittext.setInputType(InputType.TYPE_NULL); // disable soft input
            reenterpinEdittext.onTouchEvent(event); // call native handler
            reenterpinEdittext.setInputType(inType); // restore input type

            // prevent system keyboard from appearing when EditText is tapped
            reenterpinEdittext.setRawInputType(InputType.TYPE_CLASS_TEXT);
            reenterpinEdittext.setTextIsSelectable(true);

            // pass the InputConnection from the EditText to the keyboard
            InputConnection icNew = reenterpinEdittext.onCreateInputConnection(new EditorInfo());
            mobNokeyboard.setInputConnection(icNew);

            return true; // consume touch even
        });
        return rootView;
    }

    private void callControlPinChngStatus() {

        String oldPin = oldpinEdittext.getText().toString();
        String newPin = newpinEdittext.getText().toString();
        String reEnterNewPin = reenterpinEdittext.getText().toString();

        if (!Validation.isvalidTerminalPin(oldPin.trim())) {

            Toast.makeText(getActivity(),"Please enter a valid Old Terminal Pin.",Toast.LENGTH_SHORT).show();

        }else if(!Validation.isvalidTerminalPin(newPin.trim())){

            Toast.makeText(getActivity(),"Please enter a valid New Terminal Pin.",Toast.LENGTH_SHORT).show();
        }

        else if(!Validation.isvalidTerminalPin(reEnterNewPin.trim())){

            Toast.makeText(getActivity(),"Please enter a valid New Terminal Pin.",Toast.LENGTH_SHORT).show();
        }else if (!newPin.equals(reEnterNewPin)) {

            Toast.makeText(getActivity(), "Wrong Terminal Pin", Toast.LENGTH_SHORT).show();
        }

        else {

            Toast.makeText(getActivity(),"Control Pin has been changed successfully..",Toast.LENGTH_SHORT).show();

            Intent myIntent = new Intent(getActivity(), TransactionDashboardActivity.class);
            getActivity().startActivity(myIntent);

            oldpinEdittext.setText("");
            newpinEdittext.setText("");
            reenterpinEdittext.setText("");
        }
    }
}