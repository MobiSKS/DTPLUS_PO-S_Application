package com.paytm.hpclpos.activities.dashboard.merchantservice.management;

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

public class UnblockTerminalPinFrag extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", ":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_unblock_terminal_pin, container, false);

        EditText newPinEditText =  rootView.findViewById(R.id.newPin_EditText);
        EditText reEnterNewPinEditText =rootView.findViewById(R.id.reEnterNewPin_EditText);

        ImageView gotoBack=  rootView.findViewById( R.id.gotoBack);

        EnterMobileNoKeyboard mobNokeyboard =  rootView.findViewById(R.id.keyboard);

        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);


        layoutDone.setOnClickListener(v -> callPinChangeStatus());

        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());

        newPinEditText.setOnTouchListener((v, event) -> {
            int inType = newPinEditText.getInputType(); // backup the input type
            newPinEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            newPinEditText.onTouchEvent(event); // call native handler
            newPinEditText.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        newPinEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        newPinEditText.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = newPinEditText.onCreateInputConnection(new EditorInfo());
        mobNokeyboard.setInputConnection(ic);

        reEnterNewPinEditText.setOnTouchListener((v, event) -> {
            int inType = reEnterNewPinEditText.getInputType(); // backup the input type
            reEnterNewPinEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            reEnterNewPinEditText.onTouchEvent(event); // call native handler
            reEnterNewPinEditText.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        reEnterNewPinEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        reEnterNewPinEditText.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection icNew = reEnterNewPinEditText.onCreateInputConnection(new EditorInfo());
        mobNokeyboard.setInputConnection(icNew);


        return rootView;
    }

    private void callPinChangeStatus(){

        Toast.makeText(getContext(), "Terminal Pin has been changed Successfully..", Toast.LENGTH_SHORT).show();

    }
}