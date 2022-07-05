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

public class ChequeMainFragment extends Fragment {

 private EditText chequeNoEditText;
 private EditText micrCodeEditText;

    public ChequeMainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("onCreate","ChequeMainFragment");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_cheque_main, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        chequeNoEditText = rootView.findViewById(R.id.etChqNo);
        micrCodeEditText = rootView.findViewById(R.id.etMicrCode);
        LinearLayout gotoBack= rootView.findViewById( R.id.gotoBack);
        EnterMobileNoKeyboard mobNokeyboard = rootView.findViewById(R.id.keyboard);

        LinearLayout layoutDone = mobNokeyboard.findViewById(R.id.llayout_done);


        layoutDone.setOnClickListener(v -> callRechargeAmountFrag());

        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());

        chequeNoEditText.setOnTouchListener((v, event) -> {
            int inType = chequeNoEditText.getInputType(); // backup the input type
            chequeNoEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            chequeNoEditText.onTouchEvent(event); // call native handler
            chequeNoEditText.setInputType(inType); // restore input type
            // prevent system keyboard from appearing when EditText is tapped
            chequeNoEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            chequeNoEditText.setTextIsSelectable(true);

            // pass the InputConnection from the EditText to the keyboard
            InputConnection ic = chequeNoEditText.onCreateInputConnection(new EditorInfo());
            mobNokeyboard.setInputConnection(ic);
            return true; // consume touch even
        });




        micrCodeEditText.setOnTouchListener((v, event) -> {
            int inType = micrCodeEditText.getInputType(); // backup the input type
            micrCodeEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            micrCodeEditText.onTouchEvent(event); // call native handler
            micrCodeEditText.setInputType(inType); // restore input type

            // prevent system keyboard from appearing when EditText is tapped
            micrCodeEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            micrCodeEditText.setTextIsSelectable(true);

            // pass the InputConnection from the EditText to the keyboard
            InputConnection icNew = micrCodeEditText.onCreateInputConnection(new EditorInfo());
            mobNokeyboard.setInputConnection(icNew);

            return true; // consume touch even
        });

        return rootView;
    }


    private void callRechargeAmountFrag(){
        String cheqNo = chequeNoEditText.getText().toString();
        String mICRCode = micrCodeEditText.getText().toString();

        if (!Validation.isvalidChequeNo(cheqNo.trim())) {

            Toast.makeText(getContext(),"Please enter a valid Cheque No",Toast.LENGTH_SHORT).show();


        } else if (!Validation.isvalidMICRCode(mICRCode.trim())) {
            Toast.makeText(getContext(),"Please enter a valid MICR Code",Toast.LENGTH_SHORT).show();
        }

        else {

            Fragment fragment = new CheqEnterCntrlCardNoFrag();
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            chequeNoEditText.setText("");
            micrCodeEditText.setText("");
        }

    }
}