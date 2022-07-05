package com.paytm.hpclpos.fragmentnoncardedtransaction.paybaack.paybackburn;

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

import butterknife.BindView;
import butterknife.ButterKnife;


public class PBEnterPinFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.cardPin_editText)
    EditText cardPinEditText;

    public PBEnterPinFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", ":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_p_b_enter_pin, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        ButterKnife.bind(this,rootView);

        EnterMobileNoKeyboard mobNokeyboard = rootView.findViewById(R.id.mobileNokeyboard);


        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);

        layoutDone.setOnClickListener(this);


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


        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.llayout_done) {
            callTransactionStatusFrag();
        }
    }

    private void callTransactionStatusFrag() {

        String cardPin = cardPinEditText.getText().toString();

        if (!Validation.isvalidControlPin(cardPin.trim())) {

            Toast.makeText(getActivity(),"Please enter a valid  Pin.",Toast.LENGTH_SHORT).show();


        } else {

           /* Fragment fragment = new TransactionSuccessFragment();
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            cardPinEditText.setText("");*/
        }

    }
}